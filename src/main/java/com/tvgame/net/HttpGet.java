package com.tvgame.net;

import java.io.IOException;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

public class HttpGet extends HttpRequest{
    public HttpGet(String url){
        super(url);
    }
    public HttpGet(){
        super();
    }
    public String getUrl(){
        String temp = super.getUrl();
        if(getParameters().length()>0){
            if(temp.indexOf("?")<0){
                 temp =temp+"?";
            }
            temp = temp +getParameters();
        }
        return temp;
    }
    public String getRequestMethod() {
        return HttpConnection.GET;
    }


}
