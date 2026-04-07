package com.example.demo.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Benutzer;

@Repository
public interface BenutzerRepository extends JpaRepository<Benutzer, Long> {

	Optional<Benutzer> findByIdAndIsDeletedFalse(Long id);
	Optional<Benutzer> findByEmailAndIsDeletedFalse(String email);
}
