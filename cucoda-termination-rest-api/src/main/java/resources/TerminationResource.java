package resources;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import domain.Termination;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import services.TerminationService;

@Path("/terminations")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class TerminationResource {

	@Inject
	private TerminationService terminationService;

	private static final Response RESPONSE = Response
			.status(Response.Status.BAD_GATEWAY)
			.entity(new UnsupportedOperationException("Not yet implemented"))
			.build();


	@GET
	@Path("{contractNumber}")
	public Termination readTerminations(@PathParam(value = "contractNumber") String contractNumber) {
		return terminationService.readTerminations(contractNumber);
	}


	//	@GET
//	@Path("{contractNumber}/terminations")
//	public Response readTerminations(@PathParam(value = "contractNumber") String contractNumber) {
//		return RESPONSE;
//	}
//
//	@GET
//	@Path("{contractNumber}/terminations/options")
//	public Response terminationOptions(@PathParam(value = "contractNumber") String contractNumber) {
//		return RESPONSE;
//	}
//
//	@POST
//	@Path("{contractNumber}/terminations")
//	public Response terminateContract(@PathParam(value = "contractNumber") String contractNumber) {
//		return RESPONSE;
//	}
//
//	@PUT
//	@Path("{contractNumber}/terminations/{terminationId}")
//	public Response modifyTermination(@PathParam(value = "terminationId") String terminationId) {
//		return RESPONSE;
//	}
//
//	@DELETE
//	@Path("{contractNumber}/terminations/{terminationId}")
//	public Response revokeTermination(@PathParam(value = "contractNumber") String contractNumber,
//			@PathParam(value = "terminationId") String terminationId) {
//		return RESPONSE;
//	}
}
