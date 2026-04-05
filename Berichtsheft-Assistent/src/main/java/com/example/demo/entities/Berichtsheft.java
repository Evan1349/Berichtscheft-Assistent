package com.example.demo.entities;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.demo.enumeration.BerichtsheftStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="T_BERICHTSHEFT")
public class Berichtsheft {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private int nachweisNummer;
	
	private int ausbildungsjahr;
	
	private LocalDate wochenStart;
	
	private LocalDate wochenEnde;
	
	private Instant submittedAt;
	
	private Instant approvedAt;
	
	private Instant changeRequestAt;
	
	@Enumerated(EnumType.STRING)
	private BerichtsheftStatus status;
	
	@Builder.Default
	@OneToMany(mappedBy = "berichtsheft", 
			cascade = CascadeType.ALL, 
			orphanRemoval = true,
			fetch = FetchType.LAZY)
	private List<Aufgaben> aufgaben = new ArrayList<>();
	
	@Builder.Default
    @ManyToMany(mappedBy = "berichtsheft")
    private Set<Benutzer> benutzer= new HashSet<>();
	
	@OneToMany(mappedBy = "berichtsheft", 
			cascade = CascadeType.ALL, 
			orphanRemoval = true,
			fetch = FetchType.LAZY)
	private List<Arbeitsablauf> arbeitslaufe;
    
	
}
