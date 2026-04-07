package com.example.demo.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Benutzer;
import com.example.demo.enumeration.BenutzerRolle;

@Repository
public interface BenutzerRepository extends JpaRepository<Benutzer, Long> {

	Optional<Benutzer> findByIdAndRolleAndIsDeletedFalse(Long id, BenutzerRolle rolle);
	Optional<Benutzer> findByIdAndIsDeletedFalse(Long id);
}
