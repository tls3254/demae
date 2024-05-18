package com.example.demae.domain.store.dto;

import com.example.demae.domain.store.entity.Store;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreResponseDto {

	private Long storeId;
	private String storeName;
	private String storeAddress;
	private String storeCategory;
	private String storeMessage;

	public StoreResponseDto(Store store) {
		this.storeId = store.getStoreId();
		this.storeName = store.getStoreName();
		this.storeAddress = store.getStoreAddress();
		this.storeCategory = store.getStoreCategory();
	}
}
