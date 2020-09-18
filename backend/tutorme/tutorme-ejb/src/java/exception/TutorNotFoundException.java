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
public class TutorNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>TutorNotFoundException</code> without
     * detail message.
     */
    public TutorNotFoundException() {
    }

    /**
     * Constructs an instance of <code>TutorNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public TutorNotFoundException(String msg) {
        super(msg);
    }
}
