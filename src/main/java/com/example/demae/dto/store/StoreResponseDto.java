package com.example.demae.dto.store;

import com.example.demae.entity.Store;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class StoreResponseDto {
	private static final String STORE_CREATE_CODE = "성공";

	private Long id;
	private String name;
	private String address;
	private String category;
	private String message;

	public StoreResponseDto success(Store store) {
		this.name = store.getName();
		this.address = store.getAddress();
		this.category = store.getCategory();
		this.message = STORE_CREATE_CODE;
		this.id = store.getId();
		return this;
	}


}
