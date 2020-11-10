package webservices.restful;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.JobListing;
import entity.Offer;
import entity.Person;
import entity.Rating;
import entity.Tutee;
import entity.Tutor;
import exception.PersonNotFoundException;
import exception.TuteeNotFoundException;
import filter.StaffJWTTokenNeeded;
import filter.TuteeJWTTokenNeeded;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import session.EmailSessionLocal;
import session.JobListingSessionLocal;
import session.OfferSessionLocal;
import session.PersonSessionLocal;
import session.TuteeSessionLocal;
import session.TutorSessionLocal;

/**
 * REST Web Service
 *
 * @author Owen Tay
 */
@Path("staff")
@RequestScoped
public class StaffResource {

    @EJB
    JobListingSessionLocal jobListingSession;
    @EJB
    OfferSessionLocal offerSession;
    @EJB
    PersonSessionLocal personSession;
    @EJB
    TutorSessionLocal tutorSession;
    @EJB
    TuteeSessionLocal tuteeSession;
    @EJB
    EmailSessionLocal emailSession;

    public StaffResource() {
    }

    @GET
    @Path("/jobListings")
    @StaffJWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response reportJobListings() {
        System.out.println("Getting jobListing report...");
        List<JobListing> jobListings = jobListingSession.retrieveAllJobListings();
        JsonArrayBuilder jsonArrayPayload = Json.createArrayBuilder();
        ObjectMapper mapper = new ObjectMapper();

        for (JobListing jl : jobListings) {
            try {
                JsonObjectBuilder jsonObject = Json.createObjectBuilder();
                Tutor tutor = jl.getTutor();
                tutor.setSalt(null);
                tutor.setPassword(null);
                tutor.setSentMessages(null);
                tutor.setReceivedMessages(null);
                tutor.setJobListings(null);

                List<Offer> offers = jl.getOffers();
                for (Offer o : offers) {
                    Tutee tutee = o.getTutee();
                    tutee.setReceivedMessages(null);
                    tutee.setSentMessages(null);
                    tutee.setPassword(null);
                    tutee.setSalt(null);
                    tutee.setOffers(null);
                    Rating rating = o.getRating();
                    if (rating != null) {
                        rating.setOffer(null);
                    }

                    o.setJobListing(null);
                }
                String jsonListing = mapper.writeValueAsString(jl);
                jsonObject.add(String.valueOf(jl.getJobListingId()), jsonListing);
                jsonObject.add("numOffers", offers.size());
                jsonObject.add("numSubjects", jl.getSubjects().size());
                jsonArrayPayload.add(jsonObject);
            } catch (JsonProcessingException ex) {
                Logger.getLogger(JobListingResource.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return Response.status(200).entity(jsonArrayPayload.build()).build();
    }

    @GET
    @Path("/offers")
    @StaffJWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOffers() {
        System.out.println("Getting all offers...");
        List<Offer> offers = offerSession.retrieveAllOffers();

        for (Offer o : offers) {
            Tutee tutee = o.getTutee();
            tutee.setReceivedMessages(null);
            tutee.setSentMessages(null);
            tutee.setPassword(null);
            tutee.setSalt(null);
            tutee.setOffers(null);
            Rating rating = o.getRating();
            if (rating != null) {
                rating.setOffer(null);
            }

            JobListing jobListing = o.getJobListing();
            jobListing.setOffers(null);
            jobListing.setTutor(null);
        }
        GenericEntity<List<Offer>> payload = new GenericEntity<List<Offer>>(offers) {
        };
        return Response.status(200).entity(payload).build();
    }

    @GET
    @Path("/dashboard")
    @StaffJWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDashboard() {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        Integer numActiveTutors = tutorSession.getActiveTutors();
        Integer tutorGrowth = tutorSession.getTutorGrowth();
        builder.add("numActiveTutors", numActiveTutors);
        builder.add("tutorGrowth", tutorGrowth);

        Integer numActiveTutees = tuteeSession.getActiveTutees();
        Integer tuteeGrowth = tuteeSession.getTuteeGrowth();
        builder.add("numActiveTutees", numActiveTutees);
        builder.add("tuteeGrowth", tuteeGrowth);

        Integer numActiveJobListings = jobListingSession.getActiveJobListings();
        Integer jobListingGrowth = jobListingSession.getJobListingGrowth();
        builder.add("numActiveJobListings", numActiveJobListings);
        builder.add("jobListingGrowth", jobListingGrowth);

        Integer numActiveOffers = offerSession.getActiveOffers();
        Integer offerGrowth = offerSession.getOfferGrowth();
        builder.add("numActiveOffers", numActiveOffers);
        builder.add("offerGrowth", offerGrowth);

        Double offerAcceptanceRate = offerSession.getOfferAcceptanceRate();
        Double offerRejectionRate = offerSession.getOfferRejectionRate();
        builder.add("offerAcceptanceRate", offerAcceptanceRate);
        builder.add("offerRejectionRate", offerRejectionRate);

        return Response.status(200).entity(builder.build()).build();
    }

    @PUT
    @Path("/ban/{personId}")
    @StaffJWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response banPerson(@PathParam("personId") Long personId) {
        System.out.println("Banning Person Id is ... " + personId);
        try {
            Person person = personSession.deactivatePerson(personId);
            emailSession.ban(person.getFirstName(), person.getEmail());
            return Response.status(204).build();
        } catch (PersonNotFoundException ex) {
            JsonObject exception = Json.createObjectBuilder().add("error", "tuteeId does not exists").build();
            return Response.status(400).entity(exception).build();
        }
    }

    @PUT
    @Path("/unban/{personId}")
    @StaffJWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response unbanPerson(@PathParam("personId") Long personId) {
        System.out.println("Unban Person Id is ... " + personId);
        try {
            Person person = personSession.activatePerson(personId);
            emailSession.ban(person.getFirstName(), person.getEmail());
            return Response.status(204).build();
        } catch (PersonNotFoundException ex) {
            JsonObject exception = Json.createObjectBuilder().add("error", "tuteeId does not exists").build();
            return Response.status(400).entity(exception).build();
        }
    }
    
    @GET
    @Path("/get")
    @StaffJWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTuteeById(@PathParam("tuteeId") Long tuteeId) {
        System.out.println("Tutee Id is... " + tuteeId);
        try {
            Tutee tutee = tuteeSession.retrieveTuteeById(tuteeId);
            tutee.setPassword(null);
            tutee.setSalt(null);
            tutee.setReceivedMessages(null);
            tutee.setSentMessages(null);
            tutee.setOffers(null); // to confirm not needed
            GenericEntity<Tutee> packet = new GenericEntity<Tutee>(tutee) {
            };
            return Response.status(200).entity(packet).build();
        } catch (TuteeNotFoundException ex) {
            JsonObject exception = Json.createObjectBuilder().add("error", "tuteeId does not exists").build();
            return Response.status(400).entity(exception).build();
        }
    }
}