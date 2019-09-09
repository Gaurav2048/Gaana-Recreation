package com.exclusive.recreation.musical.Bus;

public class MessageEvent {
    public String message;
    public Boolean flag;

    public MessageEvent(Boolean flag, String message) {
        this.message = message;
        this.flag = flag;
    }
}
