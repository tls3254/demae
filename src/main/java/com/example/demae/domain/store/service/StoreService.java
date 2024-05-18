package com.example.demae.domain.store.service;

import com.example.demae.domain.store.dto.StoreRequestDto;
import com.example.demae.domain.store.dto.StoreResponseDto;
import com.example.demae.domain.store.dto.StoreUpdateRequestDto;
import com.example.demae.domain.store.entity.Store;
import com.example.demae.domain.store.repository.StoreRepository;
import com.example.demae.domain.user.entity.User;
import com.example.demae.domain.user.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreService {
	private final StoreRepository storeRepository;
	private final UserService userService;

	public void createStores(StoreRequestDto storeRequestDto, String userEmail) {
		User user = userService.findUser(userEmail);
		if (storeRepository.findByUserId(user.getUserId()).isPresent()) {
			throw new IllegalArgumentException("이미 상점이 등록되어 있습니다.");
		}
		Store store = new Store(storeRequestDto ,user);
		storeRepository.save(store);
	}

	public void modifyStore(Long storeId, String userEmail, StoreUpdateRequestDto storeRequestDto) {
		Store store = findStore(storeId);
		storeCheck(store, userEmail);
		store.update(storeRequestDto);
	}

	public void deleteStore(Long storeId, String userEmail) {
		Store store = findStore(storeId);
		storeCheck(store, userEmail);
		storeRepository.delete(store);
	}

	@Transactional(readOnly = true)
	public Page<StoreResponseDto> findStoreAll(int page, int size) {
		Page<Store> stores = storeRepository.findAll(PageRequest.of(page, size));
		return stores.map(StoreResponseDto::new);
	}

	@Transactional(readOnly = true)
	public List<StoreResponseDto> findByCategory(String category) {
		List<Store> stores = storeRepository.findByCategory(category);
		return stores.stream().map(StoreResponseDto::new).toList();
	}

	@Transactional(readOnly = true)
	public StoreResponseDto findStoreOne(Long storeId) {
		return new StoreResponseDto(findStore(storeId));
	}

	@Transactional(readOnly = true)
	public Store findStore(Long storeId){
		return storeRepository.findById(storeId).orElseThrow(() -> new EntityNotFoundException("상점을 찾을 수 없습니다."));
	}

	public void storeCheck(Store store, String userEmail){
		if (!store.getUser().getUserEmail().equals(userEmail)) {
			throw new IllegalArgumentException("본인 정보만 수정이 가능합니다.");
		}
	}
}
