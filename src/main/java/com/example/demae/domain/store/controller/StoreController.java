package com.example.demae.domain.store.controller;

import com.example.demae.domain.store.dto.StoreRequestDto;
import com.example.demae.domain.store.dto.StoreUpdateRequestDto;
import com.example.demae.domain.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {

	private final StoreService storeService;

	@PostMapping
	public ResponseEntity<String> createStore(@RequestBody @Validated StoreRequestDto storeRequestDto,
											  @AuthenticationPrincipal UserDetails userDetails) {
		storeService.createStores(storeRequestDto, userDetails.getUsername());
		return ResponseEntity.ok("ok");
	}

	@PatchMapping("/{storeId}")
	public ResponseEntity<String> modifyStore(@PathVariable Long storeId,
											  @RequestBody StoreUpdateRequestDto storeRequestDto,
											  @AuthenticationPrincipal UserDetails userDetails){
		storeService.modifyStore(storeId, userDetails.getUsername(), storeRequestDto);
		return ResponseEntity.ok("ok");
	}

	@DeleteMapping("/{storeId}")
	public ResponseEntity<String> deleteStore(@PathVariable Long storeId,
											  @AuthenticationPrincipal UserDetails userDetails){
		storeService.deleteStore(storeId, userDetails.getUsername());
		return ResponseEntity.ok("ok");
	}
}
