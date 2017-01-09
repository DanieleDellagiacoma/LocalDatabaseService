package localdbservice.rest.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import localdbservice.rest.model.MeasureDefinition;

@Path("/measureTypes")
public class MeasureResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
		
    @GET
    @Produces({MediaType.TEXT_XML,  MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML })
    public List<MeasureDefinition> getMeasuresBrowser() {
        System.out.println("Getting list of measures...");
        List<MeasureDefinition> measures = MeasureDefinition.getAll();
        return measures;
    }
}