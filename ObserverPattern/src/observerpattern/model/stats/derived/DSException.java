/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package observerpattern.model.stats.derived;

/**
 *
 * @author KeithW
 */
public class DSException extends Exception {

    /**
     * Creates a new instance of <code>DSException</code> without detail
     * message.
     */
    public DSException() {
    }

    /**
     * Constructs an instance of <code>DSException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public DSException(String msg) {
        super(msg);
    }
}
