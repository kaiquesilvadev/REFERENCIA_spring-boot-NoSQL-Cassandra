package com.kaique.cassandra.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaique.cassandra.model.dto.DepartmentDTO;
import com.kaique.cassandra.model.entities.Department;
import com.kaique.cassandra.repository.DepartmentRepository;

@Service
public class DepartmentService {

	@Autowired
	private DepartmentRepository repository;
	
	public List<DepartmentDTO> findAll() {
		List<Department> list = repository.findAll();
		return list.stream().map(x -> new DepartmentDTO(x)).collect(Collectors.toList());
	}
	
	private Department getById(UUID id) {
		Optional<Department> result = repository.findById(id);
		return result.orElseThrow(() -> new ResourceNotFoundException("Id não encontrado"));
	}
	
	public DepartmentDTO findById(UUID id) {
		Department entity = getById(id);
		return new DepartmentDTO(entity);
	}
}	
