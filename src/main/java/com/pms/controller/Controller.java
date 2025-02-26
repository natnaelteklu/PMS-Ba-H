package com.pms.controller;

import java.text.SimpleDateFormat;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pms.dto.AuthRequest;
import com.pms.dto.AuthResponse;
import com.pms.dto.RefreshTokenRequest;
import com.pms.entity.RefreshToken;
import com.pms.entity.Session;
import com.pms.entity.UserInfo;
import com.pms.repository.SessionDao;
import com.pms.repository.UserInfoRepository;
import com.pms.service.JwtService;
import com.pms.service.RefreshTokenService;

@RestController
@RequestMapping("/pms")
public class Controller {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserInfoRepository repository;

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private RefreshTokenService refreshTokenService;
    
    @Autowired
    SessionDao sessionDao;

    @Value("${max-login-attempts}")
    private int maxLoginAttempts;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping("/authenticate")
    public AuthResponse authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            if (authentication.isAuthenticated()) {
            	
                String loginTimeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss aa").format(new java.util.Date());
                String logOutTimeStamp = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
                RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequest.getUsername());
                
                String token = jwtService.generateToken(authRequest.getUsername());
                UserInfo users = repository.findByUsername(authRequest.getUsername());

                if (users.getUserStatus() == 0) {
                    throw new UsernameNotFoundException("User is disabled!");
                }

                // Reset invalid login attempts on successful login
                users.setInvalidLoginTrails(0);
                
                users.setLastLoginDate(loginTimeStamp);
                users.setLastLogoutDate(logOutTimeStamp);
                users.setInvalidLoginTrails(0);
                repository.save(users);
                
                sessionDao.deleteByuserName(authRequest.getUsername());

                Session saveSession = new Session();
                saveSession.setJwtToken(token);
                saveSession.setSessionId(token);
                saveSession.setUserName(authRequest.getUsername());
                sessionDao.save(saveSession);
                

                return new AuthResponse(users, token, refreshToken.getRefreshToken());
            }
        } catch (Exception ex) {
            System.out.println("Authentication failed for user: " + authRequest.getUsername());
        }

        // Handle failed login
        UserInfo user = repository.findByUsername(authRequest.getUsername());
        if (user == null) {
            System.out.println("User not found: " + authRequest.getUsername());
            throw new UsernameNotFoundException("User not found!");
        }

        System.out.println("Current invalid login trails: " + user.getInvalidLoginTrails());

        long trails = user.getInvalidLoginTrails();
        trails += 1;

        if (trails >= maxLoginAttempts) {
            System.out.println("User locked after " + maxLoginAttempts + " failed attempts.");
            user.setInvalidLoginTrails(0);
            user.setUserStatus(0);
        } else {
            user.setInvalidLoginTrails(trails);
        }

        repository.save(user);
        throw new UsernameNotFoundException("Invalid user request!");
    }

    
//    @PostMapping("/refreshToken")
//    public AuthResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
//        return refreshTokenService.findByToken(refreshTokenRequest.getToken())
//                .map(refreshTokenService::verifyExpiration)
//                .map(RefreshToken::getUserInfo)
//                .map(userInfo -> {
//                    String accessToken = jwtService.generateToken(userInfo.getUsername());
//                    return AuthResponse.builder()
//                            .accessToken(accessToken)
//                            .token(refreshTokenRequest.getToken())
//                            .build();
//                }).orElseThrow(() -> new RuntimeException(
//                        "Refresh token is not in database!"));
//    }
//    
    
    @PostMapping("/refreshToken")
    public AuthResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        Optional<RefreshToken> optionalToken = refreshTokenService.findByToken(refreshTokenRequest.getToken());

        if (optionalToken.isEmpty()) {
            throw new RuntimeException("Refresh token is not in database!");
        }

        RefreshToken refreshToken = optionalToken.get();
        refreshTokenService.verifyExpiration(refreshToken);

        UserInfo userInfo = refreshToken.getUserInfo();
        String accessToken = jwtService.generateToken(userInfo.getUsername());

        AuthResponse authResponse = new AuthResponse();
        authResponse.setDetails(userInfo);
        authResponse.setToken(accessToken);
        authResponse.setRefreshToken(refreshTokenRequest.getToken());

        return authResponse;
    }

//    @PostMapping("/refresh-token")
//    public ResponseEntity<?> refreshJwtToken(@RequestBody Map<String, String> request) {
//        String token = request.get("token");
//
//
//        if (jwtService.isTokenExpired(token)) {
//            System.out.println("Token is expired, rejecting refresh request.");
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is expired, please login again.");
//        } else if (jwtService.isTokenExpiringSoon(token)) {
//
//            String refreshedToken = jwtService.refreshToken(token);
//            
//            if (refreshedToken == null) {
//                System.out.println("The provided token is expired and cannot be refreshed.");
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("The provided token is expired and cannot be refreshed.");
//            }
//            
//            System.out.println("Token successfully refreshed.");
//            return ResponseEntity.ok(new AuthResponse(refreshedToken));
//        } else {
//            System.out.println("Token is still valid, no need for refresh.");
//            return ResponseEntity.status(HttpStatus.OK).body("Token is still valid.");
//        }
//    }
}
