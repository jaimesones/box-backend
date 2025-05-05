package com.tfm.backend.api.resources;

import com.tfm.backend.api.dtos.TokenDto;
import com.tfm.backend.api.dtos.UserDto;
import com.tfm.backend.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RestController
@Component
@RequestMapping(UserResource.USERS)
public class UserResource {
    public static final String USERS = "/users";
    public static final String TOKEN = "/token";
    public static final String SEARCH = "/search";
    public static final String PING = "/ping";
    private static final String SELF_URL = "https://box-backend-vjvj.onrender.com/users/ping";
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserResource.class);
    private final RestTemplate restTemplate = new RestTemplate();


    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @SecurityRequirement(name = "basicAuth")
    @PostMapping(value = TOKEN)
    public Optional<TokenDto> login(@AuthenticationPrincipal User activeUser) {
        return userService.login(activeUser.getUsername())
                .map(TokenDto::new);
    }
    
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping(SEARCH)
    public UserDto findUser(
            @RequestHeader(value = "Authorization") String token) {
        return new UserDto(this.userService.findUser(token));
    }
    
    @Scheduled(fixedRate = 720000)
    public void pingReply() {
        try {
            restTemplate.getForObject(SELF_URL, String.class);
            logger.info("Self-ping exitoso a {}", SELF_URL);
        } catch (Exception e) {
            logger.warn("Fallo en self-ping: {}", e.getMessage());
        }
    }
    
    @SecurityRequirement(name = "basicAuth")
    @GetMapping(PING)
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }



}
