package com.pmarko09.order_service.model.entity;

import com.pmarko09.order_service.model.dto.CartInfoDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ORDERS")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private CartInfoDto cart;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Enumerated(EnumType.STRING)
    private OrderDelivery delivery;

    @Enumerated(EnumType.STRING)
    private PaymentForm paymentForm;

    private LocalDateTime createdAt;
    private Double finalCost;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Order other)) {
            return false;
        }

        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        if (id == null) {
            return 0;
        }

        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", cart=" + cart +
                ", client=" + client +
                ", delivery=" + delivery +
                ", paymentForm=" + paymentForm +
                ", createdAt=" + createdAt +
                ", finalCost=" + finalCost +
                '}';
    }
}