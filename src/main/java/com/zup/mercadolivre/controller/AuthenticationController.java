package com.zup.mercadolivre.controller;

import com.zup.mercadolivre.configuration.security.service.TokenService;
import com.zup.mercadolivre.entity.user.form.UserLoginForm;
import com.zup.mercadolivre.entity.user.response.UserTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<UserTokenResponse> userAuthentication(@RequestBody @Valid UserLoginForm userLoginForm){

        UsernamePasswordAuthenticationToken logingToken = userLoginForm.toUsernamePasswordAuthenticationToken();
        Authentication authentication = authManager.authenticate(logingToken);
        String token = tokenService.generateToken(authentication);

        return ResponseEntity.ok(new UserTokenResponse(token, "Bearer"));
    }
}
