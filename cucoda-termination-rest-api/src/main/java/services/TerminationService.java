package services;

import domain.Termination;

public interface TerminationService {

	Termination readTerminations(String contractNumber);

}
