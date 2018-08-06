package com.lxzh123.funcdemo.socket;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lxzh123.funcdemo.R;
import com.lxzh123.funcdemo.xml.XmlSaveDataActivity;

import org.apache.log4j.helpers.OptionConverter;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.net.InetSocketAddress;

public class SocketClientActivity1 extends Activity {
    private TextView tvReceiveMsg;
    private EditText etIP;
    private EditText etPort;
    private EditText etSend;
    private Button btConnect;
    private Button btDisConnect;
    private Button btSend;
    private ILog log;
    private boolean logEnable=false;//全局日志开关

    private Intent socketServerIntent;
    private SocketClient client;

    private final static String TAG="SocketClientActivity1";

    private Handler handler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            if(msg.what==0){
                tvReceiveMsg.append(msg.obj.toString());
                log.i(TAG, msg.obj.toString());
            }else if(msg.what==1){
                Toast.makeText(getBaseContext(), msg.obj.toString(), Toast.LENGTH_LONG).show();
            }else if(msg.what==2){

            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_socket_client1);
        if(logEnable){
            log=new LxzhLog();
        }else{
            log=new NoneLog();
        }



        socketServerIntent=new Intent(this,SocketServerService.class);
        startService(socketServerIntent);
        Log.i(TAG,"startService:socketServerIntent");

//		tvIP=(TextView)findViewById(R.id.tvIp);
//		tvPort=(TextView)findViewById(R.id.tvPort);
//		tvSend=(TextView)findViewById(R.id.tvSend);
//		tvReceive=(TextView)findViewById(R.id.tvReceive);
        tvReceiveMsg=(TextView)findViewById(R.id.tvReceiveMsg);

        etIP=(EditText)findViewById(R.id.etIP);
        etPort=(EditText)findViewById(R.id.etPort);
        etSend=(EditText)findViewById(R.id.etSend);

        btConnect=(Button)findViewById(R.id.btConnect);
        btDisConnect=(Button)findViewById(R.id.btDisConnect);
        btSend=(Button)findViewById(R.id.btSend);
        log.d(TAG, "onCreate");

        SharedPreferences preferences=this.getSharedPreferences("XmlSaveData.xml",XmlSaveDataActivity.MODE_PRIVATE);
        String ip=preferences.getString("savedip","");
        String port=preferences.getString("savedport","");
        if(ip!=null&&ip!=""){
            etIP.setText(ip);
        }
        if(port!=null&&port!=""){
            etPort.setText(port);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void OnButtonClicked(View view){
        log.d(TAG, "OnButtonClicked");
        switch (view.getId()) {
            case R.id.btConnect:
                log.d(TAG, "OnButtonClicked:R.id.btConnect");
//                doConnect();
                EventBus.getDefault().post("");
                break;
            case R.id.btDisConnect:
                log.d(TAG, "OnButtonClicked:R.id.btDisConnect");
                doDisConnect();
                break;
            case R.id.btSend:
                log.d(TAG, "OnButtonClicked:R.id.btSend");
                doSendMsg();
                break;
            default:
                log.d(TAG, "OnButtonClicked:default");
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onMessageEvent(Object obj) {
        final String ip=etIP.getText().toString();
        final String port=etPort.getText().toString();
        Message message=Message.obtain();
        if(ip.equals("")||port.equals("")){
            message.what=1;
            message.obj="请输入服务器Ip和端口号";
        }else{
            message.what=0;
            try {
                client=new SocketClient();
//                            client.Connect(new InetSocketAddress("localhost",SocketServerService.PORT));
                client.Connect(new InetSocketAddress(ip, OptionConverter.toInt(port,SocketServerService.PORT)));

                SharedPreferences preferences=getSharedPreferences("XmlSaveData.xml", XmlSaveDataActivity.MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("savedip",ip);
                editor.putString("savedport",port);
                editor.commit();//save data to xml


                if(client.getState()){
                    message.obj="Connect Succeed\n";
                }else{
                    message.obj="Connect Failed\n";
                }
            } catch (Exception ex) {
                System.out.println(ex.toString());
                message.obj="Connect Failed\n";
            }
        }
        handler.sendMessage(message);
    }

    private void doConnect(){
        final String ip=etIP.getText().toString();
        final String port=etPort.getText().toString();


        new Thread(){
            @Override
            public void run() {
                super.run();
                Message message=Message.obtain();
                if(ip.equals("")||port.equals("")){
                    message.what=1;
                    message.obj="请输入服务器Ip和端口号";
                    handler.sendMessage(message);
                    return;
                }
                try {
                    client=new SocketClient();
//                            client.Connect(new InetSocketAddress("localhost",SocketServerService.PORT));
                    client.Connect(new InetSocketAddress(ip, OptionConverter.toInt(port,SocketServerService.PORT)));

                    SharedPreferences preferences=getSharedPreferences("XmlSaveData.xml", XmlSaveDataActivity.MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("savedip",ip);
                    editor.putString("savedport",port);
                    editor.commit();//save data to xml

                    message.what=0;
                    if(client.getState()){
                        message.obj="Connect Succeed\n";
                    }else{
                        message.obj="Connect Failed\n";
                    }
                    handler.sendMessage(message);
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
            }
        }.start();
    }

    private void doDisConnect(){
        if(client!=null||client.IsConnected()){
            client.Close();
            tvReceiveMsg.append("DisConnected\n");
        }
    }

    private void doSendMsg(){
        final String msg=etSend.getText().toString().trim();
        if(msg.equals("")){
            Toast.makeText(getBaseContext(), "请输入要发送的消息!", Toast.LENGTH_LONG).show();
            return;
        }
        if(client==null||!client.getState()){
            Toast.makeText(getBaseContext(), "请先连接服务器!", Toast.LENGTH_LONG).show();
            return;
        }
        new Thread(){
            @Override
            public void run() {
                try {
                    String receive=client.SendAndReceive(msg, 30000);

                    Message message=Message.obtain();
                    message.what=0;
                    message.obj=receive;
                    handler.sendMessage(message);
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }

                super.run();
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        try {
            if (client != null) {
                client.Close();
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        stopService(socketServerIntent);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
