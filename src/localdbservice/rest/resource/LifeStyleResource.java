package localdbservice.rest.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import localdbservice.rest.model.LifeStyle;

@Path("/lifeStyle")
public class LifeStyleResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
    @GET
	@Path("{lifeStyleId}")
    @Produces({MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML })
	public LifeStyle getPerson(@PathParam("lifeStyleId") int id) {
    	LifeStyle lifeStyle = LifeStyle.getLifeStyleById(id);
         return lifeStyle;
	}
    
    @GET
    @Produces({MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML })
   	public List<LifeStyle> getLifeStyleBrowser() {
		System.out.println("Getting list of lifeStyle...");
		List<LifeStyle> lifeStyles = LifeStyle.getAll();
		return lifeStyles;
   	}
    
}
