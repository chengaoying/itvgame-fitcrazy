package com.tvgame.net;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

public abstract class HttpRequest {


    private String url;

    private Hashtable headers;

    private StringBuffer parameters;

    public HttpRequest(String url){
       setUrl(url);
       parameters = new StringBuffer();
    }
    public HttpRequest(){
        this(null);
    }
    public void addParameter(String name,String value){
        if(parameters.length()>0){
            parameters.append("&");
        }
        parameters.append(name);
        parameters.append("=");
        parameters.append(value);
    }
    public String getParameters(){
        return parameters.toString();
    }
    public abstract String getRequestMethod();

    public void setUrl(String url){
        this.url = url;
    }
    public String getUrl(){
        return url;
    }

    public void setHeader(String name,String value){
        if(headers == null){
            headers = new Hashtable(4);
        }
        headers.put(name, value);
    }
    public void copyHeaders2HttpConnection(HttpConnection con) throws IOException{
        if(headers != null && headers.size()>0){
            Enumeration emu = headers.keys();
            Object key;
            while(emu.hasMoreElements()){
                key = emu.nextElement();
                con.setRequestProperty(String.valueOf(key), String.valueOf(headers.get(key)));
            }
        }
    }
    public HttpConnection getHttpConnection ()throws IOException {
        HttpConnection con = null;
        con = (HttpConnection) Connector.open(getUrl());
        con.setRequestMethod(getRequestMethod());
        copyHeaders2HttpConnection(con);

        return con;
    }
}
