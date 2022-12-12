package com.example.debugfx;

public class MarketResponse {
    private boolean successful;
    private String errorMsg;
    private ErrorType errorType;

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }
}

enum ErrorType {
    NO_ERR,
    CONNECTION_ERR,
    MARKET_ERR
}
