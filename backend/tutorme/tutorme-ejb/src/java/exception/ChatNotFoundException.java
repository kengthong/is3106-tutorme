/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

/**
 *
 * @author Owen Tay
 */
public class ChatNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>ChatNotFoundException</code> without
     * detail message.
     */
    public ChatNotFoundException() {
    }

    /**
     * Constructs an instance of <code>ChatNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ChatNotFoundException(String msg) {
        super(msg);
    }
}
