/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Message;
import exception.MessageNotFoundException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Tay Z H Owen
 */
@Local
public interface MessageSessionLocal {

    public Message createMessage(Long senderId, Long receiverId, String body);

    public Message retrieveMessageById(Long messageId) throws MessageNotFoundException;

    public List<Message> retrieveConversation(Long p1Id, Long p2Id);
    
    public List<List<Message>> retrieveAllConversations(Long personId);

}
