package com.reserved_product_service.repository;

import com.reserved_product_service.entity.ReservedProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservedProductRepository extends JpaRepository<ReservedProduct, Long> {

    Optional<ReservedProduct> findById(Long reservedProductId);

}
