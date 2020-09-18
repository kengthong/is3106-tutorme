/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Message;
import entity.Person;
import exception.MessageNotFoundException;
import exception.PersonNotFoundException;
import java.util.List;
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
    private PersonSessionLocal userSession;
    @PersistenceContext(unitName = "tutorme-ejbPU")
    private EntityManager em;

    @Override
    public Message createMessage(Message newMessage) {
        Person sender = newMessage.getSender();
        Person receiver = newMessage.getReceiver();
        em.persist(newMessage);
        em.flush();

        sender.getMessages().add(newMessage);
        receiver.getMessages().add(newMessage);
        em.merge(sender);
        em.merge(receiver);
        return newMessage;
    }

    @Override
    public Message createMessage(Long senderId, Long receiverId, String body) throws PersonNotFoundException {
        Person sender = userSession.retrievePersonById(senderId);
        Person receiver = userSession.retrievePersonById(receiverId);
        Message newMessage = new Message(sender, receiver, body);
        em.persist(newMessage);
        em.flush();

        sender.getMessages().add(newMessage);
        receiver.getMessages().add(newMessage);
        em.merge(sender);
        em.merge(receiver);
        return newMessage;
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
    public List<Message> retrieveMessagesByPersonId(Long userId) throws PersonNotFoundException {
        Person user = userSession.retrievePersonById(userId);
        List<Message> messages = user.getMessages();
        return messages;
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
