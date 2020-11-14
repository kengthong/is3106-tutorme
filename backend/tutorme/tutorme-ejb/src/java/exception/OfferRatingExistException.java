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
public class OfferRatingExistException extends Exception {

    /**
     * Creates a new instance of <code>OfferRatingExistException</code> without
     * detail message.
     */
    public OfferRatingExistException() {
    }

    /**
     * Constructs an instance of <code>OfferRatingExistException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public OfferRatingExistException(String msg) {
        super(msg);
    }
}
