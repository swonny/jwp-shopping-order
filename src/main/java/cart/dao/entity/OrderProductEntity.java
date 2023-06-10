package cart.dao.entity;

import java.util.Objects;

public class OrderProductEntity {

    private final Long id;
    private final long orderId;
    private final long productId;
    private final String productName;
    private final int productPrice;
    private final String productImageUrl;
    private final int quantity;
    private final int totalPrice;

    private OrderProductEntity(Long id, long orderId, long productId, String productName, int productPrice, String productImageUrl, int quantity, int totalPrice) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImageUrl = productImageUrl;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderProductEntity that = (OrderProductEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "OrderProductEntity{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", productImageUrl='" + productImageUrl + '\'' +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                '}';
    }

    public Long getId() {
        return id;
    }

    public long getOrderId() {
        return orderId;
    }

    public long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public static class Builder {

        private long id;
        private long orderId;
        private long productId;
        private String productName;
        private int productPrice;
        private String productImageUrl;
        private int quantity;
        private int totalPrice;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder orderId(long orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder productId(long productId) {
            this.productId = productId;
            return this;
        }

        public Builder productName(String productName) {
            this.productName = productName;
            return this;
        }

        public Builder productPrice(int productPrice) {
            this.productPrice = productPrice;
            return this;
        }

        public Builder productImageUrl(String productImageUrl) {
            this.productImageUrl = productImageUrl;
            return this;
        }

        public Builder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder totalPrice(int totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public OrderProductEntity build() {
            return new OrderProductEntity(id, orderId, productId, productName, productPrice, productImageUrl, quantity, totalPrice);
        }
    }
}
