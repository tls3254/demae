package com.example.demae.dto.menu;

import com.example.demae.entity.Picture;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class MenuRequestDto {
    private String name;
    private int price;
//    private List<MultipartFile> files;
}
