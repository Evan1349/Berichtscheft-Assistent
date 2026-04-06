package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Berichtsheft;

@Repository
public interface BerichtsheftRepository extends JpaRepository<Berichtsheft, Long> {

	Optional<Berichtsheft> findById(Long id);

	List<Berichtsheft> findAllByDeletedFalse();
}
