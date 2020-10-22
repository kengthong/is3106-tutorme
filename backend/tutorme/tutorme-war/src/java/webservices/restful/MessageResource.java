/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful;

import entity.Message;
import filter.JWTTokenNeeded;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import session.MessageSessionLocal;

/**
 * REST Web Service
 *
 * @author Owen Tay
 */
@Path("message")
@RequestScoped
public class MessageResource {

    @EJB
    MessageSessionLocal messageSession;

    /**
     * Creates a new instance of MessageResource
     */
    public MessageResource() {
    }

    @GET
    @Path("/conversation")
    @JWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response getConversation(@QueryParam("p1Id") Long p1Id, @QueryParam("p2Id") Long p2Id) {
        System.out.println("Getting conversation between... p1Id: " + p1Id + " and p2Id: " + p2Id);
        List<Message> conversation = messageSession.retrieveConversation(p1Id, p2Id);
        GenericEntity<List<Message>> packet = new GenericEntity<List<Message>>(conversation) {
        };
        return Response.status(200).entity(packet).build();
    }

    @GET
    @Path("/conversations")
    @JWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response getConversations(@QueryParam("personId") Long personId) {
        System.out.println("Getting conversations by personId...: " + personId);
        List<List<Message>> conversation = messageSession.retrieveAllConversations(personId);
        GenericEntity<List<List<Message>>> packet = new GenericEntity<List<List<Message>>>(conversation) {
        };
        return Response.status(200).entity(packet).build();
    }

    @PUT
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
        Message message = messageSession.createMessage(senderId, receiverId, messageBody);
        JsonObjectBuilder payload = Json.createObjectBuilder();
        payload.add("messageId", message.getMessageId());
        payload.add("success", true);
        return Response.status(200).entity(payload.build()).build();
    }
}
