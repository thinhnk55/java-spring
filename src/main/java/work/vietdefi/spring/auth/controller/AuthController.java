package work.vietdefi.spring.auth.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import work.vietdefi.spring.auth.dto.LoginRequest;
import work.vietdefi.spring.auth.dto.RegisterRequest;
import work.vietdefi.spring.auth.service.UserService;
import work.vietdefi.spring.common.dto.BaseResponse;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {
    @Autowired
    UserService userService;
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        return userService.register(registerRequest);
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<?> login(@Valid  @RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }
    @GetMapping("/verify")
    public BaseResponse<?> verify() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String userId = authentication.getName();
            return userService.get(userId);
        } else {
            throw new BadCredentialsException("");
        }
    }
}
