package com.unesp.ecommerce.services;

import com.unesp.ecommerce.model.*;
import com.unesp.ecommerce.payload.request.SignupRequest;
import com.unesp.ecommerce.payload.response.MessageResponse;
import com.unesp.ecommerce.repository.LegalUserRepository;
import com.unesp.ecommerce.repository.PhysicalUserRepository;
import com.unesp.ecommerce.repository.RoleRepository;
import com.unesp.ecommerce.repository.UserRepository;
import com.unesp.ecommerce.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PhysicalUserRepository physicalUserRepository;

    @Autowired
    LegalUserRepository legalUserRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    public ResponseEntity<MessageResponse> signupUser (SignupRequest signupRequest, Set<Role> roles) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if ( signupRequest.getCpf() != null ) {
            if(physicalUserRepository.existsByCpf(signupRequest.getCpf())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Cpf already taken!"));
            } else {
                return signupPhysicalUser(signupRequest, roles);
            }
        }

        if ( signupRequest.getCnpj() != null ) {
            if(legalUserRepository.existsByCnpj(signupRequest.getCnpj())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Cnpj already taken!"));
            } else {
                return signupLegalUser(signupRequest, roles);
            }
        }

        return ResponseEntity.badRequest().body(new MessageResponse("Error trying to register user!"));
    }

    public ResponseEntity<MessageResponse> signupPhysicalUser (SignupRequest signupRequest, Set<Role> roles) {

        PhysicalUser physicalUser = new PhysicalUser(
            signupRequest.getUsername(),
            encoder.encode(signupRequest.getPassword()),
            signupRequest.getName(),
            signupRequest.getCpf()
        );

        physicalUser.setRoles(roles);

        physicalUserRepository.save(physicalUser);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    public ResponseEntity<MessageResponse> signupLegalUser (SignupRequest signupRequest, Set<Role> roles) {

        LegalUser legalUser = new LegalUser(
            signupRequest.getUsername(),
            encoder.encode(signupRequest.getPassword()),
            signupRequest.getName(),
            signupRequest.getCnpj()
        );

        legalUser.setRoles(roles);

        legalUserRepository.save(legalUser);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    public Set<Role> getSetOfRoleBySignupRequest(SignupRequest signupRequest) {
        Set<String> strRoles = signupRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        return roles;
    }

    public User getUserByAuthorization(String authorization) {
        User user = null;
        String username;

        if (authorization.startsWith("Bearer ")) {
            String jwtToken = authorization.substring(7);

            if (jwtUtils.validateJwtToken(jwtToken)) {
                username = jwtUtils.getUserNameFromJwtToken(jwtToken);
                user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Error: User is not found."));
            }
        }
        return user;
    }
}
