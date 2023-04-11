package com.readingisgood.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.readingisgood.exception.PromptMessages;
import com.readingisgood.payload.BookPayload;
import com.readingisgood.payload.BookStockPayload;
import com.readingisgood.service.BookService;

@RestController
@CrossOrigin
@RequestMapping("/book")
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	@PostMapping("/save")
	public ResponseEntity<?>saveNewBook(@RequestBody BookPayload bookPayload,Principal principal){
		if(bookPayload ==null) {
			return new ResponseEntity<String>(PromptMessages.MISSING_BODY,HttpStatus.BAD_REQUEST);
		}else {
			return this.bookService.registerNewBook(bookPayload);
		}
		
	}
	
	@PostMapping("/updatestock")
	public ResponseEntity<?> updateBookStock(@RequestBody BookStockPayload bookStockPayload,Principal principal){
		if(bookStockPayload == null) {
			return new ResponseEntity<String>(PromptMessages.MISSING_BODY,HttpStatus.BAD_REQUEST);
		}else {
			return this.bookService.updateStockOfaBook(bookStockPayload);
		}
	}
	
	
	

}
