package com.tvgame.net;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.io.Connection;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

public class HttpClinet {
   public HttpResponse execute(HttpRequest request) throws IOException{
       HttpConnection con = request.getHttpConnection();
       //»ñÈ¡ÏìÓ¦Âë
       int rescode = con.getResponseCode();
       DataInputStream dis = con.openDataInputStream();
       int length = (int) con.getLength();
       byte data[];
       if(length>0){
           data = new byte[length];
           dis.readFully(data);
       }else{
           ByteArrayOutputStream bous = new  ByteArrayOutputStream();
           int c = -1;
           while((c = dis.read())!=-1){
                bous.write(c);
           }
           bous.flush();
           data = bous.toByteArray();
           try {
               bous.close();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
//       System.out.println(url);
       return new HttpResponse(data);
   }
}
