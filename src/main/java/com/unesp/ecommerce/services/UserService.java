package com.unesp.ecommerce.services;

import com.unesp.ecommerce.model.ERole;
import com.unesp.ecommerce.model.LegalUser;
import com.unesp.ecommerce.model.PhysicalUser;
import com.unesp.ecommerce.model.Role;
import com.unesp.ecommerce.payload.request.SignupRequest;
import com.unesp.ecommerce.payload.response.MessageResponse;
import com.unesp.ecommerce.repository.LegalUserRepository;
import com.unesp.ecommerce.repository.PhysicalUserRepository;
import com.unesp.ecommerce.repository.RoleRepository;
import com.unesp.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
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

    public ResponseEntity<MessageResponse> signupPhysicalUser (SignupRequest signupRequest) {

        PhysicalUser physicalUser = new PhysicalUser(
            signupRequest.getUsername(),
            encoder.encode(signupRequest.getPassword()),
            signupRequest.getCpf()
        );

        physicalUser.setRoles(getSetRoleBySignupRequest(signupRequest));

        physicalUserRepository.save(physicalUser);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    public ResponseEntity<MessageResponse> signupLegalUser (SignupRequest signupRequest) {

        LegalUser legalUser = new LegalUser(
            signupRequest.getUsername(),
            encoder.encode(signupRequest.getPassword()),
            signupRequest.getCnpj()
        );

        legalUser.setRoles(getSetRoleBySignupRequest(signupRequest));

        legalUserRepository.save(legalUser);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    public Set<Role> getSetRoleBySignupRequest(SignupRequest signupRequest) {
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
}
