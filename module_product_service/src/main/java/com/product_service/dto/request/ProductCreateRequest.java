package com.product_service.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

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
    @Min(value = 1)
    private Integer price;

    @NotNull
    @Min(value = 0)
    private Integer stock;

}
