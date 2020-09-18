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
public class OfferNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>OfferNotFoundException</code> without
     * detail message.
     */
    public OfferNotFoundException() {
    }

    /**
     * Constructs an instance of <code>OfferNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public OfferNotFoundException(String msg) {
        super(msg);
    }
}
