package com.readingisgood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.readingisgood.model.Book;
import com.readingisgood.payload.BookPayload;
import com.readingisgood.repository.BookRepository;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;

	public ResponseEntity<?> registerNewBook(BookPayload bookPayload) {

		Book newBook = new Book();
		newBook.setAuthor(bookPayload.getAuthor());
		newBook.setName(bookPayload.getName());
		newBook.setPrice(bookPayload.getPrice());

		Book saved = this.bookRepository.save(newBook);

		return new ResponseEntity<Book>(saved, HttpStatus.OK);

	}

}
