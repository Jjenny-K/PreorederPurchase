package com.reserved_product_stock_service.entity;

import com.core.entity.core.BaseCreatedUpdated;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity(name = "reservedProductStocks")
@Table(name = "reservedProductStocks")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ReservedProductStock extends BaseCreatedUpdated {

    @Id
    @Column(name = "reservedProductId", nullable = false, updatable = false)
    private Long productId;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    public void updateStock(Integer updatedStock) {
        if(updatedStock != null) this.stock = updatedStock;
    }

}
