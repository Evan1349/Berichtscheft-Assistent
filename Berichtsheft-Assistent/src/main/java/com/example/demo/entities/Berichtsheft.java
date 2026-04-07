package com.example.demo.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.enumeration.BerichtsheftStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
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
public class Berichtsheft extends Auditable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "Nummer", nullable = false)
	private Integer nachweisNummer;
	
	@Column(name = "Ausbildungsjahr", nullable = false)
	private Integer ausbildungsjahr;
	
	@NotNull
	private LocalDate wochenStart;
	
	@NotNull
	private LocalDate wochenEnde;
	
	@NotNull
	private Integer Jahr;
	
	@NotNull
	private Integer Kw;
	
	@ManyToOne
    @JoinColumn(name = "azubi_id", nullable = false)
    private Benutzer azubi;
	
	@ManyToOne
    @JoinColumn(name = "pruefer_id")
    private Benutzer pruefer;
	
	@Builder.Default
	@Enumerated(EnumType.STRING)
	private BerichtsheftStatus status = BerichtsheftStatus.ENTWURF;
	
	@Builder.Default
	@OneToMany(mappedBy = "berichtsheft", 
			cascade = CascadeType.ALL, 
			fetch = FetchType.LAZY)
	private List<Arbeitsablaeufe> arbeitsablaeufe = new ArrayList<>();
	
	@Builder.Default
	@OneToMany(mappedBy = "berichtsheft", 
			cascade = CascadeType.ALL, 
			orphanRemoval = true,
			fetch = FetchType.LAZY)
	private List<Aufgaben> aufgaben = new ArrayList<>();
    
}
