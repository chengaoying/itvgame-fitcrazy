package com.tvgame.net;

import java.io.UnsupportedEncodingException;

public class HttpResponse {

    private byte data[];

    public HttpResponse(byte arg[]){
        data = arg;
    }
    public HttpResponse(){

    }
    private byte[] getData(){
        return data;
    }

    public String getString(){
        try {
            return new String(data, "utf-8");
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
