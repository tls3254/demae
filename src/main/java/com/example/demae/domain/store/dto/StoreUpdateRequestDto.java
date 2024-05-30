package com.example.demae.domain.store.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreUpdateRequestDto {

    private String storeName;
    private String storeAddress;
    private String storeCategory;
}
