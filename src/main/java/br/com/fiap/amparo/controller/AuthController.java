package br.com.fiap.amparo.controller;

import br.com.fiap.amparo.dto.LoginRequest;
import br.com.fiap.amparo.dto.RegistroRequest;
import br.com.fiap.amparo.dto.TokenResponse;
import br.com.fiap.amparo.dto.UsuarioResponse;
import br.com.fiap.amparo.model.Usuario;
import br.com.fiap.amparo.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/registro")
    public ResponseEntity<UsuarioResponse> registrar(@RequestBody @Valid RegistroRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(req));
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody @Valid LoginRequest req) {
        return service.login(req);
    }

    /** Quem sou eu: util para o app Mobile validar o token e saber o papel. */
    @GetMapping("/me")
    public UsuarioResponse me(@AuthenticationPrincipal Usuario usuario) {
        return UsuarioResponse.from(usuario);
    }
}
