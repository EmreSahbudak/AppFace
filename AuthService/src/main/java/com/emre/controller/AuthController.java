package com.emre.controller;

import com.emre.dto.request.LoginRequestDto;
import com.emre.dto.request.RegisterRequestDto;
import com.emre.dto.response.LoginResponseDto;
import com.emre.dto.response.RegisterResponseDto;
import com.emre.exception.AuthException;
import com.emre.exception.ErrorType;
import com.emre.repository.entity.Auth;
import com.emre.service.AuthService;
import com.emre.utility.JwtTokenManager;
import com.emre.utility.TokenCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    //private final TokenCreator tokenCreator;
    private final JwtTokenManager jwtTokenManager;

    @GetMapping("getpage")
    public ResponseEntity<String> getPage(){
        return ResponseEntity.ok("AuthServiceye ulaştınız");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto dto){
        Optional<Auth> auth=authService.doLogin(dto);
        if (auth.isEmpty())
            return ResponseEntity.ok(LoginResponseDto.builder()
                            .statusCode(4000)
                            .message("Email veya şifre Hatalı")
                    .build());
        return ResponseEntity.ok(LoginResponseDto.builder()
                        .statusCode(2001)
                        .message(jwtTokenManager.createToken(auth.get().getId()).get())
                .build());
    }


    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@RequestBody @Valid RegisterRequestDto dto){
        if (!dto.getPassword().equals(dto.getRepassword()))
            throw new AuthException(ErrorType.ERROR_PASSWORD);
        authService.register(dto);
        return ResponseEntity.ok(RegisterResponseDto.builder()
                .statusCode(2000)
                .message("Kayıt işlemi Başarılı.... " +
                        "Lütfen Mailinize gelen Aktivasyon linkine tıklayınız")
                .build());
    }


}
