package com.readingisgood.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.readingisgood.model.Book;


@Repository
public interface BookRepository extends CrudRepository<Book, Long>{
	
	@Query(value = "select * from book where isbn = ?1",nativeQuery = true)	
	public Book getBookByIsbn(String isbn);

}
