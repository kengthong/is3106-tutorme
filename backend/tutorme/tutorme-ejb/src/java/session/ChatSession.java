/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Chat;
import entity.Person;
import exception.ChatNotFoundException;
import exception.PersonNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Owen Tay
 */
@Stateless
public class ChatSession implements ChatSessionLocal {

    @EJB
    private PersonSessionLocal personSession;
    @PersistenceContext(unitName = "tutorme-ejbPU")
    private EntityManager em;

    @Override
    public Chat createChat(Long p1Id, Long p2Id) {
        try {
            Person p1 = personSession.retrievePersonById(p1Id);
            Person p2 = personSession.retrievePersonById(p2Id);
            Chat chat = new Chat(p1, p2);
            em.persist(chat);
            return retrieveChatByPersonIds(p1Id, p2Id);
        } catch (PersonNotFoundException ex) {
            System.out.println("Person not found.");
            return null;
        } catch (ChatNotFoundException ex) {
            return null;
        }
    }

    @Override
    public Chat retrieveChatById(Long chatId) throws ChatNotFoundException {
        Chat chat = em.find(Chat.class, chatId);
        if (chat != null) {
            return chat;
        } else {
            throw new ChatNotFoundException("Chat ID " + chatId + "does not exists.");
        }
    }

    @Override
    public Chat retrieveChatByPersonIds(Long p1Id, Long p2Id) throws ChatNotFoundException {
        Query query = em.createQuery("SELECT c from Chat c WHERE c.p1.personId=:inputP1 AND c.p2.personId=:inputP2 OR c.p1.personId=:inputP2 AND c.p2.personId=:inputP1");
        query.setParameter("inputP1", p1Id);
        query.setParameter("inputP2", p2Id);
        try {
            Chat chat = (Chat) query.getSingleResult();
            return chat;
        } catch (NoResultException ex) {
            throw new ChatNotFoundException("Chat between " + p1Id + " & " + p2Id + "does not exists.");
        }
    }

    @Override
    public List<Chat> retrieveChatsByPersonId(Long personId) {
        List<Chat> chats = new ArrayList<>();
        Query query = em.createQuery("SELECT c from Chat c WHERE c.p1.personId=:inputPersonId OR c.p2.personId=:inputPersonId");
        query.setParameter("inputPersonId", personId);
        chats = query.getResultList();
        for (Chat c : chats) {
            c.getMessages();
        }
        return chats;
    }

}
