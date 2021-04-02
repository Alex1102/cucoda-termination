package service;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public interface TerminationService {

	@GET
	@Path("{contractNumber}/terminations")
	Response readTerminations();

	@GET
	@Path("{contractNumber}/terminations/options")
	Response terminationOptions();

	@POST
	@Path("{contractNumber}/terminations")
	Response terminateContract(String contractNumber);

	@PUT
	@Path("{contractNumber}/terminations/{terminationId}")
	Response modifyTermination(String terminationId);

	@DELETE
	@Path("{contractNumber}/terminations/{terminationId}")
	Response revokeTermination(String terminationId);

}
