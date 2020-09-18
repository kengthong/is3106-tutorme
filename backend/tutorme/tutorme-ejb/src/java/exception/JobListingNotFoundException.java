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
public class JobListingNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>JobListingNotFoundException</code>
     * without detail message.
     */
    public JobListingNotFoundException() {
    }

    /**
     * Constructs an instance of <code>JobListingNotFoundException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public JobListingNotFoundException(String msg) {
        super(msg);
    }
}
