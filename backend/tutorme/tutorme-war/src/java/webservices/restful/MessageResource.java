package webservices.restful;

import entity.Message;
import entity.Person;
import entity.Staff;
import entity.Tutee;
import entity.Tutor;
import enumeration.PersonEnum;
import exception.PersonNotFoundException;
import exception.StaffNotFoundException;
import filter.JWTTokenNeeded;
import filter.StaffJWTTokenNeeded;
import filter.UserPrincipal;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import session.MessageSessionLocal;
import session.PersonSessionLocal;
import session.StaffSessionLocal;
/**
 * REST Web Service
 *
 * @author Owen Tay
 */
@Path("/message")
@RequestScoped
public class MessageResource {

    @EJB
    MessageSessionLocal messageSession;
    @EJB
    StaffSessionLocal staffSession;
    @EJB
    PersonSessionLocal personSession;

    public MessageResource() {
    }

    @GET
    @Path("/get/{p2Id}")
    @JWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response getConversation(@Context SecurityContext securityContext, @PathParam("p2Id") Long p2Id) {
        UserPrincipal person = (UserPrincipal) securityContext.getUserPrincipal();
        Long p1Id = person.getPersonId();
        System.out.println("Getting conversation between... p1Id: " + p1Id + " and p2Id: " + p2Id);
        List<Message> conversation = messageSession.retrieveConversation(p1Id, p2Id);

        for (Message m : conversation) {
            Person sender = m.getSender();
            sender.setReceivedMessages(null);
            sender.setSentMessages(null);
            sender.setSalt(null);
            sender.setPassword(null);

            Person receiver = m.getReceiver();
            receiver.setReceivedMessages(null);
            receiver.setSentMessages(null);
            receiver.setSalt(null);
            receiver.setPassword(null);

            switch (sender.getPersonEnum()) {
                case TUTEE:
                    Tutee tempTutee = (Tutee) sender;
                    tempTutee.setOffers(null);
                    break;
                case TUTOR:
                    Tutor tempTutor = (Tutor) sender;
                    tempTutor.setJobListings(null);
                    break;
            }
            switch (receiver.getPersonEnum()) {
                case TUTEE:
                    Tutee tempTutee = (Tutee) receiver;
                    tempTutee.setOffers(null);
                    break;
                case TUTOR:
                    Tutor tempTutor = (Tutor) receiver;
                    tempTutor.setJobListings(null);
                    break;
            }
        }
        GenericEntity<List<Message>> payload = new GenericEntity<List<Message>>(conversation) {
        };
        return Response.status(200).entity(payload).build();
    }

    @GET
    @Path("/get")
    @JWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response getConversations(@Context SecurityContext securityContext) {
        UserPrincipal person = (UserPrincipal) securityContext.getUserPrincipal();
        Long personId = person.getPersonId();
        System.out.println("Getting conversations by personId...: " + personId);
        List<List<Message>> conversations = messageSession.retrieveAllConversations(personId);
        for (List<Message> c : conversations) {
            for (Message m : c) {
                Person sender = m.getSender();
                sender.setReceivedMessages(null);
                sender.setSentMessages(null);
                sender.setSalt(null);
                sender.setPassword(null);

                Person receiver = m.getReceiver();
                receiver.setReceivedMessages(null);
                receiver.setSentMessages(null);
                receiver.setSalt(null);
                receiver.setPassword(null);

                switch (sender.getPersonEnum()) {
                    case TUTEE:
                        Tutee tempTutee = (Tutee) sender;
                        tempTutee.setOffers(null);
                        break;
                    case TUTOR:
                        Tutor tempTutor = (Tutor) sender;
                        tempTutor.setJobListings(null);
                        break;
                }
                switch (receiver.getPersonEnum()) {
                    case TUTEE:
                        Tutee tempTutee = (Tutee) receiver;
                        tempTutee.setOffers(null);
                        break;
                    case TUTOR:
                        Tutor tempTutor = (Tutor) receiver;
                        tempTutor.setJobListings(null);
                        break;
                }
            }
        }
        GenericEntity<List<List<Message>>> payload = new GenericEntity<List<List<Message>>>(conversations) {
        };
        return Response.status(200).entity(payload).build();
    }

