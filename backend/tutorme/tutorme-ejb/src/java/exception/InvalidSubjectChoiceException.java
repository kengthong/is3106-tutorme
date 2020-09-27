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
public class InvalidSubjectChoiceException extends Exception {

    /**
     * Creates a new instance of <code>InvalidSubjectChoiceException</code>
     * without detail message.
     */
    public InvalidSubjectChoiceException() {
    }

    /**
     * Constructs an instance of <code>InvalidSubjectChoiceException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidSubjectChoiceException(String msg) {
        super(msg);
    }
}
