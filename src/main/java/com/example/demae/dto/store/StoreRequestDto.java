package com.example.demae.dto.store;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class StoreRequestDto {
	private String name;
	private String address;
	private String category;
}
