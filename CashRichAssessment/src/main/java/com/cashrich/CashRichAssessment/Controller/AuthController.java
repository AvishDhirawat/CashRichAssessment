package com.cashrich.CashRichAssessment.Controller;

import com.cashrich.CashRichAssessment.DTO.ApiResponse;
import com.cashrich.CashRichAssessment.DTO.LoginRequest;
import com.cashrich.CashRichAssessment.DTO.ThirdPartyApiRequest;
import com.cashrich.CashRichAssessment.DTO.UpdateUserRequest;
import com.cashrich.CashRichAssessment.Service.AuthService;
import com.cashrich.CashRichAssessment.Service.ThirdPartyApiService;
import com.cashrich.CashRichAssessment.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private ThirdPartyApiService thirdPartyApiService;

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        if (loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
            return ResponseEntity.badRequest().body("Username and password are required");
        }

//        if (!request.getHeader("X-Api-Origin").equals("your_predefined_value")) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid API origin");
//        }

        String token = authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());

        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }

        return ResponseEntity.ok().body(token);
    }

    @PutMapping("/users/update")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserRequest updateUserRequest,@AuthenticationPrincipal Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }

        Long userId = authService.getUserIdFromToken(principal.getName());

        userService.updateUser(userId, updateUserRequest);

        return ResponseEntity.ok().body("User data updated successfully");
    }

    @GetMapping("/third-party-api")
    public ResponseEntity<?> callThirdPartyApi(@RequestBody ThirdPartyApiRequest request, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }

        Long userId = authService.getUserIdFromToken(principal.getName());

        ApiResponse response = thirdPartyApiService.callApi(request);

        thirdPartyApiService.saveRequestResponse(userId, request, response);

        return ResponseEntity.ok().body(response);
    }
}
