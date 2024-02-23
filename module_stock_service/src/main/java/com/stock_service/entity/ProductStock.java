package com.stock_service.entity;

import com.core.entity.core.BaseCreatedUpdated;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity(name = "productStocks")
@Table(name = "productStocks")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ProductStock extends BaseCreatedUpdated {

    @Id
    @Column(name = "productId", nullable = false, updatable = false)
    private Long productId;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    public void updateStock(Integer quantity) {
        if(quantity != null) this.stock = this.stock - quantity;
    }

}
