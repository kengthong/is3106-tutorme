///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package entity;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.ManyToOne;
//import javax.persistence.OneToMany;
//import javax.validation.constraints.NotNull;
//
///**
// *
// * @author Owen Tay
// */
//@Entity
//public class Chat implements Serializable {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long chatId;
//
//    @NotNull
//    @ManyToOne(fetch = FetchType.EAGER)
//    private Person p1;
//
//    @NotNull
//    @ManyToOne(fetch = FetchType.EAGER)
//    private Person p2;
//
//    @OneToMany(fetch = FetchType.EAGER, mappedBy = "chat")
//    private List<Message> messages;
//
//    public Chat() {
//        this.messages = new ArrayList<>();
//    }
//
//    public Chat(Person p1, Person p2) {
//        this();
//        this.p1 = p1;
//        this.p2 = p2;    
//    }
//
//    public Person getP1() {
//        return p1;
//    }
//
//    public void setP1(Person p1) {
//        this.p1 = p1;
//    }
//
//    public Person getP2() {
//        return p2;
//    }
//
//    public void setP2(Person p2) {
//        this.p2 = p2;
//    }
//
//    public List<Message> getMessages() {
//        return messages;
//    }
//
//    public void setMessages(List<Message> messages) {
//        this.messages = messages;
//    }
//
//    public Long getChatId() {
//        return chatId;
//    }
//
//    public void setChatId(Long chatId) {
//        this.chatId = chatId;
//    }
//}
