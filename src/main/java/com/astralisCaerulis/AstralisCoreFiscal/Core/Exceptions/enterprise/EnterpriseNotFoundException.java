package com.astralisCaerulis.AstralisCoreFiscal.Core.Exceptions.enterprise;

public class EnterpriseNotFoundException extends RuntimeException {

  public EnterpriseNotFoundException(String message) {
    super(message);
  }

  public EnterpriseNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
