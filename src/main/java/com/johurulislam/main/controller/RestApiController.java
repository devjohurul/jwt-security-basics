package com.johurulislam.main.controller;

import com.johurulislam.main.model.User;
import com.johurulislam.main.service.UserService;
import com.johurulislam.main.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestApiController {
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public RestApiController(JwtUtil jwtUtil, UserService userService, AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }
    @PostMapping("/user-register")
    public ResponseEntity<?> userRegister(@RequestBody User user){
        try {
            User existingUser=userService.findByUsername(user.getName());
            if(existingUser != null) return ResponseEntity.badRequest().body("User already Exists!");
            userService.saveUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(Exception ex){
            return ResponseEntity.badRequest().body("bad request");
        }
    }
    @PostMapping("/admin-register")
    public ResponseEntity<?> adminRegister(@RequestBody User user){
        try {
            User existingUser=userService.findByUsername(user.getName());
            if(existingUser != null) return ResponseEntity.badRequest().body("User already Exists!");
            userService.saveUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(Exception ex){
            return ResponseEntity.badRequest().body("bad request");
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody User user) {
        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    user.getUsername(), user.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Incorrect credentials!", HttpStatus.BAD_REQUEST);
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtUtil.generateToken(userDetails);

        return new ResponseEntity<>(jwt, HttpStatus.OK);
    }
    @GetMapping("/user-dashboard")
    public String userDashboard(){
        return "Welcome to the user dashboard";
    }
    @GetMapping("/admin-dashboard")
    public String adminDashboard(){
        return "Welcome to the admin dashboard";
    }
}
