package de.yourchessboook.controller;

import de.yourchessboook.oauth.OAuthService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/params")
    public String[] getParams(){
        OAuthService oAuthService = new OAuthService();
        return new String[]{oAuthService.getCode_challenge(), oAuthService.getCode_challenge_method(), oAuthService.getState()};
    }
}
