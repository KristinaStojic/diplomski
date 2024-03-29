package com.example.posta.repository;

import com.example.posta.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
    Worker findByEmail(String userEmail);
}
