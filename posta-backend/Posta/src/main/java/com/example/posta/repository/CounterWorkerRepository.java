package com.example.posta.repository;

import com.example.posta.model.CounterWorker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CounterWorkerRepository extends JpaRepository<CounterWorker, Long> {
}
