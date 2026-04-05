package com.example.demo.service;

import java.util.List;

import com.example.demo.dtos.AufgabenRequest;
import com.example.demo.dtos.AufgabenResponse;

public interface AufgabenService {
	
	public AufgabenResponse createAufgaben(AufgabenRequest request);
	
	public List<AufgabenResponse> getTageAufgaben();
	
	public AufgabenResponse updateAufgaben();
	
	public void deleteAufgaben();

}
