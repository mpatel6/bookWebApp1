/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.mpatel.bookwebapp1.model;

/**
 *
 * @author Ankita
 */
public class DataAccessException extends Exception {
    public DataAccessException(String message, Throwable cause) {
        super(message,cause);
    }
}
