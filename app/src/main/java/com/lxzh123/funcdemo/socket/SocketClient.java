package com.lxzh123.funcdemo.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class SocketClient {
    private final static String ENCODING="utf-8";

    private Socket client;
    private boolean state = false;
    private InputStream iReader = null;
    private PrintWriter pWriter = null;

    public boolean getState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	public SocketClient(){
        client = new Socket();
    }
	
	public void Connect(String ip,int port) throws IOException{
		SocketAddress socketAdd=new InetSocketAddress(ip, port);
		client.connect(socketAdd, 3000);

        state=client.isConnected();
	}

    public void Connect(InetAddress ip, int port) throws IOException{
        client=new Socket(ip,port);
        state=client.isConnected();
    }

//    public String Send(String msg, int timeout) {
//        String result = "";
//        if (!msg.equals("") && client != null && client.isConnected()) {
//            byte[] bs = NetUtil.Encode.GetBytes(msg);
//            client.Send(bs);
//        }
//        return result;
//    }
    public String SendAndReceive(String msg, int timeout) throws UnsupportedEncodingException, IOException {
        String result = "";
        if (!msg.equals("")  && client != null && client.isConnected()) {
        	iReader = client.getInputStream();
        	pWriter = new PrintWriter(new OutputStreamWriter(client.getOutputStream(),ENCODING));
        	pWriter.write(msg, 0, msg.length());
        	pWriter.flush();
            client.setSoTimeout(timeout);
            System.out.println("BufferSize:"+client.getReceiveBufferSize());
            int size=1024;
            byte[] srvMsg=new byte[size];
            System.out.println("Size:"+size);
            int len=iReader.read(srvMsg);
            System.out.println("ReadSize:"+len);
            if (len > 0) {
                result = new String(getSubBytes(srvMsg,0,len),ENCODING);
            }else{
            	result = "null";
            }
            System.out.println("result:"+result);
        }
        return result;
    }
    
    private byte[] getSubBytes(byte[] array,int start,int len){
    	int btlen=array.length;
    	byte[] result=null;
    	if(len>0&&start>=0&&start+len<=btlen){
    		result=new byte[len];
    		for(int i=start,j=0;j<len;i++,j++){
    			result[j]=array[i];
    		}
    	}
    	return result;
    }
    public void Close() {
        if (client != null) {
            try {
                client.close();
            } catch(Exception ex) {
            	System.out.println(ex.toString());
            }
        }
        state = false;
    }
}
