package webservices.restful;

import entity.JobListing;
import entity.Offer;
import entity.Person;
import entity.Rating;
import entity.Staff;
import entity.Tutee;
import exception.PersonNotFoundException;
import filter.StaffJWTTokenNeeded;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
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
import session.StaffSessionLocal;
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
    @EJB
    StaffSessionLocal staffSession;

    public StaffResource() {
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
    @Path("/home")
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
    @Path("/getTutees")
    @StaffJWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTutees() {
        System.out.println("Getting tutees...");
        List<Tutee> tutees = new ArrayList();
        tutees = tuteeSession.retrieveAllTutees();
        if (!tutees.isEmpty()) {
            for (Tutee t : tutees) {
                t.setSalt(null);
                t.setPassword(null);
                t.setReceivedMessages(null);
                t.setSentMessages(null);
                t.setOffers(null); //to confirm not needed
            }
            GenericEntity<List<Tutee>> packet = new GenericEntity<List<Tutee>>(tutees) {
            };
            return Response.status(200).entity(packet).build();
        } else {
            JsonObject exception = Json.createObjectBuilder().add("error", "returned empty list from REST/getTutees").build();
            return Response.status(400).entity(exception).build();
        }
    }

    @GET
    @Path("/getStaffs")
    @StaffJWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStaffs() {
        System.out.println("Getting staffs...");
        List<Staff> staffs = staffSession.retrieveAllStaffs();
        if (!staffs.isEmpty()) {
            for (Staff s : staffs) {
                s.setSalt(null);
                s.setPassword(null);
                s.setReceivedMessages(null);
                s.setSentMessages(null);
            }
            GenericEntity<List<Staff>> packet = new GenericEntity<List<Staff>>(staffs) {
            };
            return Response.status(200).entity(packet).build();
        } else {
            JsonObject exception = Json.createObjectBuilder().add("error", "returned empty list from REST/getStaffs").build();
            return Response.status(400).entity(exception).build();
        }
    }
}
