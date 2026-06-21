package com.yobrunox.tp01backendhwt.controller;

import com.yobrunox.tp01backendhwt.dto.CodeDTO;
import com.yobrunox.tp01backendhwt.dto.LoginRequestDTO;
import com.yobrunox.tp01backendhwt.dto.UpdateUserDTO;
import com.yobrunox.tp01backendhwt.dto.ResponseUserDTO;
import com.yobrunox.tp01backendhwt.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("auth")
@Slf4j
public class AuthController {

    private final UserService userService;

    @GetMapping("{idUser}")
    public ResponseEntity<ResponseUserDTO> getUser(@PathVariable UUID idUser) {
        log.info("getUser with id={}", idUser);
        var response = userService.getUserById(idUser);
        return ResponseEntity.ok(response);
    }

    @PostMapping("login")
    public ResponseEntity<ResponseUserDTO> login(@RequestBody @Valid LoginRequestDTO requestDTO) {
        log.info("Login request received for user: {}", requestDTO.getUsername());
        var response = userService.login(requestDTO);
        return ResponseEntity.ok(response);

    }

    @PostMapping("register")
    public ResponseEntity<ResponseUserDTO> register(@RequestBody @Valid LoginRequestDTO requestDTO) {
        log.info("Reg: {}", requestDTO.getUsername());

        var response= userService.registerUser(requestDTO);

        return ResponseEntity.ok(response);

    }

    @PutMapping("{idUser}")
    public ResponseEntity<ResponseUserDTO> updateInfomation(@RequestBody @Valid UpdateUserDTO requestDTO, @PathVariable UUID idUser) {
        var response = userService.updateUser(idUser,requestDTO);
        log.info("Update information: {}", requestDTO.getUsername());
        return ResponseEntity.ok(response);
    }

    @PostMapping("sendCode")
    public ResponseEntity<Void> sendCode(@RequestBody CodeDTO codeDTO) {
        log.info("Send code: {}", codeDTO.getUsername());
        userService.sendCodeToEmail(codeDTO.getUsername());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("validateCode")
    public ResponseEntity<Void> validateCode(@RequestBody CodeDTO codeDTO) {
        log.info("Validate code: {}", codeDTO.getUsername());
        var res = userService.validateCodeToEmail(codeDTO);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("change")
    public ResponseEntity<Void> change(@RequestBody LoginRequestDTO dto) {
        log.info("Change user: {}", dto.getUsername());
        userService.changePassword(dto);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("test/token")
    public ResponseEntity<Void> testToken() {
        return ResponseEntity.ok().build();
    }
}
