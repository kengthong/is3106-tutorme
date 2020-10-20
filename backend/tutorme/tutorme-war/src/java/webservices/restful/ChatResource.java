/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful;

import entity.Chat;
import entity.Message;
import exception.ChatNotFoundException;
import filter.JWTTokenNeeded;
import utils.MessageDateComaparator;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import session.ChatSessionLocal;
import session.MessageSessionLocal;

/**
 * REST Web Service
 *
 * @author Owen Tay
 */
@Path("/chat")
@RequestScoped
public class ChatResource {

    @EJB
    private ChatSessionLocal chatSession;
    @EJB
    private MessageSessionLocal messageSession;

    @GET
    @Path("/getChats")
    @JWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response getChatsByPersonId(@QueryParam("personId") Long personId) {
        System.out.println("Getting chats by personId..." + personId);
        List<Chat> chats = chatSession.retrieveChatsByPersonId(personId);
//        List<Chat> chats = new ArrayList<>();

        if (!chats.isEmpty()) {
            for (Chat c : chats) {
                System.out.println("#### ChatId: "+c.getChatId());
                List<Message> messages = c.getMessages();
                c.getP1().setSalt(null);
                c.getP1().setPassword(null);
                c.getP2().setSalt(null);
                c.getP2().setPassword(null);
                if (messages.size() > 1) {
                    System.out.println("sorting messages...");
                    messages.sort(new MessageDateComaparator());
                    c.setMessages(messages.subList(0, 1));
                } else {
                    System.out.println("0 messages");
                }
            }
            GenericEntity<List<Chat>> packet = new GenericEntity<List<Chat>>(chats) {
            };
            return Response.status(200).entity(packet).build();
        } else {
            JsonObject exception = Json.createObjectBuilder().add("error", "returned empty list from REST/getChats").build();
            return Response.status(400).entity(exception).build();
        }
    }

    @GET
    @Path("/getChat")
    @JWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response getChatByChatId(@QueryParam("chatId") Long chatId) {
        System.out.println("Getting chat by chatId..." + chatId);
        try {
            Chat chat = chatSession.retrieveChatById(chatId);
            GenericEntity<Chat> packet = new GenericEntity<Chat>(chat) {
            };
            return Response.status(200).entity(packet).build();
        } catch (ChatNotFoundException ex) {
            JsonObject exception = Json.createObjectBuilder().add("error", "No chat found REST/getChat").build();
            return Response.status(400).entity(exception).build();
        }
    }

    @POST
    @Path("/sendMsg")
    @JWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response createMessage(JsonObject json) {
        System.out.println("Creating new message...");
        String senderIdStr = json.getString("senderId");
        Long senderId = Long.valueOf(senderIdStr);
        String receiverIdStr = json.getString("receiverId");
        Long receiverId = Long.valueOf(receiverIdStr);
        String body = json.getJsonString("msgBody").getString();

        try {
            Message message = messageSession.createMessage(senderId, receiverId, body);
            Chat chat = chatSession.retrieveChatByPersonIds(senderId, receiverId);
            GenericEntity<Chat> packet = new GenericEntity<Chat>(chat) {
            };
            return Response.status(200).entity(packet).build();
        } catch (ChatNotFoundException ex) {
            JsonObject exception = Json.createObjectBuilder().add("error", "No chat found REST/getChat").build();
            return Response.status(400).entity(exception).build();
        }
    }
}
