package com.product_service.entity;

import com.core.entity.core.BaseCreatedUpdated;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity(name = "reservedProducts")
@Table(name = "reservedProducts")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ReservedProduct extends BaseCreatedUpdated {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservedProductId", nullable = false, updatable = false)
    private Long id;

    @Column(name = "userId", nullable = false)
    private Long userId;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "reservedStart", nullable = false)
    private LocalDateTime reservedStart;

    @Column(name = "reservedEnd", nullable = false)
    private LocalDateTime reservedEnd;

}
