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
public class TuteeNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>TuteeNotFoundException</code> without
     * detail message.
     */
    public TuteeNotFoundException() {
    }

    /**
     * Constructs an instance of <code>TuteeNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public TuteeNotFoundException(String msg) {
        super(msg);
    }
}
