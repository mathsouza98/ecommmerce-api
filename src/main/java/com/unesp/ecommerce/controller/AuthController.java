package com.unesp.ecommerce.controller;

import com.unesp.ecommerce.payload.request.LoginRequest;
import com.unesp.ecommerce.payload.request.SignupRequest;
import com.unesp.ecommerce.payload.response.JwtResponse;
import com.unesp.ecommerce.payload.response.MessageResponse;
import com.unesp.ecommerce.repository.LegalUserRepository;
import com.unesp.ecommerce.repository.PhysicalUserRepository;
import com.unesp.ecommerce.repository.UserRepository;
import com.unesp.ecommerce.security.jwt.JwtUtils;
import com.unesp.ecommerce.security.services.UserDetailsImpl;
import com.unesp.ecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PhysicalUserRepository physicalUserRepository;

    @Autowired
    LegalUserRepository legalUserRepository;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(HttpServletResponse response, @Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        response.addHeader("Authorization", "Bearer" + " " + jwt);

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if ( signUpRequest.getCpf() != null ) {
            if(physicalUserRepository.existsByCpf(signUpRequest.getCpf())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Cpf already taken!"));
            } else {
                return userService.signupPhysicalUser(signUpRequest);
            }
        }

        if ( signUpRequest.getCnpj() != null ) {
            if(legalUserRepository.existsByCnpj(signUpRequest.getCnpj())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Cnpj already taken!"));
            } else {
                return userService.signupLegalUser(signUpRequest);
            }
        }

        return ResponseEntity.badRequest().body(new MessageResponse("Error trying to register user!"));
    }
}
