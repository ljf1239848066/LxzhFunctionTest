package com.lxzh123.funcdemo.ftp;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;

public class FTPManager {
	
	private static Logger log = Logger.getLogger("fileLogger");

	//连接FTP服务器
	public FTPClient getFTPClient() {
		FTPClient ftpClient = new FTPClient();
		ftpClient.setDefaultPort(21);
		try {
		 ftpClient.connect("222.20.19.170");
		 ftpClient.login("Lxzh", "12345678");
		 ftpClient.changeWorkingDirectory("/");
		 log.info("连接FTP服务器成功！");
		} catch (Exception e) {
		 log.error("连接FTP服务器失败！");
		 e.printStackTrace();
		}
		return ftpClient;
	}
	
	//断开FTP连接
	public void disconnetFTPServer(FTPClient ftpClient) {
		if (null != ftpClient) {
			try {
				ftpClient.disconnect();
				log.info("关闭FTP服务器连接成功！");
			} catch (IOException e) {
				log.error("关闭FTP服务器连接失败！");
				e.printStackTrace();
			}
		}
	}
	
	//获取FTP服务器指定目录下的所有文件名
	public Vector<String> getFileListOfFtp(FTPClient ftpClient) {
		Vector<String> result = new Vector<String>();
		FTPFile[] files = null;
		try {
			//不过滤，获取全部文件名
			//files = ftpClient.listFiles();
			//按照文件名后缀过滤，比如.txt类型的文本文件
			files = ftpClient.listFiles("*.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (null != files) {
			for (int j=0; j<files.length; j++) {
				result.add(decode(files[j].getName()));
			}
		}
		return result;
	}
	
	
	//下载文件
	public void downloadFile(FTPClient ftpClient, String remoteFileName, String localFileName) {
		BufferedOutputStream buffOut=null;
		//下载文件保存到本地/sdcard/目录下
		File dir = new File("/sdcard/");
		if (!dir.exists()) {
			dir.mkdir();
		}
		
		File localFile = new File("/sdcard/" + localFileName);
        try{
            buffOut=new BufferedOutputStream(new FileOutputStream(localFile));
            ftpClient.retrieveFile(remoteFileName, buffOut);
            log.info("下载文件：" + localFileName + "成功！");
        }catch(Exception e){
        	log.error("下载文件：" + localFileName + "失败！");
        	e.printStackTrace();
	    	try {
				buffOut.close();
			} catch (IOException e1) {
				log.error("关闭流异常");
				e1.printStackTrace();
			}
	    	localFile.delete();
            return;
        }finally{
        	try{
        		if(buffOut!=null){
        			buffOut.close();
        		}
        	}catch(Exception e){
        		log.error("关闭流异常");
        		e.printStackTrace();
        	}
        }
	}
	
	//转码（FTP服务器上获取到中文文件名会是乱码，需要处理一下）
	public String decode(Object obj){
		try{
			if(obj==null)
				return "";
      else
        return new String(obj.toString().getBytes("iso-8859-1"),"GBK");
		}catch(Exception e){
			return "";
		}
	}
	
	public String code(Object obj){
		try{
			if(obj==null)
				return "";
            else
                return new String(obj.toString().getBytes("GBK"),"iso-8859-1");
		}catch(Exception e){
			return "";
		}
	}

}
