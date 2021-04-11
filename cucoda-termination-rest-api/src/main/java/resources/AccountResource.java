package resources;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import domain.Account;
import domain.User;

import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import services.AccountService;

@Path("/accounts")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class AccountResource {

	@Inject
	private AccountService accountRestService;

	@GET
	public Response getAccount() {
		return Response.ok("account").build();
	}

	@GET
	@Path("/users")
	public Response getAllOrganizers() {
		Set<User> users = accountRestService.getAllUsers();
		return Response.ok(users).build();
	}
	
	@GET
	@Path("/{provider}/{identifier}")
	public Response getAccount(
			@PathParam(value = "provider") String provider,
			@PathParam(value = "identifier") String identifier) {

		Optional<Account> accountOptional = accountRestService.getAccount(provider, identifier);
		if (accountOptional.isEmpty()) {
			return Response
					.status(Response.Status.NOT_FOUND)
					.entity(String.format("AccountNotFound for provider %s and identifier %s", provider, identifier))
					.build();
		}

		return Response.ok(accountOptional.get()).build();
	}

	@POST
	@Path("/{provider}/{identifier}")
	public Response createAccount(
			@PathParam(value = "provider") String provider,
			@PathParam(value = "identifier") String identifier) {

		Optional<Account> accountOptional = accountRestService.createAccount(provider, identifier);
		if (accountOptional.isEmpty()) {
			return Response
					.status(Response.Status.NOT_FOUND)
					.entity(String.format("AccountNotFound for provider %s and identifier %s", provider, identifier))
					.build();
		}

		return Response.ok(accountOptional.get()).build();
	}

}
