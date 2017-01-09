package localdbservice.rest.resource;

import localdbservice.rest.model.*;

import java.io.IOException;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;


@Path("/person")
public class PersonCollectionResource {

	@Context
	UriInfo uriInfo;
	@Context
	Request request;


	// Return the list of people to the user in the browser
	@GET
	@Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Person> getPersonsBrowser() {
		System.out.println("Getting list of people...");
		List<Person> people = Person.getAll();
		return people;
	}

	// retuns the number of people
	// to get the total number of records
	@GET
	@Path("count")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCount() {
		System.out.println("Getting count...");
		List<Person> people = Person.getAll();
		int count = people.size();
		return String.valueOf(count);
	}

	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Person newPerson(Person person) throws IOException {
		System.out.println("Creating new person...");
		return Person.savePerson(person);
	}

	@Path("{personId}")
	public PersonResource getPerson(@PathParam("personId") int id) {
		return new PersonResource(uriInfo, request, id);
	}

	@GET
	@Path("{personId}/{measureName}")
	@Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<HealthMeasureHistory> getHistory(@PathParam("personId") Integer id,
			@PathParam("measureName") String measureName) {
		Integer idMeasure = MeasureDefinition.getIdByName(measureName);
		List<HealthMeasureHistory> healthProfile = HealthMeasureHistory.getPeopleHistory(id, idMeasure);
		return healthProfile;
	}

	@GET
	@Path("{personId}/{measureName}/{mid}")
	@Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public HealthMeasureHistory getMeasureValue(@PathParam("personId") int id, @PathParam("measureName") String measureName,
			@PathParam("mid") int idHealthProfile) {
		Integer idMeasure = MeasureDefinition.getIdByName(measureName);
		HealthMeasureHistory healthProfile = HealthMeasureHistory.getValueMeasureHistory(id, idMeasure, idHealthProfile);
		return healthProfile;
	}

	
	@POST
	@Path("{personId}/{measureType}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public LifeStatus updateHealth(@PathParam("personId") int personid, @PathParam("measureType") String measureType,
			HealthMeasureHistory hist) throws IOException {
		Integer idMeasure = MeasureDefinition.getIdByName(measureType);
		LifeStatus health = LifeStatus.getValueHealthProfile(personid, idMeasure);
		Person p = Person.getPersonById(personid);
		LifeStatus healthProfile=new LifeStatus();
		MeasureDefinition m = MeasureDefinition.getMeasureDefinitionById(idMeasure);
		String Oldvalue= health.getValue();
    	HealthMeasureHistory history = new HealthMeasureHistory();
    	history.setPerson(p);
    	history.setMeasureDef(m);
    	history.setValue(Oldvalue);
    	history.setTimestamp(hist.getTimestamp());
    	HealthMeasureHistory.saveHealthMeasureHistory(history);
    	LifeStatus.removeLifeStatus(health);
    	healthProfile.setValue(hist.getValue());
    	healthProfile.setPerson(p);
    	healthProfile.setMeasureDefinition(m);
    	LifeStatus.saveLifeStatus(healthProfile);
		return healthProfile;
	}
}