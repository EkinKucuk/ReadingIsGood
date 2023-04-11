package com.readingisgood.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.readingisgood.model.BookOrder;

@Repository
public interface BookOrderRepository extends PagingAndSortingRepository<BookOrder, Long> {

	@Query(value = "select * from book_order where customer_id = ?1", nativeQuery = true)
	Page<BookOrder> findBookOrdersByCustomerId(Long customerId, Pageable pageable);
	
	@Query(value = "select * from book_order where order_date BETWEEN :startDate AND :endDate",nativeQuery = true)
	public List<BookOrder> findBookOrdersBetweenDates(@Param("startDate")Date startDate,@Param("endDate")Date endDate);

}
