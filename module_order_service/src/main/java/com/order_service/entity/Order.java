package com.order_service.entity;

import com.core.entity.core.BaseCreatedUpdated;
import com.core.entity.type.ProductType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity(name = "orders")
@Table(name = "orders")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Order extends BaseCreatedUpdated {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderId", nullable = false, updatable = false)
    private Long id;

    @Column(name = "userId", nullable = false)
    private Long userId;

    @Column(name = "productType", nullable = false)
    private ProductType productType;

    @Column(name = "productId", nullable = false)
    private Long productId;

    @Column(name = "paymentId")
    private Long paymentId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "isOrdered", nullable = false)
    private Boolean isOrdered;

}
