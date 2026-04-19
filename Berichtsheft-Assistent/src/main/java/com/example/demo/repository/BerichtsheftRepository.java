package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Berichtsheft;

@Repository
public interface BerichtsheftRepository extends JpaRepository<Berichtsheft, Long> {

	Optional<Berichtsheft> findByIdAndIsDeletedFalse(Long id);
	
	boolean existsByAzubiIdAndJahrAndKw(Long id, int jahr, int kw);

	List<Berichtsheft> findAllByAzubiIdAndIsDeletedFalse(Long id);
	
	@Query("SELECT b FROM Berichtsheft b JOIN b.azubi a WHERE a.ausbilder.id = :ausbilderId AND b.deleted = false")
	List<Berichtsheft> findAllByAusbilderIdAndIsDeletedFalse(@Param("ausbilderId")Long id);

	List<Berichtsheft> findAllByIsDeletedFalse();
}
