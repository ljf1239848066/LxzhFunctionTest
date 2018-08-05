package com.lxzh123.funcdemo.socket;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class SocketServerService extends IntentService {

    private final static String TAG="SocketServerService";
    public final static int PORT=8080;

    private ServerSocket serverSocket;

    public SocketServerService() {
        super("SocketServerService");
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.i(TAG,"onStart");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.i(TAG,"onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG,"onBind");
        return super.onBind(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG,"onHandleIntent");
        if (intent != null) {
            final String action = intent.getAction();
        }
        startListen();
    }

    private void startListen(){
        Socket socket = null;
        BufferedReader br = null;
        PrintWriter pw = null;
        Scanner scanner=new Scanner(System.in);
        try {
            // 设定服务端的端口号
            serverSocket = new ServerSocket(PORT);
            System.out.println("ServerSocket Start:" + serverSocket);
            // 等待请求,此方法会一直阻塞,直到获得请求才往下走
            socket = serverSocket.accept();
            System.out.println("Connection accept socket:" + socket);
            // 用于接收客户端发来的请求
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // 用于发送返回信息,可以不需要装饰这么多io流使用缓冲流时发送数据要注意调用.flush()方法
            pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            System.out.println("start to read data from client:" + socket);
            int bt=0;
            char buffer[]=new char[1024];
            String str;
            while((bt=br.read(buffer))!=-1){
                str=new String(buffer,0,bt);
                System.out.println("Client Socket Message:" + str);
                pw.println("Message Received:"+str);
                pw.flush();
            }
//            while (true) {
//                String str = br.readLine();
////                tvText.append("Client Socket Message:" + str);
//                System.out.println("Client Socket Message:" + str);
////                Thread.sleep(1000);
////                tvText.append("Message Received");
//                str=scanner.next();
//                pw.println("Message Received");
//                pw.flush();
//                if(str.equals("exit")){
//                    break;
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Close.....");
            try {
                br.close();
                pw.close();
                socket.close();
                serverSocket.close();
            } catch (Exception e2) {

            }
        }
    }
}
