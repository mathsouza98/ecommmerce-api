package com.unesp.ecommerce.services;

import com.unesp.ecommerce.model.*;
import com.unesp.ecommerce.payload.request.SignupRequest;
import com.unesp.ecommerce.payload.response.MessageResponse;
import com.unesp.ecommerce.repository.*;
import com.unesp.ecommerce.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
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
    AddressRepository addressRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    public ResponseEntity<MessageResponse> signupUser (SignupRequest signupRequest, Set<Role> roles) {
        String _userType = signupRequest.getUserType();

        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        switch (_userType) {
            case "fisica":
                if(physicalUserRepository.existsByCpf(signupRequest.getCpf_cnpj())) {
                    return ResponseEntity
                            .badRequest()
                            .body(new MessageResponse("Error: Cpf already taken!"));
                }

                return signupPhysicalUser(signupRequest, roles);

            case "juridica":
                if(legalUserRepository.existsByCnpj(signupRequest.getCpf_cnpj())) {
                    return ResponseEntity
                            .badRequest()
                            .body(new MessageResponse("Error: Cnpj already taken!"));
                }

                return signupLegalUser(signupRequest, roles);
        }

        return ResponseEntity.badRequest().body(new MessageResponse("Error trying to register user!"));
    }

    public ResponseEntity<MessageResponse> signupPhysicalUser (SignupRequest signupRequest, Set<Role> roles) {
        Contact contact = new Contact(
                signupRequest.getContact().getHomePhone(),
                signupRequest.getContact().getCommercialPhone(),
                signupRequest.getContact().getCellPhone(),
                signupRequest.getContact().getEmail()
        );

        PhysicalUser physicalUser = new PhysicalUser(
            signupRequest.getUsername(),
            encoder.encode(signupRequest.getPassword()),
            contact,
            signupRequest.getName(),
            signupRequest.getCpf_cnpj()
        );

        physicalUser.setRoles(roles);

        physicalUserRepository.save(physicalUser);

        Address address = new Address(
                physicalUser.getId(),
                signupRequest.getAddress().getStreet(),
                signupRequest.getAddress().getNumber(),
                signupRequest.getAddress().getNeighborhood(),
                signupRequest.getAddress().getZip(),
                signupRequest.getAddress().getCity(),
                signupRequest.getAddress().getFedUnit()
        );

        addressRepository.save(address);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    public ResponseEntity<MessageResponse> signupLegalUser (SignupRequest signupRequest, Set<Role> roles) {
        Contact contact = new Contact(
            signupRequest.getContact().getHomePhone(),
            signupRequest.getContact().getCommercialPhone(),
            signupRequest.getContact().getCellPhone(),
            signupRequest.getContact().getEmail()
        );

        LegalUser legalUser = new LegalUser(
            signupRequest.getUsername(),
            encoder.encode(signupRequest.getPassword()),
            contact,
            signupRequest.getName(),
            signupRequest.getCpf_cnpj()
        );

        legalUser.setRoles(roles);

        legalUserRepository.save(legalUser);

        Address address = new Address(
                legalUser.getId(),
                signupRequest.getAddress().getStreet(),
                signupRequest.getAddress().getNumber(),
                signupRequest.getAddress().getNeighborhood(),
                signupRequest.getAddress().getZip(),
                signupRequest.getAddress().getCity(),
                signupRequest.getAddress().getFedUnit()
        );

        addressRepository.save(address);

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

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
