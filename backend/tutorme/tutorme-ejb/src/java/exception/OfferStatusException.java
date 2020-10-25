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
public class OfferStatusException extends Exception {

    /**
     * Creates a new instance of <code>OfferWithdrawException</code> without
     * detail message.
     */
    public OfferStatusException() {
    }

    /**
     * Constructs an instance of <code>OfferWithdrawException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public OfferStatusException(String msg) {
        super(msg);
    }
}
