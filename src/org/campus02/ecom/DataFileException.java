package org.campus02.ecom;

public class DataFileException extends Exception{
    public DataFileException(String message) {
        super(message);
    }

    //Exception-Chaining
    public DataFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
