package com.example.demae.controller;

import com.example.demae.dto.store.StoreRequestDto;
import com.example.demae.security.UserDetailsImpl;
import com.example.demae.service.StoreService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/stores")
@AllArgsConstructor
public class StoreController {
	private final StoreService storeService;

	@GetMapping("home")
	public String home(){return "/admin/store/store";}

	@PostMapping
	@ResponseBody
	public String createStore(@RequestBody @Validated StoreRequestDto storeRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		storeService.createStores(storeRequestDto, userDetails.getUser());
		return "ok";
	}

	@GetMapping("/category")
	public String findByCategory(@RequestParam String category, Model model){
		model.addAttribute("storeList", storeService.findByCategory(category));
		return "/admin/store/showStorePage";
	}

	@GetMapping("/{storeId}")
	public String findStore(@PathVariable Long storeId, Model model,
							@AuthenticationPrincipal UserDetailsImpl userDetails){
		model.addAttribute("storeList", storeService.findStore(storeId));
		if (userDetails.getUser().getRole().name().equals("ADMIN") &&
				userDetails.getUser().getStore().getId() == storeId) {
			return "/admin/store/showStorePage";
		}
		return "/user/store/showStorePageUser";
	}


	@GetMapping
	public String findAllStore(@RequestParam(defaultValue = "0") int page,
							   @RequestParam(defaultValue = "10") int size,
							   Model model){
		model.addAttribute("storeList", storeService.findAll(page, size));
		return "/global/store/showAllStorePage";
	}

	@PatchMapping("/{storeId}")
	@ResponseBody
	public ResponseEntity<String> modifyStore(@PathVariable Long storeId,
											  @RequestBody StoreRequestDto storeRequestDto,
											  @AuthenticationPrincipal UserDetails userDetails){
		try {
			storeService.modifyStore(storeId, storeRequestDto, userDetails.getUsername());
			return ResponseEntity.ok("ok");
		} catch (EntityNotFoundException e) {
			return ResponseEntity.badRequest().body("fail");
		}
	}

	@DeleteMapping("/{storeId}")
	@ResponseBody
	public ResponseEntity<String> deleteStore(@PathVariable Long storeId, @AuthenticationPrincipal UserDetails userDetails){
		try {
			storeService.deleteStore(storeId, userDetails.getUsername());
			return ResponseEntity.ok("ok");
		} catch (EntityNotFoundException e) {
			return ResponseEntity.badRequest().body("fail");
		}
	}
}
