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
public class PersonLoginFailException extends Exception {

    /**
     * Creates a new instance of <code>UserLoginFailException</code> without
     * detail message.
     */
    public PersonLoginFailException() {
    }

    /**
     * Constructs an instance of <code>UserLoginFailException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public PersonLoginFailException(String msg) {
        super(msg);
    }
}
