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
public class OfferWithdrawException extends Exception {

    /**
     * Creates a new instance of <code>OfferWithdrawException</code> without
     * detail message.
     */
    public OfferWithdrawException() {
    }

    /**
     * Constructs an instance of <code>OfferWithdrawException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public OfferWithdrawException(String msg) {
        super(msg);
    }
}
