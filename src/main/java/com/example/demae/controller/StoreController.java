package com.example.demae.controller;

import com.example.demae.dto.menu.MenuResponseDto;
import com.example.demae.dto.store.StoreRequestDto;
import com.example.demae.dto.store.StoreResponseDto;
import com.example.demae.service.StoreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/stores")
@AllArgsConstructor
public class StoreController {
	private final StoreService storeService;

	@GetMapping("home")
	public String home(){return "store";}

	@PostMapping
	public String createStore(@RequestBody StoreRequestDto storeRequestDto) {
		storeService.createStores(storeRequestDto);
		return "showStorePage";


	}
	@GetMapping("/{storeId}")
	public String findStore(@PathVariable Long storeId, Model model){
		model.addAttribute("storeList", storeService.findStore(storeId));
		return "showStorePage";
	}

	@GetMapping
	public String findAllStore(Model model){
		List<StoreResponseDto> store = storeService.findAll();
		model.addAttribute("storeList", store);
		return "showStorePage";
	}

	@PatchMapping("/{storeId}")
	public String modify(@PathVariable Long storeId, Model model){
		model.addAttribute("storeList", storeService.findStore(storeId));
		return "showStorePage";
	}
}
