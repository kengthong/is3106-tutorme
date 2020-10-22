/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Tay Z H Owen
 */
@Entity
public class Message implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Chat chat;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Person sender;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Person receiver;

    @NotNull
    private String body;

    private Boolean activeStatus;

    public Message() {
        this.activeStatus = true;
        this.createdDate = Date.from(Instant.now());
    }

    public Message(Chat chat, Person sender, Person receiver, String body) {
        this();
        this.chat = chat;
        this.sender = sender;
        this.receiver = receiver;
        this.body = body;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Person getSender() {
        return sender;
    }

    public void setSender(Person sender) {
        this.sender = sender;
    }

    public Person getReceiver() {
        return receiver;
    }

    public void setReceiver(Person receiver) {
        this.receiver = receiver;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

}
