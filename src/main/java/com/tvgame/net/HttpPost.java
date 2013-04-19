package com.tvgame.net;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

public class HttpPost extends HttpRequest {

    public String getRequestMethod() {
        return HttpConnection.POST;
    }

    public HttpConnection getHttpConnection ()throws IOException {
        HttpConnection con = null;
        con = super.getHttpConnection();
        DataOutputStream dos = con.openDataOutputStream();
        byte data[] = null;
        String postString = getParameters();
        if(postString.length()>0){
            try {
                data = postString.getBytes("utf-8");
            } catch (Exception e) {
                data = postString.getBytes();
            }
//            con.setRequestProperty("Content-Length", String.valueOf(data.length));
//            con.setRequestProperty("Content-Type", "");
            dos.write(data);
        }
//        try {
//            if(dos!=null){
//                dos.close();
//            }
//        } catch (Exception e) {}
        return con;
    }

}
