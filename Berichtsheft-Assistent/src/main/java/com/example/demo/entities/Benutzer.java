package com.example.demo.entities;

import java.util.HashSet;
import java.util.Set;

import com.example.demo.enumeration.BenutzerRolle;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@Table(name ="T_BENUTZER")
public class Benutzer {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable= false)
	private String vorname;
	
	@Column(nullable= false)
	private String nachname;
	
	@Enumerated(EnumType.STRING)
	private BenutzerRolle rolle;
	
	@Builder.Default
	@ManyToMany
    @JoinTable(
            name = "benutzer_berichtsheft",
            joinColumns = @JoinColumn(name = "benutzer_id"),
            inverseJoinColumns = @JoinColumn(name = "berichtsheft_id")
        )
	private Set<Berichtsheft> berichtshefte = new HashSet<>();

}
