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
public class SubjectNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>SubjectNotFoundException</code> without
     * detail message.
     */
    public SubjectNotFoundException() {
    }

    /**
     * Constructs an instance of <code>SubjectNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public SubjectNotFoundException(String msg) {
        super(msg);
    }
}
