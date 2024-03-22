package com.kaique.cassandra.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import com.kaique.cassandra.model.entities.Product;

public interface ProductRepository extends CassandraRepository<Product, UUID> {
	
	@AllowFiltering
	List<Product> findByDepartment(String department);
}
