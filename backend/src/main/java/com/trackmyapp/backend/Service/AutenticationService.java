package com.trackmyapp.backend.Service;

import com.trackmyapp.backend.DTO.LoginRequest;
import com.trackmyapp.backend.DTO.LoginResponse;
import com.trackmyapp.backend.DTO.UserRegisterRequest;
import com.trackmyapp.backend.Entity.User;
import com.trackmyapp.backend.Exception.InvalidPasswordException;
import com.trackmyapp.backend.Exception.UserAlreadyExistsException;
import com.trackmyapp.backend.Exception.UserNotFoundException;
import com.trackmyapp.backend.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AutenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    // Registration
    public LoginResponse register(UserRegisterRequest request){
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new UserAlreadyExistsException("User with this email already exists");
        }

        User user=User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        userRepository.save(user);
        String token=jwtService.generateToken(user);
        return new LoginResponse(token,user.getName());
    }

    // Login
    public LoginResponse login(LoginRequest request){
        User user=userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        if(!passwordEncoder.matches(request.getPassword(),user.getPassword())){
            throw new InvalidPasswordException("Invalid password");
        }
        String token=jwtService.generateToken(user);
        return new LoginResponse(token,user.getName());
    }
}
