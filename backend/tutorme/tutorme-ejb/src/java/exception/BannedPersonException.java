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
public class BannedPersonException extends Exception {

    /**
     * Creates a new instance of <code>BannedPersonException</code> without
     * detail message.
     */
    public BannedPersonException() {
    }

    /**
     * Constructs an instance of <code>BannedPersonException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public BannedPersonException(String msg) {
        super(msg);
    }
}
