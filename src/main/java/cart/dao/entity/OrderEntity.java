package cart.dao.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class OrderEntity {

    private final Long id;
    private final long memberId;
    private final int totalPayment;
    private final int usedPoint;
    private final LocalDateTime createdAt;

    public OrderEntity(Long id, long memberId, int totalPayment, int usedPoint, LocalDateTime createdAt) {
        this.id = id;
        this.memberId = memberId;
        this.totalPayment = totalPayment;
        this.usedPoint = usedPoint;
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderEntity that = (OrderEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public long getMemberId() {
        return memberId;
    }

    public int getTotalPayment() {
        return totalPayment;
    }

    public int getUsedPoint() {
        return usedPoint;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", totalPayment=" + totalPayment +
                ", usedPoint=" + usedPoint +
                ", createdAt=" + createdAt +
                '}';
    }

    public static class Builder {

        private Long id;
        private long memberId;
        private int totalPayment;
        private int usedPoint;
        private LocalDateTime createdAt;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder memberId(long memberId) {
            this.memberId = memberId;
            return this;
        }

        public Builder totalPayment(int totalPayment) {
            this.totalPayment = totalPayment;
            return this;
        }

        public Builder usedPoint(int usedPoint) {
            this.usedPoint = usedPoint;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public OrderEntity build() {
            return new OrderEntity(id, memberId, totalPayment, usedPoint, createdAt);
        }
    }
}
