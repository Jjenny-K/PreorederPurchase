package com.core.entity.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductType {

    NORMAL("normal"),
    RESERVED("reserved"),;

    private final String productType;

}
