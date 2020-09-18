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
public class TimeslotNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>TimeslotNotFoundException</code> without
     * detail message.
     */
    public TimeslotNotFoundException() {
    }

    /**
     * Constructs an instance of <code>TimeslotNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public TimeslotNotFoundException(String msg) {
        super(msg);
    }
}
