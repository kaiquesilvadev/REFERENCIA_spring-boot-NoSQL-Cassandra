package com.kaique.cassandra.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaique.cassandra.exceptions.ResourceNotFoundException;
import com.kaique.cassandra.model.dto.ProductDTO;
import com.kaique.cassandra.model.entities.Product;
import com.kaique.cassandra.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;
	
	public ProductDTO findById(UUID id) {
		Product entity = getById(id);
		return new ProductDTO(entity);
	}
	
	private Product getById(UUID id) {
		Optional<Product> result = repository.findById(id);
		return result.orElseThrow(() -> new ResourceNotFoundException("Id não encontrado"));
	}
	
	public List<ProductDTO> findByDepartment(String department) {
		List<Product> list;
		if ("".equals(department)) {
			list = repository.findAll();
		}
		else {
			list = repository.findByDepartment(department);
		}
		return list.stream().map(x -> new ProductDTO(x)).collect(Collectors.toList());
	}

}
