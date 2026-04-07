package com.example.demo.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Aufgaben;

@Repository
public interface AufgabenRepository extends JpaRepository<Aufgaben, Long> {
	
	Optional<Aufgaben> findByIdAndIsDeletedFalse(Long id);
	List<Aufgaben> findByBerichtsheftIdAndIsDeletedFalse(Long berichtsheftId);

}
