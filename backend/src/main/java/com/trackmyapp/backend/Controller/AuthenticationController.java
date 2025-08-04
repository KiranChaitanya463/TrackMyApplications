package com.trackmyapp.backend.Controller;

import com.trackmyapp.backend.DTO.LoginRequest;
import com.trackmyapp.backend.DTO.LoginResponse;
import com.trackmyapp.backend.DTO.UserRegisterRequest;
import com.trackmyapp.backend.Entity.User;
import com.trackmyapp.backend.Service.AutenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AutenticationService autenticationService;

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody UserRegisterRequest request){
        LoginResponse response=autenticationService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        LoginResponse response=autenticationService.login(request);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/me")
    public ResponseEntity<String> getCurrentUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(user.getName()); 
    }
}
