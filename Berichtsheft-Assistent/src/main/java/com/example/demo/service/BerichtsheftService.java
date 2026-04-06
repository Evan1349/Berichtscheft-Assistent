package com.example.demo.service;

import java.util.List;

import com.example.demo.dtos.BerichtsheftRequest;
import com.example.demo.dtos.BerichtsheftResponse;

public interface BerichtsheftService {

	public BerichtsheftResponse createBerichtsheft(Long benutzerId, BerichtsheftRequest request);

	List<BerichtsheftResponse> getBerichtshefte();

	BerichtsheftResponse updateBerichtsheft(Long berichtsheftId, BerichtsheftRequest request);

	void deleteBerichtsheft(Long berichtsheftId);
}
