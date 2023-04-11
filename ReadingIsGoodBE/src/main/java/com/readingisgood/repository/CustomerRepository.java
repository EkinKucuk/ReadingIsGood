package com.readingisgood.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.readingisgood.model.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
	
	@Query(value = "select * from customer where id = ?1",nativeQuery = true)	
	public Customer getCustomerById(Long id);
	
	@Query(value = "select * from customer where email = ?1",nativeQuery = true)
	public Customer getCustomerByEmail(String email);

	

}
