package com.example.demo.service;

import java.util.List;

import com.example.demo.dtos.AufgabenRequest;
import com.example.demo.dtos.AufgabenResponse;

public interface AufgabenService {
	
	public AufgabenResponse createAufgaben(AufgabenRequest request, Long berichtsheftId);
	
	public List<AufgabenResponse> getTageAufgaben(Long berichtsheftId) ;
	
	public AufgabenResponse updateAufgaben(AufgabenRequest request, Long aufgabenId);
	
	public void deleteAufgaben(Long aufgabenId);

}
