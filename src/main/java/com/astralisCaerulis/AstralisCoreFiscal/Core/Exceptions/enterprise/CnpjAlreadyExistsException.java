package com.astralisCaerulis.AstralisCoreFiscal.Core.Exceptions.enterprise;

public class CnpjAlreadyExistsException extends RuntimeException {

  public CnpjAlreadyExistsException(String message) {
    super(message);
  }

  public CnpjAlreadyExistsException(String message, Throwable cause) {
    super(message, cause);
  }
}