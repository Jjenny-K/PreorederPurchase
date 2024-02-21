package com.product_service.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.product_service.entity.ReservedProduct;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReservedProductResponse {

    private String title;

    private String content;

    private Integer price;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd 'T' HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime reservedStart;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd 'T' HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime reservedEnd;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd 'T' HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd 'T' HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;

    public static ReservedProductResponse from(ReservedProduct reservedProduct) {
        if (reservedProduct == null) return null;

        return ReservedProductResponse.builder()
                .title(reservedProduct.getTitle())
                .content(reservedProduct.getContent())
                .price(reservedProduct.getPrice())
                .reservedStart(reservedProduct.getReservedStart())
                .reservedEnd(reservedProduct.getReservedEnd())
                .createdAt(reservedProduct.getCreatedAt())
                .updatedAt(reservedProduct.getUpdatedAt())
                .build();
    }

}
