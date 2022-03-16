package com.example.yibanke.api.exception;

import lombok.Data;

@Data
public class YibankeException extends RuntimeException{
    private String msg;
    private int code = 500;

    public YibankeException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public YibankeException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public YibankeException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public YibankeException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }
}

