package com.cashrich.CashRichAssessment.Exception;

public class CashRichException extends RuntimeException {

    public CashRichException(String message) {
        super(message);
    }

    public CashRichException(String message, Throwable cause) {
        super(message, cause);
    }
}
