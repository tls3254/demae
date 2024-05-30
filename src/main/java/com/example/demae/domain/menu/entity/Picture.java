//package com.example.demae.entity;
//
//import com.example.demae.domain.menu.entity.Menu;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.Table;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Getter
//@Setter
//@Entity
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@Table(name = "picture")
//public class Picture {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;
//
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name ="menu_id")
//	@JsonIgnore
//	private Menu menu;
//
//	@Column(nullable = false)
//	private String uuid;
//
//	public Picture(Menu menu, String uuid) {
//		this.menu = menu;
//		this.uuid = uuid;
//	}
//}

