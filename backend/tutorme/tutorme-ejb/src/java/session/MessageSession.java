/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Message;
import entity.Person;
import entity.Tutee;
import entity.Tutor;
import exception.MessageNotFoundException;
import exception.PersonNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.MessageDateComaparator;

/**
 *
 * @author Tay Z H Owen
 */
@Stateless
public class MessageSession implements MessageSessionLocal {

    @EJB
    private PersonSessionLocal personSession;
    @PersistenceContext(unitName = "tutorme-ejbPU")
    private EntityManager em;

    @Override
    public Message createMessage(Long senderId, Long receiverId, String body) {
        try {
            Person sender = personSession.retrievePersonById(senderId);
            Person receiver = personSession.retrievePersonById(receiverId);
            Message newMessage = new Message(sender, receiver, body);
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
    public List<Message> retrieveConversation(Long p1Id, Long p2Id) {
        List<Message> conversation = new ArrayList<>();
        Query query = em.createQuery("SELECT m from Message m WHERE m.sender.personId=:inputP1 AND m.receiver.personId=:inputP2 OR m.sender.personId=:inputP2 AND m.receiver.personId=:inputP1");
        query.setParameter("inputP1", p1Id);
        query.setParameter("inputP2", p2Id);
        conversation = query.getResultList();
        for (Message m : conversation) {
            em.detach(m);
            Person sender = m.getSender();
            em.detach(sender);
            sender.setReceivedMessages(null);
            sender.setSentMessages(null);
            sender.setSalt(null);
            sender.setPassword(null);

            Person receiver = m.getReceiver();
            em.detach(receiver);
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
        conversation.sort(new MessageDateComaparator());
        return conversation;
    }

    @Override
    public List<List<Message>> retrieveAllConversations(Long personId) {
        List<Message> messages = new ArrayList<>();
        Query query = em.createQuery("SELECT m from Message m WHERE m.sender.personId=:inputPersonId OR m.receiver.personId=:inputPersonId");
        query.setParameter("inputPersonId", personId);
        messages = query.getResultList();

        Set<Long> otherPersonIds = new HashSet<>();
        for (Message m : messages) {
            Long senderId = m.getSender().getPersonId();
            Long receiverId = m.getReceiver().getPersonId();
            if (senderId.equals(personId)) {
                otherPersonIds.add(receiverId);
            } else {
                otherPersonIds.add(senderId);
            }
        }

        List<List<Message>> conversations = new ArrayList<>();
        otherPersonIds.forEach(pId -> conversations.add(retrieveConversation(personId, pId)));
        return conversations;
    }
}
