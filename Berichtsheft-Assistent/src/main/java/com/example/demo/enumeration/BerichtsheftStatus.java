package com.example.demo.enumeration;


public enum BerichtsheftStatus {
	ENTWURF,
	EINGEREICHT,
	AENDERUNG_ERFORDERLICH,
	GENEHMIGT;
	
    public boolean canBeSubmitted() {
        return this == ENTWURF || this == AENDERUNG_ERFORDERLICH;
    }
}
