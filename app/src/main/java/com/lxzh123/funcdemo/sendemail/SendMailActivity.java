package com.lxzh123.funcdemo.sendemail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lxzh123.funcdemo.R;
import com.lxzh123.funcdemo.util.mail.MultiMailsender;
import com.lxzh123.funcdemo.util.mail.MultiMailsender.MultiMailSenderInfo;

public class SendMailActivity extends Activity {
    private final static String TAG="SendMailActivity";
    private EditText etUserName,etPassword,etSendAddr,etToAddr;
	private EditText etTitle, etContent;
	private Button btnSend;
	private SendEmailThread sendThread;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sendmail);
        etUserName=(EditText) findViewById(R.id.etUserName);
        etPassword=(EditText) findViewById(R.id.etPassword);
        etSendAddr=(EditText) findViewById(R.id.etSendAddr);
        etToAddr=(EditText) findViewById(R.id.etToAddr);
		etTitle = (EditText) findViewById(R.id.title);
		etContent = (EditText) findViewById(R.id.content);
		btnSend = (Button) findViewById(R.id.send);
		btnSend.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// 这个类主要是设置邮件
				String title = etTitle.getText().toString();
				String content = etContent.getText().toString();
				String username = etUserName.getText().toString();
                String password = etPassword.getText().toString();
                String sendaddr = etSendAddr.getText().toString();
                String toaddr = etToAddr.getText().toString();
				if (title.length() > 0 && content.length() > 0) {
                    sendThread=new SendEmailThread(title,content,username,password,sendaddr,toaddr);
                    sendThread.setToAddrs(new String[]{toaddr});
                    sendThread.setCcAddrs(new String[]{"1239848066@qqcom"});
                    sendThread.start();
				} else if (title.length() <= 0) {
					Toast.makeText(getBaseContext(), "请输入邮件标题",
							Toast.LENGTH_LONG).show();
				} else if (content.length() <= 0) {
					Toast.makeText(getBaseContext(), "请输入邮件正文",
							Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	private class SendEmailThread extends Thread{
	    private String title;
	    private String content;
        private String username;
        private String password;
        private String sendaddr;
        private String toaddr;
        private String[] toAddrs;
        private String[] ccAddrs;
        private String[] bccAddrs;

        public SendEmailThread(String title, String content, String username, String password, String sendaddr, String toaddr) {
            this.title = title;
            this.content = content;
            this.username = username;
            this.password = password;
            this.sendaddr = sendaddr;
            this.toaddr = toaddr;
        }

        public void setToAddrs(String[] toAddrs) {
            this.toAddrs = toAddrs;
        }

        public void setCcAddrs(String[] ccAddrs) {
            this.ccAddrs = ccAddrs;
        }

        public void setBccAddrs(String[] bccAddrs) {
            this.bccAddrs = bccAddrs;
        }

        private boolean sendByThirdPartyEmail(){
            // 这个类主要是设置邮件
            MultiMailSenderInfo mailInfo = new MultiMailSenderInfo();
            mailInfo.setMailServerHost("smtp.163.com");
            mailInfo.setMailServerPort("25");
            mailInfo.setValidate(true);
            mailInfo.setUserName(username);
            mailInfo.setPassword(password);// 您的邮箱密码
            mailInfo.setFromAddress(sendaddr);
            mailInfo.setToAddress(toaddr);
            mailInfo.setSubject(title);
            mailInfo.setContent(content);
            String[] receivers = new String[] { "1239848066@qq.com"};
            String[] ccs = receivers;
            mailInfo.setReceivers(receivers);
            mailInfo.setCcs(ccs);
            // 这个类主要来发送邮件
            MultiMailsender sms = new MultiMailsender();
            boolean rst=sms.sendTextMail(mailInfo);// 发送文体格式
            // MultiMailsender.sendHtmlMail(mailInfo);//发送html格式
            MultiMailsender.sendMailtoMultiCC(mailInfo);//
            return rst;
        }

        private boolean sendBySysEmail(){
            Intent intent=new Intent(Intent.ACTION_SEND);
            if(toAddrs==null||toAddrs.length<1){
                Log.e(TAG,"send email must set to address first!");
                return false;
            }else{
                intent.putExtra(Intent.EXTRA_EMAIL,toAddrs);
            }

            if(ccAddrs!=null&&ccAddrs.length>0){
                intent.putExtra(Intent.EXTRA_CC,ccAddrs);
            }
            if(bccAddrs!=null&&bccAddrs.length>0){
                intent.putExtra(Intent.EXTRA_BCC,bccAddrs);
            }

            intent.putExtra(Intent.EXTRA_SUBJECT,title);
            intent.putExtra(Intent.EXTRA_TEXT,content);
            startActivity(intent);
            return true;
        }

        @Override
        public void run() {
            super.run();
            boolean rst=sendByThirdPartyEmail();
            sendBySysEmail();
            if(rst){
                Toast.makeText(getApplicationContext(),"发送成功", Toast.LENGTH_LONG).show();
            }
        }
    };

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(sendThread!=null&&sendThread.isAlive()){
            try{
                sendThread.interrupt();
            }catch (Exception ex){

            }
        }
    }
}
