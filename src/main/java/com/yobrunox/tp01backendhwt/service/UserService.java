package com.yobrunox.tp01backendhwt.service;

import com.yobrunox.tp01backendhwt.dto.CodeDTO;
import com.yobrunox.tp01backendhwt.dto.LoginRequestDTO;
import com.yobrunox.tp01backendhwt.dto.UpdateUserDTO;
import com.yobrunox.tp01backendhwt.dto.ResponseUserDTO;
import com.yobrunox.tp01backendhwt.exception.BusinessException;
import com.yobrunox.tp01backendhwt.mail.MailSend;
import com.yobrunox.tp01backendhwt.model.Child;
import com.yobrunox.tp01backendhwt.model.Code;
import com.yobrunox.tp01backendhwt.model.Role;
import com.yobrunox.tp01backendhwt.model.User;
import com.yobrunox.tp01backendhwt.repository.ChildRepository;
import com.yobrunox.tp01backendhwt.repository.CodeRepository;
import com.yobrunox.tp01backendhwt.repository.RoleRepository;
import com.yobrunox.tp01backendhwt.repository.UserRepository;
import com.yobrunox.tp01backendhwt.security.JwtService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
@Transactional(timeout = 20)
public class UserService {
    private final UserRepository userRepository;
    private final ChildRepository childRepository;
    private final MailSend mailSend;
    private final CodeRepository codeRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RoleRepository roleRepository;
//    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    public ResponseUserDTO getUserById(UUID id){
        User user = userRepository.findById(id).orElseThrow(()-> new BusinessException(HttpStatus.NOT_FOUND, "Recurso no encontrada" ));
        return ResponseUserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .work(user.getWork())
                .idChild(Objects.isNull(user.getChild()) ? null : user.getChild().getId())
                .nameChild(Objects.isNull(user.getChild()) ? null : user.getChild().getName())
                .ageChild(Objects.isNull(user.getChild()) ? null : user.getChild().getAge())
                .genderChild(Objects.isNull(user.getChild()) ? null : user.getChild().getGender())
                .levelTEA(Objects.isNull(user.getChild()) ? null : user.getChild().getLevelTEA())
                .build();
    }

    @Transactional
    public ResponseUserDTO login(LoginRequestDTO request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Recurso no encontrada" ));
        //Child child = childRepository.findByUser_Id(user.getId()).orElse(null);
        ResponseUserDTO response = ResponseUserDTO.builder()
                .token(jwtService.generateToken(user))
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .work(user.getWork())
                .idChild(Objects.isNull(user.getChild()) ? null : user.getChild().getId())
                .nameChild(Objects.isNull(user.getChild()) ? null : user.getChild().getName())
                .ageChild(Objects.isNull(user.getChild()) ? null : user.getChild().getAge())
                .genderChild(Objects.isNull(user.getChild()) ? null : user.getChild().getGender())
                .levelTEA(Objects.isNull(user.getChild()) ? null : user.getChild().getLevelTEA())
                .build();
        return response;
    }

    @Transactional
    public ResponseUserDTO registerUser(LoginRequestDTO userGetDTO) {

//        if(this.getUserByUsername(userGetDTO.getEmail()) != null){
//            throw new BusinessException(HttpStatus.BAD_REQUEST, "Ya existe un usuario con este email");
//        }

        List<Role> getRoles = roleRepository.findAll();
        Set<Role> roles;

        if(getRoles.size() == 0){
            roles = Set.of(Role.builder()
                            .role(Role.ERole.USER)
                            .build(),
                    Role.builder()
                            .role(Role.ERole.ADMIN)
                            .build()
            );
            roleRepository.saveAll(roles);
        }else {
            roles = getRoles.stream().collect(Collectors.toSet());
        }

        /*Child  child = Child.builder()
                .name(userGetDTO.getNameChild())
                .age(userGetDTO.getAgeChild())
                .gender(userGetDTO.getGenderChild())
                .build();*/

        User user = User.builder()
                //.name(userGetDTO.getName())
                .username(userGetDTO.getUsername())
                .password(passwordEncoder.encode(userGetDTO.getPassword()))
                .roles(roles.stream().filter(r-> r.getRole().equals(Role.ERole.USER)).collect(Collectors.toSet()))
                //.child(child)
                .build();
        //child.setUser(user);
     userRepository.save(user);
        ResponseUserDTO response = ResponseUserDTO.builder()
                .token(jwtService.generateToken(user))
                .id(user.getId())
                //.name(userGetDTO.getName())
                .username(userGetDTO.getUsername())
                //.work(userGetDTO.getWork())
                //.idChild(child.getId())
                //.nameChild(child.getName())
                //.ageChild(child.getAge())
                //.genderChild(child.getGender())
                //.levelTEA(child.getLevelTEA())
                .build();
        return response;
    }

    @Transactional
    public ResponseUserDTO updateUser(UUID idUser, UpdateUserDTO userGetDTO) {
        User user = userRepository.findById(idUser).
                orElseThrow(
                        () -> new BusinessException(HttpStatus.NOT_FOUND, "Usuario no encontrado" )
                );

        Child child;
        System.out.println("PASO POR AQUI 0");

        if (Objects.isNull(user.getChild())){
            System.out.println("PASO POR AQUI 1");
            child = Child.builder()
                    .name(userGetDTO.getNameChild() == "" ? user.getChild().getName() : userGetDTO.getNameChild() )
                    .age(userGetDTO.getAgeChild() == null ? user.getChild().getAge() : userGetDTO.getAgeChild() )
                    .gender(userGetDTO.getNameChild() == "" ? user.getChild().getName() : userGetDTO.getNameChild())
                    .levelTEA(user.getChild() == null ? 0 :user.getChild().getLevelTEA())
                    .build();
            child = childRepository.save(child);
            user.setChild(child);
        }else{
            System.out.println("PASO POR AQUI 2");
            child = Child.builder()
                    .id(user.getChild().getId())
                    .name(userGetDTO.getNameChild() == "" ? user.getChild().getName() : userGetDTO.getNameChild() )
                    .age(userGetDTO.getAgeChild() == null ? user.getChild().getAge() : userGetDTO.getAgeChild() )
                    .gender(userGetDTO.getGenderChild() == "" ? user.getChild().getGender() : userGetDTO.getGenderChild())
                    .levelTEA(user.getChild().getLevelTEA())
                    .build();
        }

        /*if(!userGetDTO.getPassword().equals(user.getPassword()) || !(userGetDTO.getPassword() == "") || !(userGetDTO.getPassword() == null)){
            throw new BusinessException(HttpStatus.BAD_REQUEST, "");
        }*/
        String password = user.getPassword();
        if(!Objects.isNull(userGetDTO.getCurrentPassword())){
            if(!userGetDTO.getCurrentPassword().isEmpty()){
                if(passwordEncoder.matches(userGetDTO.getCurrentPassword(),password)){
                    password = passwordEncoder.encode(userGetDTO.getPassword());
                } else{
                    throw new BusinessException(HttpStatus.BAD_REQUEST, "Las contraseñas no coinciden");
                }
            }
        }
        User userSave = User.builder()
                .id(user.getId())
                .name(userGetDTO.getName() == "" ? user.getName() : userGetDTO.getName() )
                .work(userGetDTO.getWork() == "" ?  user.getWork() : userGetDTO.getWork() )
                .username(user.getUsername())
                .password(password)
                .child(child)
                .build();

        child.setUser(userSave);

        userRepository.save(userSave);

        ResponseUserDTO response = ResponseUserDTO.builder()
                .token(jwtService.generateToken(user))
                .id(user.getId())
                .name(userGetDTO.getName())
                .username(user.getUsername())
                .work(userGetDTO.getWork())
                .idChild(child.getId())
                .nameChild(child.getName())
                .ageChild(child.getAge())
                .genderChild(child.getGender())
                .levelTEA(child.getLevelTEA())
                .build();
        return response;
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Recurso no encontrada" ));
    }

    public void sendCodeToEmail(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new BusinessException(HttpStatus.NOT_FOUND, "No se encontro el usuario con el email" )
                );
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expired = now.plusMinutes(5);
        Integer numberRandom = ThreadLocalRandom.current().nextInt(100000, 1000000);
        Code code = Code.builder()
                .code(numberRandom.toString())
                .expirationDate(expired)
                .user(user)
                .build();
        codeRepository.save(code);
        mailSend.sendMail(username,
                "Codigo de verificación",
                "Tu codigo es: " + numberRandom.toString() + "\n" +
                "Tiene una duracion 5 minutos \n" + "No responder este correo");
    }

    public boolean validateCodeToEmail(CodeDTO codeDTO){
        User user = userRepository.findByUsername(codeDTO.getUsername())
                .orElseThrow(
                        () -> new BusinessException(HttpStatus.NOT_FOUND, "No se encontro el usuario con el email" )
                );
        LocalDateTime now = LocalDateTime.now();

        Code code = codeRepository.findByUser_UsernameAndCodeAndExpirationDateAfter(codeDTO.getUsername(),codeDTO.getCode(),now)
                .orElseThrow(
                        () -> new BusinessException(HttpStatus.UNAUTHORIZED, "Codigo incorrecto o ya vencio")
                );
        code.setExpirationDate(LocalDateTime.now().minusMinutes(10));
        codeRepository.save(code);
        return true;
    }

    public void changePassword(LoginRequestDTO requestDTO){
        User user = userRepository.findByUsername(requestDTO.getUsername())
                .orElseThrow(
                        () -> new BusinessException(HttpStatus.NOT_FOUND, "No se encontro el usuario con el email" )
                );
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        userRepository.save(user);
    }


//    public User createUser(User user){
//        if(userRepository.existsByUsername(user.getEmail())){
//            throw new BusinessException(ApiError.RESOURCE_ALREADY_EXISTS);
//        }
//        return userRepository.save(user);
//    }

}
