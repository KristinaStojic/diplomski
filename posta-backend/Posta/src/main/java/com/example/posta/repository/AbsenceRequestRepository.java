package com.example.posta.repository;

import com.example.posta.model.AbsenceRequest;
import com.example.posta.model.AccountingWorker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbsenceRequestRepository extends JpaRepository<AbsenceRequest, Long> {
}
