package com.github.edurbs.datsa.infra.service.report;

public class ReportException extends RuntimeException {

    public ReportException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ReportException(String msg) {
        super(msg);
    }

}
