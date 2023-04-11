package com.readingisgood.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.readingisgood.exception.PromptMessages;
import com.readingisgood.payload.BookOrderPayload;
import com.readingisgood.service.BookOrderService;

@RestController
@CrossOrigin
@RequestMapping("/order")
public class BookOrderController {

	@Autowired
	private BookOrderService bookOrderService;

	@PostMapping("/create")
	public ResponseEntity<?> makeBookOrder(@RequestBody BookOrderPayload bookOrderPayload, Principal principal) {
		if (bookOrderPayload == null) {
			return new ResponseEntity<String>(PromptMessages.MISSING_BODY, HttpStatus.BAD_REQUEST);
		}

		return this.bookOrderService.createBookOrder(bookOrderPayload);
	}

	@GetMapping("/byid/{id}")
	public ResponseEntity<?> getOrderById(@PathVariable Long id, Principal principal) {
		if (id == null) {
			return new ResponseEntity<String>(PromptMessages.MISSING_BODY, HttpStatus.BAD_REQUEST);
		} else {
			return this.bookOrderService.getBookOrderById(id);
		}

	}

	@PostMapping("/bycustomerid")
	public ResponseEntity<?> getBookOrdersByCustomerId(@RequestBody BookOrderPayload bookOrderPayload,
			Principal principal) {
		return this.bookOrderService.getBookOrdersByCustomerId(bookOrderPayload);

	}

	@PostMapping("/bydate")
	public ResponseEntity<?> getBookOrdersByDate(@RequestBody BookOrderPayload bookOrderPayload, Principal principal) {
		return this.bookOrderService.getBookOrdersByDate(bookOrderPayload);
	}

}
