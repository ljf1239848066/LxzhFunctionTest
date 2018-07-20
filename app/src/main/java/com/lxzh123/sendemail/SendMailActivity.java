package com.lxzh123.sendemail;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lxzh123.funcdemo.R;
import com.lxzh123.util.mail.MultiMailsender;
import com.lxzh123.util.mail.MultiMailsender.MultiMailSenderInfo;

public class SendMailActivity extends Activity {
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
        String username;
        String password;
        String sendaddr;
        String toaddr;

        public SendEmailThread(String title, String content, String username, String password, String sendaddr, String toaddr) {
            this.title = title;
            this.content = content;
            this.username = username;
            this.password = password;
            this.sendaddr = sendaddr;
            this.toaddr = toaddr;
        }

        @Override
        public void run() {
            super.run();
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
            String[] receivers = new String[] { "1239848066@qq.com",
                    "1440924340@qq.com" };
            String[] ccs = receivers;
            mailInfo.setReceivers(receivers);
            mailInfo.setCcs(ccs);
            // 这个类主要来发送邮件
            MultiMailsender sms = new MultiMailsender();
            sms.sendTextMail(mailInfo);// 发送文体格式
            // MultiMailsender.sendHtmlMail(mailInfo);//发送html格式
            MultiMailsender.sendMailtoMultiCC(mailInfo);//
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
