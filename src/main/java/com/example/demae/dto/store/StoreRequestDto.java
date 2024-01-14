package com.example.demae.dto.store;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class StoreRequestDto {
	@NotBlank
	private String name;
	@NotBlank
	private String address;
	@NotBlank

	private String category;
	List<MultipartFile> files;
}
