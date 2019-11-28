package spring_rabbitmq_test.entity;

import java.io.Serializable;

public class Msg implements Serializable {
    private String msgHead;
    private String msgBody;

    public Msg(){

    }

    public Msg(String msgHead, String msgBody) {
        this.msgHead = msgHead;
        this.msgBody = msgBody;
    }

    public String getMsgHead() {
        return msgHead;
    }

    public Msg setMsgHead(String msgHead) {
        this.msgHead = msgHead;
        return this;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public Msg setMsgBody(String msgBody) {
        this.msgBody = msgBody;
        return this;
    }
}