    @POST
    @Path("/sendMessage")
    @JWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendMessage(@Context SecurityContext securityContext, JsonObject json) {
        UserPrincipal person = (UserPrincipal) securityContext.getUserPrincipal();
        String receiverIdStr = json.getString("receiverId");
        Long senderId = person.getPersonId();
        Long receiverId = Long.valueOf(receiverIdStr);
        System.out.println("Sending new message from...senderId: " + senderId + " to receiverId: " + receiverId);
        String messageBody = json.getString("body");
        try {
            Message message = messageSession.createMessage(senderId, receiverId, messageBody);
            List<Message> conversation = messageSession.retrieveConversation(senderId, receiverId);
            for (Message m : conversation) {
                Person sender = m.getSender();
                sender.setReceivedMessages(null);
                sender.setSentMessages(null);
                sender.setSalt(null);
                sender.setPassword(null);

                Person receiver = m.getReceiver();
                receiver.setReceivedMessages(null);
                receiver.setSentMessages(null);
                receiver.setSalt(null);
                receiver.setPassword(null);

                switch (sender.getPersonEnum()) {
                    case TUTEE:
                        Tutee tempTutee = (Tutee) sender;
                        tempTutee.setOffers(null);
                        break;
                    case TUTOR:
                        Tutor tempTutor = (Tutor) sender;
                        tempTutor.setJobListings(null);
                        break;
                }
                switch (receiver.getPersonEnum()) {
                    case TUTEE:
                        Tutee tempTutee = (Tutee) receiver;
                        tempTutee.setOffers(null);
                        break;
                    case TUTOR:
                        Tutor tempTutor = (Tutor) receiver;
                        tempTutor.setJobListings(null);
                        break;
                }
            }
            GenericEntity<List<Message>> payload = new GenericEntity<List<Message>>(conversation) {
            };
            return Response.status(201).entity(payload).build();
        } catch (PersonNotFoundException ex) {
            JsonObject exception = Json.createObjectBuilder().add("Bad Request", ex.getMessage()).build();
            return Response.status(400).entity(exception).build();
        }
    }

    @POST
    @Path("/sendFeedback")
    @JWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendFeedback(@Context SecurityContext securityContext, JsonObject json) {
        UserPrincipal person = (UserPrincipal) securityContext.getUserPrincipal();
        Long senderId = person.getPersonId();
        JsonObjectBuilder exception = Json.createObjectBuilder();

        try {
            Person staffCheck = personSession.retrievePersonById(senderId);
            if (staffCheck.getPersonEnum().equals(PersonEnum.STAFF)) {
                exception.add("Bad Request", "Staff cannot be sending feedback.");
                return Response.status(400).entity(exception.build()).build();
            }
            Staff staff = staffSession.retrieveStaffByEmail("tutormecare3106@gmail.com");
            Long receiverId = staff.getPersonId();
            System.out.println("Sending feedback message from...senderId: " + senderId + " to receiverId: " + receiverId);
            String messageBody = json.getString("body");

            Message message = messageSession.createMessage(senderId, receiverId, messageBody);
            return Response.status(204).build();
        } catch (PersonNotFoundException | StaffNotFoundException ex) {
            exception.add("Bad Request", ex.getMessage());
            return Response.status(400).entity(exception.build()).build();
        }
    }

    @GET
    @Path("/allFeedbacks")
    @StaffJWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewAllFeedback() {
        JsonObjectBuilder exception = Json.createObjectBuilder();
        try {
            Staff staff = staffSession.retrieveStaffByEmail("tutormecare3106@gmail.com");
            Long staffId = staff.getPersonId();
            List<List<Message>> feedbacks = messageSession.retrieveAllConversations(staffId);
            for (List<Message> c : feedbacks) {
                for (Message m : c) {
                    Person sender = m.getSender();
                    sender.setReceivedMessages(null);
                    sender.setSentMessages(null);
                    sender.setSalt(null);
                    sender.setPassword(null);

                    Person receiver = m.getReceiver();
                    receiver.setReceivedMessages(null);
                    receiver.setSentMessages(null);
                    receiver.setSalt(null);
                    receiver.setPassword(null);

                    switch (sender.getPersonEnum()) {
                        case TUTEE:
                            Tutee tempTutee = (Tutee) sender;
                            tempTutee.setOffers(null);
                            break;
                        case TUTOR:
                            Tutor tempTutor = (Tutor) sender;
                            tempTutor.setJobListings(null);
                            break;
                    }
                }
            }
            GenericEntity<List<List<Message>>> payload = new GenericEntity<List<List<Message>>>(feedbacks) {
            };
            return Response.status(200).entity(payload).build();
        } catch (StaffNotFoundException ex) {
            exception.add("Bad Request", ex.getMessage());
            return Response.status(400).entity(exception.build()).build();
        }
    }
}
