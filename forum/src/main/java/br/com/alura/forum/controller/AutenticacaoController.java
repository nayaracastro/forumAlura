package br.com.alura.forum.controller;

import br.com.alura.forum.config.validacao.security.TokenService;
import br.com.alura.forum.controller.dto.TokenDto;
import br.com.alura.forum.controller.form.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenDto> autenticar( @RequestBody @Valid LoginForm form){
        UsernamePasswordAuthenticationToken dadosLogin = form.converter();

        // try , catch para autenticar o login ou retornar uma resposta em caso de erro
        try {
            Authentication authentication= authManager.authenticate(dadosLogin);

            String token = tokenService.gerarToken(authentication);
            return  ResponseEntity.ok(new TokenDto(token,"Bearer"));
        } catch (AuthenticationException e){
            return ResponseEntity.badRequest().build();
        }


    }
}
