package com.example.demo.entities;

import java.time.Instant;

import com.example.demo.enumeration.BerichtsheftStatus;

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
@Table(name="T_BERICHTSHEFT_HISTORIE")
public class Arbeitsablauf {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "akteur_id", nullable = false)
	private Benutzer akteur;
	
	@Column(nullable = false, updatable = false)
	private Instant zeitpunkt;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private BerichtsheftStatus status;
	
	@Column(columnDefinition ="TEXT")
	private String comment;

	@ManyToOne
	@JoinColumn(name="berichtsheftId", nullable = false)
	private Berichtsheft berichtsheft;
	
}
