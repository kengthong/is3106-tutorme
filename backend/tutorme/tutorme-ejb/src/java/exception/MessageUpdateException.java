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
public class MessageUpdateException extends Exception {

    /**
     * Creates a new instance of <code>MessageUpdateException</code> without
     * detail message.
     */
    public MessageUpdateException() {
    }

    /**
     * Constructs an instance of <code>MessageUpdateException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public MessageUpdateException(String msg) {
        super(msg);
    }
}
