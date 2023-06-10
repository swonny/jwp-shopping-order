package cart.repository;

import cart.domain.member.Member;
import cart.domain.order.Order;
import cart.domain.order.OrderProduct;
import cart.domain.order.OrderProducts;
import cart.domain.payment.Payment;
import cart.exception.OrderException;
import cart.exception.OrderProductException;
import cart.repository.dao.OrderDao;
import cart.repository.entity.OrderEntity;
import cart.repository.entity.OrderProductEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@Repository
public class OrderRepository {

    private final OrderDao orderDao;

    public OrderRepository(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public long createOrder(Order order) {
        OrderProducts orderProducts = new OrderProducts(order.getOrderProducts());
        OrderEntity orderEntity = toOrderEntity(order);
        long orderId = orderDao.insertOrder(orderEntity);
        saveOrderItems(orderProducts, orderId);
        return orderId;
    }

    private static OrderEntity toOrderEntity(Order order) {
        return new OrderEntity.Builder()
                .memberId(order.getMember().getId())
                .totalPayment(order.getTotalPrice())
                // TODO: 디미터의 법칙 지켜보기
                .usedPoint(order.getUsedPoint().getPoint())
                .build();
    }

    private void saveOrderItems(OrderProducts orderProducts, long orderId) {
        orderProducts.getOrderProducts()
                .stream()
                .map(orderProduct -> toOrderItemEntity(orderId, orderProduct))
                .forEach(orderDao::insertOrderItems);
    }

    private static OrderProductEntity toOrderItemEntity(long orderId, OrderProduct orderProduct) {
        return new OrderProductEntity.Builder()
                .productId(orderProduct.getProductId())
                .orderId(orderId)
                .productName(orderProduct.getName())
                .productPrice(orderProduct.getPrice())
                .productImageUrl(orderProduct.getImageUrl())
                .quantity(orderProduct.getQuantity())
                .totalPrice(orderProduct.getTotalPrice())
                .build();
    }

    // 사용자별 주문 내역
    public List<Order> findOrderProductsByMemberId(Member member) {
        List<Long> orderIds = orderDao.getOrderIdsByMemberId(member.getId())
                .orElseThrow(OrderProductException.NotFound::new);
        return orderIds.stream()
                .map(orderId -> findOrderById(member, orderId))
                .collect(toList());
    }

    public Order findOrderById(Member member, long orderId) {
        OrderEntity orderEntity = orderDao.getOrderById(orderId)
                .orElseThrow(OrderException.NotFound::new);
        List<OrderProductEntity> orderProductEntities = orderDao.getOrderProductsByOrderId(orderId)
                .orElseThrow(OrderProductException.NotFound::new);
        OrderProducts orderProducts = toOrderProducts(orderId, orderProductEntities);
        Payment payment = Payment.of(orderEntity.getTotalPayment(), orderEntity.getUsedPoint());
        return Order.of(orderId, member, orderProducts, payment, orderEntity.getCreatedAt());
    }

    private static OrderProducts toOrderProducts(long orderId, List<OrderProductEntity> orderProductEntities) {
        return orderProductEntities.stream()
                .map(orderProductEntity -> new OrderProduct.Builder()
                        .id(orderProductEntity.getId())
                        .orderId(orderProductEntity.getOrderId())
                        .productId(orderProductEntity.getProductId())
                        .productName(orderProductEntity.getProductName())
                        .productPrice(orderProductEntity.getProductPrice())
                        .productImageUrl(orderProductEntity.getProductImageUrl())
                        .quantity(orderProductEntity.getQuantity())
                        .totalPrice(orderProductEntity.getTotalPrice())
                        .build()
                ).collect(collectingAndThen(toList(), (orderProducts) -> new OrderProducts(orderId, orderProducts)));
    }
}
