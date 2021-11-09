package com.example.jms.controller;

import com.example.jms.service.RegistrationService;
import jms.dto.ActivateUserDto;
import jms.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jms-test/api/user")
@RequiredArgsConstructor
public class RegisterController {

    private final RegistrationService registrationService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(registrationService.register(userDto));
    }

    @PostMapping("/activate")
    public ResponseEntity<Void> activate(@RequestBody ActivateUserDto activateUserDto) {
        registrationService.activate(activateUserDto);
        return ResponseEntity.ok().build();
    }

}
