package com.example.demae.service;

import com.amazonaws.services.s3.AmazonS3;
import com.example.demae.dto.menu.MenuResponseDto;
import com.example.demae.dto.store.StoreRequestDto;
import com.example.demae.dto.store.StoreResponseDto;
import com.example.demae.entity.Store;
import com.example.demae.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {
	private final StoreRepository storeRepository;

	public StoreResponseDto createStores(StoreRequestDto storeRequestDto) {
		if (storeRepository.findByName(storeRequestDto.getName()).isPresent()) {
			throw new IllegalArgumentException("이미 가입된 계정입니다.");
		}
		return new StoreResponseDto().success(storeRepository.save(new Store(storeRequestDto)));
	}

	public List<StoreResponseDto> findALL() {
		return storeRepository.findAll().stream()
				.map(store -> new StoreResponseDto().success(store))
				.toList();
	}
}
