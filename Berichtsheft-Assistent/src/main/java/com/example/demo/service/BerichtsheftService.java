package com.example.demo.service;

import java.util.List;

import com.example.demo.dtos.BerichtsheftRequest;
import com.example.demo.dtos.BerichtsheftResponse;

public interface BerichtsheftService {
	
	// CRUD
	public BerichtsheftResponse createBerichtsheft(BerichtsheftRequest request);

	public List<BerichtsheftResponse> getBerichtshefte();

	public BerichtsheftResponse updateBerichtsheft(Long berichtsheftId, BerichtsheftRequest request);

	public void deleteBerichtsheft(Long berichtsheftId);
	
	// Workflow
	public void einreichen(Long berichtsheftId);

	public void genehmigen(Long berichtsheftId);

	public void aenderungErforderlich(Long berichtsheftId);
}
