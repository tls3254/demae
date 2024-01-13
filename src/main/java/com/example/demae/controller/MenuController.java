package com.example.demae.controller;

import com.example.demae.dto.menu.MenuRequestDto;
import com.example.demae.dto.menu.MenuResponseDto;
import com.example.demae.security.UserDetailsImpl;
import com.example.demae.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class MenuController {
	private final MenuService menuService;

	@GetMapping
	public String home(){return "menu";}

	@PostMapping("/createMenu")
	public String createMenu(@RequestBody MenuRequestDto menuRequestDto){
		String message = menuService.createMenu(menuRequestDto);
		if(!message.equals("성공")){
			throw new IllegalArgumentException("실패했습니다.");
		}
		return "showMenuPage";
	}
	@GetMapping("/menu")
	public String AllMenu(Model model){
		List<MenuResponseDto> allMenu = menuService.AllMenu();
		model.addAttribute("menuList", allMenu);
		return "showMenuPage";
	}


}
