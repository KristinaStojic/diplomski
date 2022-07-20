package com.example.posta.repository;

import com.example.posta.model.PostOffice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostOfficeRepository extends JpaRepository<PostOffice, Long> {
}
