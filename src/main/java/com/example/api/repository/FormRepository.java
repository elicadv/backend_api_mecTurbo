package com.example.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api.model.Form;

public interface FormRepository extends JpaRepository<Form, Long> {
}

