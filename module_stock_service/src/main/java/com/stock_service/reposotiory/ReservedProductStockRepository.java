package com.stock_service.reposotiory;

import com.stock_service.entity.ReservedProductStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservedProductStockRepository extends JpaRepository<ReservedProductStock, Long> {

    Optional<ReservedProductStock> findByProductId(Long reservedProductId);

}
