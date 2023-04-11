package com.readingisgood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.readingisgood.exception.PromptMessages;
import com.readingisgood.model.Book;
import com.readingisgood.model.BookOrder;
import com.readingisgood.payload.BookPayload;
import com.readingisgood.payload.BookStockPayload;
import com.readingisgood.repository.BookRepository;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;

	public ResponseEntity<?> registerNewBook(BookPayload bookPayload) {
		String isbn = bookPayload.getIsbn();
		if (isbn == null) {
			return new ResponseEntity<String>(PromptMessages.MISSING_ISBN, HttpStatus.BAD_REQUEST);

		} else {
			Book check = this.bookRepository.getBookByIsbn(isbn);
			if (check == null) {
				Book newBook = new Book();
				newBook.setAuthor(bookPayload.getAuthor());
				newBook.setName(bookPayload.getName());
				newBook.setPrice(bookPayload.getPrice());
				newBook.setStock(1);

				Book saved = this.bookRepository.save(newBook);

				return new ResponseEntity<Book>(saved, HttpStatus.OK);
			} else {
				check.setStock(check.getStock() + 1);
				Book updated = this.bookRepository.save(check);
				return new ResponseEntity<Book>(updated, HttpStatus.OK);
			}

		}

	}

	public ResponseEntity<?> orderBook(String isbn) {
		if (isbn == null) {
			return new ResponseEntity<String>(PromptMessages.MISSING_ISBN, HttpStatus.BAD_REQUEST);

		} else {
			Book check = this.bookRepository.getBookByIsbn(isbn);
			if (check == null) {
				return new ResponseEntity<String>(PromptMessages.BOOK_NOT_FOUND, HttpStatus.BAD_REQUEST);
			} else {
				if (check.getStock() == 1) {
					check.setStock(0);
					this.bookRepository.delete(check);
					return new ResponseEntity<Book>(check, HttpStatus.OK);

				} else {
					check.setStock(check.getStock() - 1);
					Book ordered = this.bookRepository.save(check);
					return new ResponseEntity<Book>(ordered, HttpStatus.OK);

				}

			}
		}

	}

	public Book orderBook(Long id) {
		Book check = this.bookRepository.findById(id).get();
		if (check == null) {
			return null;
		} else {
			if (check.getStock() == 1) {
				check.setStock(0);
				this.bookRepository.delete(check);
				return check;

			} else {
				check.setStock(check.getStock() - 1);
				Book ordered = this.bookRepository.save(check);
				return ordered;

			}
		}

	}

	public ResponseEntity<?> updateStockOfaBook(BookStockPayload bookStockPayload) {
		String isbn = bookStockPayload.getIsbn();
		if (isbn == null) {
			return new ResponseEntity<String>(PromptMessages.MISSING_ISBN, HttpStatus.BAD_REQUEST);

		} else {
			Book check = this.bookRepository.getBookByIsbn(isbn);
			if (check == null) {
				return new ResponseEntity<String>(PromptMessages.BOOK_NOT_FOUND, HttpStatus.BAD_REQUEST);
			} else {
				int newStock = bookStockPayload.getStock();
				check.setStock(newStock);
				Book updated = this.bookRepository.save(check);
				return new ResponseEntity<Book>(updated, HttpStatus.OK);
			}

		}

	}

}
