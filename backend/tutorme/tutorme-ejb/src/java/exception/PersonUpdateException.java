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
public class PersonUpdateException extends Exception {

    /**
     * Creates a new instance of <code>UserUpdateException</code> without detail
     * message.
     */
    public PersonUpdateException() {
    }

    /**
     * Constructs an instance of <code>UserUpdateException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public PersonUpdateException(String msg) {
        super(msg);
    }
}
