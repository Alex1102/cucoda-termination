package boundary;

import domain.Termination;

import java.util.Random;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import services.TerminationService;


@Stateless
@RolesAllowed("Organizer")
public class TerminationBoundary implements TerminationService {

	@Inject
	EntityManager entityManager;

	public static final Random RANDOM = new Random();

	public Termination terminateContract(String contractNumber) {
		return null;
	}

	public Termination readTerminations(String contractNumber) {
		String terminationId = String.valueOf(RANDOM.nextInt());
		return Termination.builder()
				.id(terminationId)
				.build();
	}

}
