package com.example.demae.controller;

import com.example.demae.dto.menu.MenuResponseDto;
import com.example.demae.dto.store.StoreRequestDto;
import com.example.demae.dto.store.StoreResponseDto;
import com.example.demae.service.StoreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

	@GetMapping
	public String findStore(Model model){
		List<StoreResponseDto> store = storeService.findALL();
		model.addAttribute("storeList", store);
		return "showStorePage";
	}
}
