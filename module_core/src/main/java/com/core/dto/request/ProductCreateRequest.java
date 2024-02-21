package com.core.dto.request;

import com.core.entity.type.ProductType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProductCreateRequest {

    @NotNull
    @Size(min = 3, max = 100)
    private String title;

    @NotNull
    private String content;

    @NotNull
    private Integer price;

    @NotNull
    private ProductType productType;

    @DateTimeFormat(pattern = "yyyy-MM-dd 'T' HH:mm:ss")
    private LocalDateTime reservedStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd 'T' HH:mm:ss")
    private LocalDateTime reservedEnd;

}
