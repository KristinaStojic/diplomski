package com.example.posta.repository;

import com.example.posta.model.Payment;
import com.example.posta.model.Payoff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayoffRepository extends JpaRepository<Payoff, Long> {
}
