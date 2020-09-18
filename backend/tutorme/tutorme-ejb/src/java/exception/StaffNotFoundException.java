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
public class StaffNotFoundException extends Exception {
    /**
     * Creates a new instance of <code>UserNotFoundException</code> without
     * detail message.
     */
    public StaffNotFoundException() {
    }

    /**
     * Constructs an instance of <code>UserNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public StaffNotFoundException(String msg) {
        super(msg);
    }
}
