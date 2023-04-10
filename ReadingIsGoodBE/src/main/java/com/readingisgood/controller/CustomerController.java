package com.readingisgood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.readingisgood.exception.PromptMessages;
import com.readingisgood.payload.CustomerPaylaod;
import com.readingisgood.payload.JWTLoginSuccessResponse;
import com.readingisgood.payload.LoginRequest;
import com.readingisgood.security.JwtProvider;
import com.readingisgood.service.CustomerService;

@RestController
@CrossOrigin
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtProvider tokenProvider;

	private static final String TOKEN_PREFIX = "Bearer ";

	@PostMapping("/register")
	public ResponseEntity<?> registerCustomer(@RequestBody CustomerPaylaod customerPaylaod) {

		if (customerPaylaod != null) {
			return this.customerService.createNewCustomer(customerPaylaod);
		} else {
			return new ResponseEntity<String>(PromptMessages.MISSING_BODY, HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("/login")
	public ResponseEntity<?> authenticateCustomer(@RequestBody LoginRequest loginRequest) {
		if (loginRequest != null) {
			return new ResponseEntity<String>(PromptMessages.MISSING_BODY, HttpStatus.BAD_REQUEST);

		} else {
			String email = loginRequest.getEmail();
			String password = loginRequest.getPassword();
			if (email == null || password == null) {
				return new ResponseEntity<String>(PromptMessages.EMPTY_EMAIL_OR_PASSWD, HttpStatus.BAD_REQUEST);
			} else {

				Authentication authentication = authenticationManager
						.authenticate(new UsernamePasswordAuthenticationToken(email, password));

				SecurityContextHolder.getContext().setAuthentication(authentication);
				String jwt = TOKEN_PREFIX + tokenProvider.generateToken(authentication);

				return new ResponseEntity<JWTLoginSuccessResponse>(new JWTLoginSuccessResponse(true, jwt),
						HttpStatus.OK);

			}
		}

	}

}
