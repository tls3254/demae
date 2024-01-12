package com.example.demae.controller;


import com.example.demae.dto.login.SignupRequestDto;
import com.example.demae.entity.User;
import com.example.demae.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping
    public User signup(@RequestBody SignupRequestDto requestDto, BindingResult bindingResult){
//        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
//        if(fieldErrors.size() > 0){
//            for (FieldError fieldError : fieldErrors) {
//                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
//            }
//        }
        return userService.signup(requestDto);
    }

}
