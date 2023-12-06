package az.azercell.bankingapp.controller;

import az.azercell.bankingapp.model.request.AuthenticationRequest;
import az.azercell.bankingapp.model.request.UserRegisterRequest;
import az.azercell.bankingapp.model.response.AuthenticationResponse;
import az.azercell.bankingapp.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/v1")
@RequiredArgsConstructor
@Log4j2
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody UserRegisterRequest userRegisterRequest) {
        log.debug("user register for {} start", userRegisterRequest.getGsmNumber());
        AuthenticationResponse authenticationResponse = authenticationService.registerUser(userRegisterRequest);
        log.debug("user register for {} end", userRegisterRequest.getGsmNumber());
        return ResponseEntity.ok(authenticationResponse);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        log.debug("user authenticate for {} start", authenticationRequest.getLogin());
        AuthenticationResponse authenticationResponse = authenticationService.authenticateUser(authenticationRequest);
        log.debug("user authenticate for {} end", authenticationRequest.getLogin());
        return ResponseEntity.ok(authenticationResponse);
    }

}
