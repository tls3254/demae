package com.example.demae.service;

import com.amazonaws.services.s3.AmazonS3;
import com.example.demae.dto.menu.MenuResponseDto;
import com.example.demae.dto.store.StoreRequestDto;
import com.example.demae.dto.store.StoreResponseDto;
import com.example.demae.entity.Store;
import com.example.demae.repository.StoreRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	public StoreResponseDto findStore(Long storeId) {
		return new StoreResponseDto().success(storeRepository.findById(storeId)
				.orElseThrow(() -> new EntityNotFoundException("Store not found with ID: " + storeId)));
	}

	public List<StoreResponseDto> findByCategory(String category) {
		return storeRepository.findByCategory(category).stream()
				.map(store -> new StoreResponseDto().success(store))
				.toList();
	}

	@Transactional
	public void modifyStore(Long storeId, StoreRequestDto storeRequestDto) {
		Store store = storeRepository.findById(storeId)
				.orElseThrow(() -> new EntityNotFoundException("Store not found with ID: " + storeId));
		store.setName(storeRequestDto.getName());
		store.setAddress(storeRequestDto.getAddress());
		store.setCategory(storeRequestDto.getCategory());
	}

	@Transactional
	public void deleteStore(Long storeId) {
		storeRepository.delete(storeRepository.findById(storeId)
				.orElseThrow(() -> new EntityNotFoundException("Store not found with ID: " + storeId)));
	}

	public List<StoreResponseDto> findAll(int page, int size) {
		return storeRepository.findAll(PageRequest.of(page, size)).stream()
				.map(store -> new StoreResponseDto().success(store))
				.toList();
	}
}
