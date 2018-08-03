package com.lxzh123.funcdemo.socket;

import android.annotation.SuppressLint;
import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

enum LogType {
    v,//Verbose
    d,//Debug
    i,//Information
    w,//Warning
    e //Error
}

interface ILog{
	/// <summary>
    /// Verbose Log
    /// </summary>
    public void v(String Tag, String msg);
    /// <summary>
    /// Debug Log
    /// </summary>
    public void d(String Tag, String msg);
    /// <summary>
    /// Information Log
    /// </summary>
    public void i(String Tag, String msg);
    /// <summary>
    /// Warning Log
    /// </summary>
    public void w(String Tag, String msg);
    /// <summary>
    /// Error Log
    /// </summary>
    public void e(String Tag, String msg);
}
class NoneLog implements ILog{
    public void v(String Tag, String msg){}
    public void d(String Tag, String msg){}
    public void i(String Tag, String msg){}
    public void w(String Tag, String msg){}
    public void e(String Tag, String msg){}
}

/// <summary>
/// 全局日志类
/// </summary>
@SuppressLint("SimpleDateFormat")
public class LxzhLog implements ILog{
    private static String logName = "";//日志文件
    private static String logPath = Environment.getExternalStorageDirectory()+"/log";
	private static SimpleDateFormat LogDateSdf = new SimpleDateFormat("yyyy-MM-dd"); // 日志的输出格式
    private static SimpleDateFormat LogTimeSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); // 日志日期输出格式
    
    private static Lock lock=new ReentrantLock();  
    
    private static String getLogFileName(LogType type) {
        return String.format("%s/%s_%s%s", logPath, type, LogDateSdf.format(new Date()), ".log");
    }
    /// <summary>
    /// Verbose Log
    /// </summary>
    public void v(String Tag, String msg) {
        writeLog(LogType.v, Tag, msg);
    }
    /// <summary>
    /// Debug Log
    /// </summary>
    public void d(String Tag, String msg) {
        writeLog(LogType.d, Tag, msg);
    }
    /// <summary>
    /// Information Log
    /// </summary>
    public void i(String Tag, String msg) {
        writeLog(LogType.i, Tag, msg);
    }
    /// <summary>
    /// Warning Log
    /// </summary>
    public void w(String Tag, String msg) {
        writeLog(LogType.w, Tag, msg);
    }
    /// <summary>
    /// Error Log
    /// </summary>
    public void e(String Tag, String msg) {
        writeLog(LogType.e, Tag, msg);
    }
    /// <summary>
    /// 写入日志信息
    /// </summary>
    /// <param name="Tag">Log日志标签</param>
    /// <param name="msg">要写入的内容</param>
    private void writeLog(LogType type, String Tag, String msg) {
        try {
        	System.out.println(msg);
        	File dir=new File(logPath);
            if (!dir.exists()) {
                dir.mkdirs();
                System.out.println("mkdirs");
            }
            logName = getLogFileName(type);//记录网元操作输入的日志
            System.out.println(logName);
            writeMsg(logName, String.format("【%s】:【%s】:%s", LogTimeSdf.format(new Date()), Tag, msg),true);
        } catch(Exception ex) { }
    }
    /**
	 * 打开日志文件并写入日志
	 * 
	 * @return
	 * **/
	private static void writeMsg(String logName, String msg, boolean append) {
		File file = new File(logName);
		try {
			lock.lock();
			FileWriter filerWriter = new FileWriter(file, append);// 后面这个参数代表是不是要接上文件中原来的数据，不进行覆盖
			BufferedWriter bufWriter = new BufferedWriter(filerWriter);
			bufWriter.write(msg);
			bufWriter.newLine();
			bufWriter.close();
			filerWriter.close();
		} catch (Exception ex) { } 
		finally{
			lock.unlock();
		}
	}
	public static void ClearLog() {
        try {
            File di = new File(logPath);
            if (di.exists()) {
            	File[] fis=di.listFiles();
            	for (File file : fis) {
            		file.delete();
				}
            }
        } catch (Exception ex) { }
    }
}