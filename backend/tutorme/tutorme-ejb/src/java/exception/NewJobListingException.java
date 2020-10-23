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
public class NewJobListingException extends Exception {

    /**
     * Creates a new instance of <code>NewJobListingError</code> without detail
     * message.
     */
    public NewJobListingException() {
    }

    /**
     * Constructs an instance of <code>NewJobListingError</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public NewJobListingException(String msg) {
        super(msg);
    }
}
