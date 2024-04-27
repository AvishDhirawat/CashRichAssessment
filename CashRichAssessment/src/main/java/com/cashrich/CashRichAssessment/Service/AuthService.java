package com.cashrich.CashRichAssessment.Service;

import com.cashrich.CashRichAssessment.Config.TokenProvider;
import com.cashrich.CashRichAssessment.Entity.User;
import com.cashrich.CashRichAssessment.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final TokenProvider tokenProvider;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;


    public AuthService(UserRepository userRepository, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
    }

    public String authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // Validate password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        // Generate authentication token
        return tokenProvider.generateToken(user.getId());
    }

    public Long getUserIdFromToken(String token) {
        return tokenProvider.getUserIdFromToken(token);
    }
}
