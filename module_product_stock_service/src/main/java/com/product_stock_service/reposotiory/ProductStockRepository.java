package com.product_stock_service.reposotiory;

import com.product_stock_service.entity.ProductStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {

    Optional<ProductStock> findByProductId(Long productId);

}
