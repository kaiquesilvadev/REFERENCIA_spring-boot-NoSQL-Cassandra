package com.kaique.cassandra.repository;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.kaique.cassandra.model.entities.Department;

public interface DepartmentRepository extends CassandraRepository<Department, UUID> {

}
