package com.alimuya.ying.sharedr.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author ov_alimuya
 *
 */
public class FileUtils {
	
	public static void writeBytesFile(byte[] bs, String file_path)
			throws IOException {
		writeBytesFile(bs, file_path, false);
	}

	
	public static void writeBytesFile(byte[] bs, String file_path,
			boolean append) throws IOException {
		File file = new File(file_path);
		createDir(file.getParentFile());
		FileOutputStream fos = new FileOutputStream(file, append);
		try {
			fos.write(bs);
			fos.flush();
		} finally {
			fos.close();
		}
	}

	public static byte[] readBytesFile(String file_path) throws IOException {
		File file = new File(file_path);
		if (!file.isFile()) {
			throw new IOException("not file");
		}
		FileInputStream fis = new FileInputStream(file);
		DataInputStream dis = new DataInputStream(fis);
		int len = (int) file.length();
		byte[] bs = new byte[len];
		try {
			dis.readFully(bs);
			return bs;
		} finally {
			dis.close();
		}
	}

	public static FileInputStream getInputStreamFile(String file_path)
			throws FileNotFoundException {
		File file = new File(file_path);
		if (!file.isFile()) {
			return null;
		} else {
			return new FileInputStream(file_path);
		}
	}
	
	public static FileOutputStream getOutputStreamFile(String file_path)
			throws IOException {
		File file = new File(file_path);
		if(file.isDirectory()){
			file.delete();
		}
		createDir(file.getParentFile());
		file.createNewFile();
		return new FileOutputStream(file);
	}

	public static byte[] readBytesInputStream(InputStream is)
			throws IOException {
		return readBytesInputStream(is, 10240);
	}

	public static byte[] readBytesInputStream(InputStream is, int buffer)
			throws IOException {
		if (is == null) {
			return null;
		}
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int size = 0;
			byte[] bs = new byte[buffer];
			while ((size = is.read(bs)) != -1) {
				bos.write(bs, 0, size);
			}
			return bos.toByteArray();
		} finally {
			is.close();
		}
	}

	public static void copyFile(File inFile, File outFile) throws IOException {
	    createDir(outFile.getParentFile());
		FileInputStream fis = new FileInputStream(inFile);
		FileOutputStream fos = new FileOutputStream(outFile);
		try {
			byte[] buffer = new byte[1024];
			int i = 0;
			while ((i = fis.read(buffer)) != -1) {
				fos.write(buffer, 0, i);
			}
		} finally {
			fis.close();
			fos.close();
		}
	}

	public static void copyDir(File sourceDir, File destDir) throws IOException {
		createDir(destDir);
		File[] files = sourceDir.listFiles();
		if (files == null) {
			return;
		}
		for (File file : files) {
			String dest = destDir.getCanonicalPath() + "/" + file.getName();
			File destFile = new File(dest);
			if (file.isFile()) {
				copyFile(file, destFile);
			} else if (file.isDirectory()) {
				copyDir(file, destFile);
			}
		}
	}

	public static void deleteDir(File dir) {
		if (dir.isDirectory()) {
			File[] files = dir.listFiles();
			for (File file : files) {
				if (file.isFile()) {
					file.delete();
				} else if (file.isDirectory()) {
					deleteDir(file);
				}
			}
			dir.delete();
		} else {
			dir.delete();
		}
	}

	public static boolean deleteFile(String path) {
		return new File(path).delete();
	}

	public static long getFileLength(String path) {
		return new File(path).length();
	}
	
    public static int readFileInt(String path){
        try {
            DataInputStream dis=new DataInputStream(new FileInputStream(path));
            try{
                return dis.readInt();
            }finally{
                dis.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

	public static long getLastModified(String path) {
		return new File(path).lastModified();
	}

	public static boolean rename(String src, String dest) {
		File srcFile = new File(src);
		File destFile = new File(dest);
		File backupFile = new File(dest + ".backup");
		if (srcFile.isFile()) {
			if (destFile.isFile()) {
				destFile.renameTo(backupFile);
			}
			boolean result = srcFile.renameTo(destFile);
			if (result) {
				backupFile.delete();
			} else {
				backupFile.renameTo(destFile);
			}
			return result;
		} else {
			return false;
		}
	}


	public static void copyFileTo(InputStream is, String path)
			throws IOException {
		File desFile = new File(path);
		createDir(desFile.getParentFile());
		OutputStream os = new FileOutputStream(desFile);
		try {
			int realLength = 0;
			byte[] buffer = new byte[10240];
			while ((realLength = is.read(buffer)) != -1) {
				os.write(buffer, 0, realLength);
			}
			buffer = null;
		} finally {
			os.close();
			is.close();
		}
	}

	public static boolean isFileExist(String filePath) {
		File file = new File(filePath);
		try {
			return file.isFile();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean createDir(File dir) {
		File parentFile = dir.getParentFile();
		if(parentFile==null){
			return false;
		}
		if (parentFile.exists()) {
			try {
				if (!parentFile.canWrite()) {
					parentFile.setWritable(true, false);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			createDir(parentFile);
		}
		if(!dir.exists()){			
			return dir.mkdir();
		}else {
			return true;
		}
	}
	
	public static String getCurrentDir(){
		File file=new File(".");
		return file.getAbsolutePath();
	}
}
