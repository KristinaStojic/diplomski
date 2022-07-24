package com.example.posta.repository;

import com.example.posta.model.Postman;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostmanRepository extends JpaRepository<Postman, Long> {
}
