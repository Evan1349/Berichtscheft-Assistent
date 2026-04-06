package com.example.demo.entities;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.enumeration.BenutzerRolle;

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
public class Benutzer extends Auditable {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable= false)
	private String vorname;
	
	@Column(nullable= false)
	private String nachname;
	
	@Enumerated(EnumType.STRING)
	private BenutzerRolle rolle;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ausbilder_id")
    private Benutzer ausbilder;
	
    @Builder.Default
    @OneToMany(mappedBy = "ausbilder")
    private List<Benutzer> azubis = new ArrayList<>();
	
	@Builder.Default
    @OneToMany(mappedBy = "azubi")
    private List<Berichtsheft> meineBerichte = new ArrayList<>();
	
	@Builder.Default
    @OneToMany(mappedBy = "pruefer")
    private List<Berichtsheft> zuPruefendeBerichte = new ArrayList<>();

}
