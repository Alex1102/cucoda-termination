package boundary;

import domain.Account;
import domain.Organizer;
import domain.User;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import services.AccountService;


@Stateless
public class AccountBoundary implements AccountService {

	@Inject
	EntityManager entityManager;

	private Set<Account> accounts = new HashSet<>();

	@Override
	@PermitAll
	public Set<User> getAllUsers() {
		TypedQuery<Organizer> query = entityManager.createNamedQuery(Organizer.findAll, Organizer.class);
		List<Organizer> organizers = query.getResultList();
		return toUsers(organizers);
	}

	private Set<User> toUsers(List<Organizer> organizers) {
		return organizers.stream()
				.map(organizer -> toUser(organizer))
				.collect(Collectors.toSet());
	}

	private User toUser(Organizer organizer) {
		return User.builder()
				.email(organizer.getEmail())
				.firstName(organizer.getFirstName())
				.lastName(organizer.getLastName())
				.build();
	}

	@Override
	@RolesAllowed("Organizer")
	public Optional<Account> getAccount(String provider, String identifier) {
		return accounts.stream()
				.filter(account -> account.getProvider().equals(provider))
				.filter(account -> account.getIdentifier().equals(identifier))
				.findFirst();
	}

	@Override
	@RolesAllowed("Organizer")
	public Optional<Account> createAccount(String provider, String identifier) {
		accounts.add(Account.builder()
				.provider(provider)
				.identifier(identifier)
				.build());
		return getAccount(provider, identifier);
	}




	//	// TODO aw: Teste ob es so mit der Authorizierung funktioniert und Security
	//	@Context
	//	HttpRequest httpRequest;
	//	@Context
	//	HttpResponse httpResponse;
	//	@Context
	//	private SecurityContext securityContext;
	//
	//	public Account createAccount(String provider, String identifier) {
	//		//			securityContext.isUserInRole();
	//		//			securityContext.getUserPrincipal()
	//
	//		securityContext.isCallerInRole("XYZ");
	//		securityContext.getCallerPrincipal();
	//
	//		return Account.builder()
	//				.provider(provider)
	//				.identifier(identifier)
	//				.build();
	//	}

}
