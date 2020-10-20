/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Message;
import exception.MessageNotFoundException;
import javax.ejb.Local;

/**
 *
 * @author Tay Z H Owen
 */
@Local
public interface MessageSessionLocal {

    public Message createMessage(Long senderId, Long receiverId, String body);

    public Message retrieveMessageById(Long messageId) throws MessageNotFoundException;

    public void updateMessage(Message updatedMessage);

    public void updateMessage(Long messageId, String updatedMessage) throws MessageNotFoundException;

    public void deactivateMessage(Long messageId) throws MessageNotFoundException;

    public void deleteMessage(Long messageId) throws MessageNotFoundException;
}
