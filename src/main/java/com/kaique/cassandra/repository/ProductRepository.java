package com.kaique.cassandra.repository;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.kaique.cassandra.model.entities.Product;

public interface ProductRepository extends CassandraRepository<Product, UUID> {
}
