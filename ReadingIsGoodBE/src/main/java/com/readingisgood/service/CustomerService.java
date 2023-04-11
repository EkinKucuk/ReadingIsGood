package com.readingisgood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.readingisgood.exception.PromptMessages;
import com.readingisgood.model.Customer;
import com.readingisgood.payload.CustomerPaylaod;
import com.readingisgood.payload.LoginRequest;
import com.readingisgood.repository.CustomerRepository;

@Service
public class CustomerService implements UserDetailsService {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private CustomerRepository customerRepository;

	public ResponseEntity<?> createNewCustomer(CustomerPaylaod payload) {

		String email = payload.getEmail();
		String password = payload.getPassword();

		if (email == null || password == null) {
			return new ResponseEntity<String>(PromptMessages.EMPTY_EMAIL_OR_PASSWD, HttpStatus.BAD_REQUEST);
		} else {

			Customer check = this.findCustomerByEmail(email);
			if (check != null) {
				return new ResponseEntity<String>(PromptMessages.CUSTOMER_ALREADY_EXIST, HttpStatus.BAD_REQUEST);
			} else {
				Customer newCustomer = new Customer();

				newCustomer.setEmail(email);
				newCustomer.setPassword(bCryptPasswordEncoder.encode(password));
				newCustomer.setAddress(payload.getAddress());
				newCustomer.setName(payload.getName());
				newCustomer.setSurname(payload.getSurname());

				Customer saved = this.customerRepository.save(newCustomer);

				return new ResponseEntity<Customer>(saved, HttpStatus.OK);

			}

		}

	}

	public Customer findCustomerById(Long id) {
		return this.customerRepository.getCustomerById(id);
	}

	public Customer findCustomerByEmail(String email) {
		return this.customerRepository.getCustomerByEmail(email);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		return this.customerRepository.getCustomerByEmail(username);
	}

}
