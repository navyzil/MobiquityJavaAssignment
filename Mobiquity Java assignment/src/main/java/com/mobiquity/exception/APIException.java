package com.mobiquity.exception;

/**
 * @author Denzil Gideon M. Daulo
 * APIException - Customized Exception for Packaging Challenge
 */
public class APIException extends Exception {

  /**
   * APIException to handle other runtime exceptions like FileIOException and give a user friendly exception
   * @param message - the exception message
   * @param e - The exception to be handled
   */
  public APIException(String message, Exception e) {
    super(message, e);
  }

  /**
   * APIException to provide friendly exception messages
   * @param message - the exception message
   */
  public APIException(String message) {
    super(message);
  }
}
