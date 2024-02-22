package com.reserved_product_service.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReservedProductCreateRequest {

    @NotNull
    @Size(min = 3, max = 100)
    private String title;

    @NotNull
    private String content;

    @NotNull
    @Min(1)
    private Integer price;

    @NotNull
    @Min(0)
    private Integer stock;

    @DateTimeFormat(pattern = "yyyy-MM-dd 'T' HH:mm:ss")
    private LocalDateTime reservedStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd 'T' HH:mm:ss")
    private LocalDateTime reservedEnd;

}
