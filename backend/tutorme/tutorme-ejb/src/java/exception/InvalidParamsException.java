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
public class InvalidParamsException extends Exception {

    /**
     * Creates a new instance of <code>InvalidParametersException</code> without
     * detail message.
     */
    public InvalidParamsException() {
    }

    /**
     * Constructs an instance of <code>InvalidParametersException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidParamsException(String msg) {
        super(msg);
    }
}
