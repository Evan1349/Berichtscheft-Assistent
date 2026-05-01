package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Arbeitsablaeufe;


@Repository
public interface ArbeitsablaeufeRepository extends JpaRepository<Arbeitsablaeufe, Long> {
	
	List<Arbeitsablaeufe> findByBerichtsheftIdAndIsDeletedFalse(Long berichtsheftId);

}
