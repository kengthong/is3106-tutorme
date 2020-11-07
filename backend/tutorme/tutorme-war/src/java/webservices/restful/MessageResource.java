/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful;

import entity.Message;
import entity.Person;
import entity.Staff;
import entity.Tutee;
import entity.Tutor;
import exception.PersonNotFoundException;
import exception.StaffNotFoundException;
import filter.JWTTokenNeeded;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import session.MessageSessionLocal;
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

    public MessageResource() {
    }

    @GET
    @Path("/conversation")
    @JWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response getConversation(@QueryParam("p1Id") Long p1Id, @QueryParam("p2Id") Long p2Id) {
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
    @Path("/conversations")
    @JWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response getConversations(@QueryParam("personId") Long personId) {
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
    public Response sendMessage(JsonObject json) {
        String senderIdStr = json.getString("senderId");
        String receiverIdStr = json.getString("receiverId");
        Long senderId = Long.valueOf(senderIdStr);
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
    public Response sendFeedback(JsonObject json) {
        String senderIdStr = json.getString("senderId");

        try {
            Staff staff = staffSession.retrieveStaffByEmail("tutormecare3106@gmail.com");
            Long receiverId = staff.getPersonId();
            Long senderId = Long.valueOf(senderIdStr);
            System.out.println("Sending new message from...senderId: " + senderId + " to receiverId: " + receiverId);
            String messageBody = json.getString("body");

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
        } catch (StaffNotFoundException ex) {
            JsonObject exception = Json.createObjectBuilder().add("Bad Request", ex.getMessage()).build();
            return Response.status(400).entity(exception).build();
        }
    }
}
