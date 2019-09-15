package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.domain.Orders;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {

	Orders findByid(int id);
}
