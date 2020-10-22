///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package session;
//
//import entity.Chat;
//import exception.ChatNotFoundException;
//import java.util.List;
//import javax.ejb.Local;
//
///**
// *
// * @author Owen Tay
// */
//@Local
//public interface ChatSessionLocal {
//    
//    public Chat createChat(Long p1Id, Long p2Id);
//    
//    public Chat retrieveChatById(Long chatId) throws ChatNotFoundException;
//    
//    public Chat retrieveChatByPersonIds(Long p1Id, Long p2Id) throws ChatNotFoundException;
//    
//    public List<Chat> retrieveChatsByPersonId(Long personId);
//    
//}
