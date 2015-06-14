package com.alimuya.ying.sharedr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import com.alimuya.ying.sharedr.exception.SharedrException;

public class JavaProcessManager implements IProcessManager{
	
	@Override
	public boolean isRunning(int pid) {
		String line;
//		if (OS.isFamilyWindows()) {
//	        //tasklist exit code is always 0. Parse output
//	        //findstr exit code 0 if found pid, 1 if it doesn't
//	        line = "cmd /c \"tasklist /FI \"PID eq " + pid + "\" | findstr " + pid + "\"";
//	    }
//	    else {
//	        //ps exit code 0 if process exists, 1 if it doesn't
//	        line = "ps -p " + pid;
//	    }
		Process proc;
        try {
            proc = Runtime.getRuntime().exec("tasklist");//"tasklist"
            BufferedReader br = new BufferedReader(new InputStreamReader(proc
                    .getInputStream()));
            String info = br.readLine();
            while (info != null) {
                System.out.println(info);
//                if (info.indexOf(exeName) >= 0) {
//                    return true;
//                }
                info = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } 
        return false;
	}
	
	@Override
	public int getSelfPID() throws SharedrException {
		try {
			RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
	        String name = runtime.getName();
	        int index = name.indexOf("@");
	        return Integer.parseInt(name.substring(0, index));
		} catch (Exception e) {
			throw new SharedrException("get PID failed!! ",e);
		}
		
	}
	
	public static void main(String[] args) {
		new JavaProcessManager().isRunning(752);
	}

}
