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
public class RegistrationFailException extends Exception {

    /**
     * Creates a new instance of <code>RegistrationFailException</code> without
     * detail message.
     */
    public RegistrationFailException() {
    }

    /**
     * Constructs an instance of <code>RegistrationFailException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public RegistrationFailException(String msg) {
        super(msg);
    }
}
