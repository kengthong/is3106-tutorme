/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import entity.Message;
import java.util.Comparator;

/**
 *
 * @author Owen Tay
 */
public class MessageDateComaparator implements Comparator<Message> {
    @Override
    public int compare(Message m1, Message m2) {
        return m1.getCreatedDate().compareTo(m2.getCreatedDate());
    }
}
