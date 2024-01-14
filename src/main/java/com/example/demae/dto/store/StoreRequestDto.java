package com.example.demae.dto.store;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreRequestDto {
	@NotBlank
	private String name;
	@NotBlank
	private String address;
	@NotBlank
	private String category;
}
