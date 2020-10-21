/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Chat;
import entity.Message;
import entity.Person;
import exception.ChatNotFoundException;
import exception.MessageNotFoundException;
import exception.PersonNotFoundException;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Tay Z H Owen
 */
@Stateless
public class MessageSession implements MessageSessionLocal {

    @EJB
    private PersonSessionLocal personSession;
    @EJB
    private ChatSessionLocal chatSession;
    @PersistenceContext(unitName = "tutorme-ejbPU")
    private EntityManager em;

    @Override
    public Message createMessage(Long senderId, Long receiverId, String body) {
        Chat chat = new Chat();
        try {
            try {
                chat = chatSession.retrieveChatByPersonIds(senderId, receiverId);
            } catch (ChatNotFoundException ex) {
                chat = chatSession.createChat(senderId, receiverId);
//                System.out.println("Chat not found when creating Message, new chat is created... chatId: " + chat.getChatId());
            }
            Person sender = personSession.retrievePersonById(senderId);
            Person receiver = personSession.retrievePersonById(receiverId);
            Message newMessage = new Message(chat, sender, receiver, body);
            em.persist(newMessage);
            return newMessage;
        } catch (PersonNotFoundException ex) {
            System.out.println("Person not found when creating Message");
            return null;
        }
    }

    @Override
    public Message retrieveMessageById(Long messageId) throws MessageNotFoundException {
        Message message = em.find(Message.class, messageId);
        if (message != null) {
            return message;
        } else {
            throw new MessageNotFoundException("Message ID " + messageId + "does not exists.");
        }
    }

    @Override
    public void updateMessage(Message updatedMessage) {
        em.merge(updatedMessage);
    }

    @Override
    public void updateMessage(Long messageId, String updatedMessage) throws MessageNotFoundException {
        Message message = retrieveMessageById(messageId);
        message.setBody(updatedMessage);
        updateMessage(message);
    }

    @Override
    public void deactivateMessage(Long messageId) throws MessageNotFoundException {
        Message message = retrieveMessageById(messageId);
        if (message.getActiveStatus()) {
            message.setActiveStatus(false);
            updateMessage(message);
        }
    }

    @Override
    public void deleteMessage(Long messageId) throws MessageNotFoundException {
        Message message = retrieveMessageById(messageId);
        em.remove(message);
    }

}
