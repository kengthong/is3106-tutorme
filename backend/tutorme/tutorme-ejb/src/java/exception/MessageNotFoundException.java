/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

/**
 *
 * @author Tay Z H Owen
 */
public class MessageNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>MessageNotFoundException</code> without
     * detail message.
     */
    public MessageNotFoundException() {
    }

    /**
     * Constructs an instance of <code>MessageNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public MessageNotFoundException(String msg) {
        super(msg);
    }
}
