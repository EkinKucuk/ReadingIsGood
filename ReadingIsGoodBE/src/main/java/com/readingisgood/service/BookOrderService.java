package com.readingisgood.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.readingisgood.exception.PromptMessages;
import com.readingisgood.model.Book;
import com.readingisgood.model.BookOrder;
import com.readingisgood.model.Customer;
import com.readingisgood.payload.BookOrderPayload;
import com.readingisgood.repository.BookOrderRepository;
import com.readingisgood.repository.CustomerRepository;

@Service
public class BookOrderService {

	@Autowired
	private BookOrderRepository bookOrderRepository;

	@Autowired
	private BookService bookService;

	@Autowired
	private CustomerRepository customerRepository;

	public ResponseEntity<?> getBookOrdersByCustomerId(BookOrderPayload bookOrderPayload) {

		int page = bookOrderPayload.getPage();
		int size = bookOrderPayload.getSize();
		Long customerId = bookOrderPayload.getCustomerId();

		Pageable paging = PageRequest.of(page, size);
		Page<BookOrder> bookOrders = this.bookOrderRepository.findBookOrdersByCustomerId(customerId, paging);

		return new ResponseEntity<Page<BookOrder>>(bookOrders, HttpStatus.OK);

	}

	public ResponseEntity<?> createBookOrder(BookOrderPayload bookOrderPayload) {
		
		List<Long> bookIds = bookOrderPayload.getBookIds();
		List<Book> books = new ArrayList<Book>();
		Long customerId = bookOrderPayload.getCustomerId();
		Customer customer = this.customerRepository.getCustomerById(customerId);
		BookOrder newBookOrder = new BookOrder();
		
		for (Long bookId : bookIds) {
			Book found = bookService.orderBook(bookId);
			if (found == null) {
				return new ResponseEntity<String>(PromptMessages.BOOK_NOT_FOUND, HttpStatus.BAD_REQUEST);
			} else {
				books.add(found);
			}
		}

		Date orderDate = new Date();

		newBookOrder.setBooks(books);
		newBookOrder.setOrderDate(orderDate);
		newBookOrder.setCustomer(customer);

		BookOrder created = this.bookOrderRepository.save(newBookOrder);

		return new ResponseEntity<BookOrder>(created, HttpStatus.OK);

	}
	
	public ResponseEntity<?> getBookOrdersByDate(BookOrderPayload bookOrderPayload){
		Date startDate = bookOrderPayload.getStartDate();
		Date endDate = bookOrderPayload.getEndDate();
		
		List<BookOrder> bookOrders = this.bookOrderRepository.findBookOrdersBetweenDates(startDate, endDate);
		
		return new ResponseEntity<List<BookOrder>>(bookOrders,HttpStatus.OK);
		
	}
	
	public ResponseEntity<?> getBookOrderById(Long id){
		
		BookOrder bookOrder = this.bookOrderRepository.findById(id).get();
		
		return new ResponseEntity<BookOrder>(bookOrder,HttpStatus.OK);
	}

}
