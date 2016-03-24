package com.cosmosource.base.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * @类描述:  文件处理工具类.
 * 
 * @作者： WXJ
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class FileUtil {
	private static Log log = LogFactory.getLog(FileUtil.class);

	/**
	 * 创建单个文件夹。
	 * 
	 * @param dir
	 * @param ignoreIfExitst
	 *            true 表示如果文件夹存在就不再创建了。false是重新创建。
	 * @throws IOException
	 */
	public static void createDir(String dir, boolean ignoreIfExitst)
			throws IOException {
		File file = new File(dir);
		if (ignoreIfExitst && file.exists()) {
			return;
		}
		if (file.mkdir() == false) {
			throw new IOException("Cannot create the directory = " + dir);
		}
	}

	/**
	 * 创建多个文件夹
	 * 
	 * @param dir
	 * @param ignoreIfExitst
	 * @throws IOException
	 */
	public static void createDirs(String dir, boolean ignoreIfExitst)
			throws IOException {
		File file = new File(dir);
		if (ignoreIfExitst && file.exists()) {
			return;
		}
		if (file.mkdirs() == false) {
			throw new IOException("Cannot create directories = " + dir);
		}
	}

	/**
	 * 删除一个文件。
	 * 
	 * @param filename
	 * @throws IOException
	 */
	public static void deleteFile(String filename) throws IOException {
		File file = new File(filename);
		log.trace("Delete file = " + filename);
		if (file.isDirectory()) {
			throw new IOException(
					"IOException -> BadInputException: not a file.");
		}
		if (file.exists() == false) {
			throw new IOException(
					"IOException -> BadInputException: file is not exist.");
		}
		if (file.delete() == false) {
			throw new IOException("Cannot delete file. filename = " + filename);
		}
	}

	/**
	 * 删除文件夹及其下面的子文件夹
	 * 
	 * @param dir
	 * @throws IOException
	 */
	public static void deleteDir(File dir) throws IOException {
		if (dir.isFile())
			throw new IOException(
					"IOException -> BadInputException: not a directory.");
		File[] files = dir.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				File file = files[i];
				if (file.isFile()) {
					file.delete();
				} else {
					deleteDir(file);
				}
			}
		}// if
		dir.delete();
	}

	public static String getPathSeparator() {
		return java.io.File.pathSeparator;
	}

	public static String getFileSeparator() {
		return java.io.File.separator;
	}

	/**
	 * 获取到目录下面文件的大小。包含了子目录。
	 * 
	 * @param dir
	 * @return
	 * @throws IOException
	 */
	public static long getDirLength(File dir) throws IOException {
		if (dir.isFile())
			throw new IOException("BadInputException: not a directory.");
		long size = 0;
		File[] files = dir.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				File file = files[i];
				// file.getName();
				// System.out.println(file.getName());
				long length = 0;
				if (file.isFile()) {
					length = file.length();
				} else {
					length = getDirLength(file);
				}
				size += length;
			}// for
		}// if
		return size;
	}

	/**
	 * 将文件清空。
	 * 
	 * @param srcFilename
	 * @throws IOException
	 */
	public static void emptyFile(String srcFilename) throws IOException {
		File srcFile = new File(srcFilename);
		if (!srcFile.exists()) {
			throw new FileNotFoundException("Cannot find the file: "
					+ srcFile.getAbsolutePath());
		}
		if (!srcFile.canWrite()) {
			throw new IOException("Cannot write the file: "
					+ srcFile.getAbsolutePath());
		}
		FileOutputStream outputStream = new FileOutputStream(srcFilename);
		outputStream.close();
	}

	/**
	 * Write content to a fileName with the destEncoding 写文件。如果此文件不存在就创建一个。
	 * 
	 * @param content
	 *            String
	 * @param fileName
	 *            String
	 * @param destEncoding
	 *            String
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void writeFile(String content, String fileName,
			String destEncoding) throws FileNotFoundException, IOException {
		File file = null;
		try {
			file = new File(fileName);
			if (!file.exists()) {
				if (file.createNewFile() == false) {
					throw new IOException("create file '" + fileName
							+ "' failure.");
				}
			}
			if (file.isFile() == false) {
				throw new IOException("'" + fileName + "' is not a file.");
			}
			if (file.canWrite() == false) {
				throw new IOException("'" + fileName + "' is a read-only file.");
			}
		} finally {
			// we dont have to close File here
		}
		BufferedWriter out = null;
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			out = new BufferedWriter(new OutputStreamWriter(fos, destEncoding));
			out.write(content);
			out.flush();
		} catch (FileNotFoundException fe) {
			log.error("Error", fe);
			throw fe;
		} catch (IOException e) {
			log.error("Error", e);
			throw e;
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException ex) {
			}
		}
	}

	/**
	 * 读取文件的内容，并将文件内容以字符串的形式返回。
	 * 
	 * @param fileName
	 * @param srcEncoding
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String readFile(String fileName, String srcEncoding)
			throws FileNotFoundException, IOException {
		File file = null;
		try {
			file = new File(fileName);
			if (file.isFile() == false) {
				throw new IOException("'" + fileName + "' is not a file.");
			}
		} finally {
			// we dont have to close File here
		}
		BufferedReader reader = null;
		try {
			StringBuilder result = new StringBuilder(1024);
			FileInputStream fis = new FileInputStream(fileName);
			reader = new BufferedReader(new InputStreamReader(fis, srcEncoding));
			char[] block = new char[512];
			while (true) {
				int readLength = reader.read(block);
				if (readLength == -1)
					break;// end of file
				result.append(block, 0, readLength);
			}
			return result.toString();
		} catch (FileNotFoundException fe) {
			log.error("Error", fe);
			throw fe;
		} catch (IOException e) {
			log.error("Error", e);
			throw e;
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException ex) {
			}
		}
	}

	/*
	 * 1 ABC 2 abC Gia su doc tu dong 1 lay ca thay 5 dong => 1 --> 5 3 ABC
	 */
	public static String[] getLastLines(File file, int linesToReturn)
			throws IOException, FileNotFoundException {
		final int AVERAGE_CHARS_PER_LINE = 250;
		final int BYTES_PER_CHAR = 2;
		RandomAccessFile randomAccessFile = null;
		StringBuilder buffer = new StringBuilder(linesToReturn
				* AVERAGE_CHARS_PER_LINE);
		int lineTotal = 0;
		try {
			randomAccessFile = new RandomAccessFile(file, "r");
			long byteTotal = randomAccessFile.length();
			long byteEstimateToRead = linesToReturn * AVERAGE_CHARS_PER_LINE
					* BYTES_PER_CHAR;
			long offset = byteTotal - byteEstimateToRead;
			if (offset < 0) {
				offset = 0;
			}
			randomAccessFile.seek(offset);
			// log.debug("SKIP IS ::" + offset);
			String line = null;
			String lineUTF8 = null;
			while ((line = randomAccessFile.readLine()) != null) {
				lineUTF8 = new String(line.getBytes("ISO8859_1"), "UTF-8");
				lineTotal++;
				buffer.append(lineUTF8).append("\n");
			}
		} finally {
			if (randomAccessFile != null) {
				try {
					randomAccessFile.close();
				} catch (IOException ex) {
				}
			}
		}
		String[] resultLines = new String[linesToReturn];
		BufferedReader in = null;
		try {
			in = new BufferedReader(new StringReader(buffer.toString()));
			int start = lineTotal /* + 2 */- linesToReturn; // Ex : 55 - 10 = 45
			// ~ offset
			if (start < 0)
				start = 0; // not start line
			for (int i = 0; i < start; i++) {
				in.readLine(); // loop until the offset. Ex: loop 0, 1 ~~ 2
				// lines
			}
			int i = 0;
			String line = null;
			while ((line = in.readLine()) != null) {
				resultLines[i] = line;
				i++;
			}
		} catch (IOException ie) {
			log.error("Error" + ie);
			throw ie;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException ex) {
				}
			}
		}
		return resultLines;
	}
	/**
	 * 单个文件剪切
	 * @param srcFilename
	 * @param destFilename
	 * @param overwrite
	 * @throws IOException
	 */
	public static void cutFile(String srcFilename, String destFilename,
			boolean overwrite) throws IOException{
		//拷贝到备份文件夹后删除
		copyFile(srcFilename, destFilename, true);
		FileUtil.deleteFile(srcFilename);
	}
	/**
	 * 单个文件拷贝。
	 * 
	 * @param srcFilename
	 * @param destFilename
	 * @param overwrite
	 * @throws IOException
	 */
	public static void copyFile(String srcFilename, String destFilename,
			boolean overwrite) throws IOException {
		File srcFile = new File(srcFilename);
		// 首先判断源文件是否存在
		if (!srcFile.exists()) {
			throw new FileNotFoundException("Cannot find the source file: "
					+ srcFile.getAbsolutePath());
		}
		// 判断源文件是否可读
		if (!srcFile.canRead()) {
			throw new IOException("Cannot read the source file: "
					+ srcFile.getAbsolutePath());
		}
		File destFile = new File(destFilename);
		if (overwrite == false) {
			// 目标文件存在就不覆盖
			if (destFile.exists())
				return;
		} else {
			// 如果要覆盖已经存在的目标文件，首先判断是否目标文件可写。
			if (destFile.exists()) {
				if (!destFile.canWrite()) {
					throw new IOException("Cannot write the destination file: "
							+ destFile.getAbsolutePath());
				}
			} else {
				// 不存在就创建一个新的空文件。
				if (!destFile.createNewFile()) {
					throw new IOException("Cannot write the destination file: "
							+ destFile.getAbsolutePath());
				}
			}
		}
		BufferedInputStream inputStream = null;
		BufferedOutputStream outputStream = null;
		byte[] block = new byte[1024];
		try {
			inputStream = new BufferedInputStream(new FileInputStream(srcFile));
			outputStream = new BufferedOutputStream(new FileOutputStream(
					destFile));
			while (true) {
				int readLength = inputStream.read(block);
				if (readLength == -1)
					break;// end of file
				outputStream.write(block, 0, readLength);
			}
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException ex) {
					// just ignore
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException ex) {
					// just ignore
				}
			}
		}
	}

	/**
	 * 单个文件拷贝。
	 * 
	 * @param srcFile
	 * @param destFile
	 * @param overwrite
	 *            是否覆盖目的文件
	 * @throws IOException
	 */
	public static void copyFile(File srcFile, File destFile, boolean overwrite)
			throws IOException {
		// 首先判断源文件是否存在
		if (!srcFile.exists()) {
			throw new FileNotFoundException("Cannot find the source file: "
					+ srcFile.getAbsolutePath());
		}
		// 判断源文件是否可读
		if (!srcFile.canRead()) {
			throw new IOException("Cannot read the source file: "
					+ srcFile.getAbsolutePath());
		}
		if (overwrite == false) {
			// 目标文件存在就不覆盖
			if (destFile.exists())
				return;
		} else {
			// 如果要覆盖已经存在的目标文件，首先判断是否目标文件可写。
			if (destFile.exists()) {
				if (!destFile.canWrite()) {
					throw new IOException("Cannot write the destination file: "
							+ destFile.getAbsolutePath());
				}
			} else {
				// 不存在就创建一个新的空文件。
				if (!destFile.createNewFile()) {
					throw new IOException("Cannot write the destination file: "
							+ destFile.getAbsolutePath());
				}
			}
		}
		BufferedInputStream inputStream = null;
		BufferedOutputStream outputStream = null;
		byte[] block = new byte[1024];
		try {
			inputStream = new BufferedInputStream(new FileInputStream(srcFile));
			outputStream = new BufferedOutputStream(new FileOutputStream(
					destFile));
			while (true) {
				int readLength = inputStream.read(block);
				if (readLength == -1)
					break;// end of file
				outputStream.write(block, 0, readLength);
			}
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException ex) {
					// just ignore
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException ex) {
					// just ignore
				}
			}
		}
	}

	/**
	 * 拷贝文件，从源文件夹拷贝文件到目的文件夹。 <br>
	 * 参数源文件夹和目的文件夹，最后都不要带文件路径符号，例如：c:/aa正确，c:/aa/错误。
	 * 
	 * @param srcDirName
	 *            源文件夹名称 ,例如：c:/test/aa 或者c:\\test\\aa
	 * @param destDirName
	 *            目的文件夹名称,例如：c:/test/aa 或者c:\\test\\aa
	 * @param overwrite
	 *            是否覆盖目的文件夹下面的文件。
	 * @throws IOException
	 */
	public static void copyFiles(String srcDirName, String destDirName,
			boolean overwrite) throws IOException {
		File srcDir = new File(srcDirName);// 声明源文件夹
		// 首先判断源文件夹是否存在
		if (!srcDir.exists()) {
			throw new FileNotFoundException(
					"Cannot find the source directory: "
							+ srcDir.getAbsolutePath());
		}
		File destDir = new File(destDirName);
		if (overwrite == false) {
			if (destDir.exists()) {
				// do nothing
			} else {
				if (destDir.mkdirs() == false) {
					throw new IOException(
							"Cannot create the destination directories = "
									+ destDir);
				}
			}
		} else {
			// 覆盖存在的目的文件夹
			if (destDir.exists()) {
				// do nothing
			} else {
				// create a new directory
				if (destDir.mkdirs() == false) {
					throw new IOException(
							"Cannot create the destination directories = "
									+ destDir);
				}
			}
		}
		// 循环查找源文件夹目录下面的文件（屏蔽子文件夹），然后将其拷贝到指定的目的文件夹下面。
		File[] srcFiles = srcDir.listFiles();
		if (srcFiles == null || srcFiles.length < 1) {
			// throw new IOException ("Cannot find any file from source
			// directory!!!");
			return;// do nothing
		}
		// 开始复制文件
		int SRCLEN = srcFiles.length;
		for (int i = 0; i < SRCLEN; i++) {
			// File tempSrcFile = srcFiles[i];
			File destFile = new File(destDirName + File.separator
					+ srcFiles[i].getName());
			// 注意构造文件对象时候，文件名字符串中不能包含文件路径分隔符";".
			// log.debug(destFile);
			if (srcFiles[i].isFile()) {
				copyFile(srcFiles[i], destFile, overwrite);
			} else {
				// 在这里进行递归调用，就可以实现子文件夹的拷贝
				copyFiles(srcFiles[i].getAbsolutePath(), destDirName
						+ File.separator + srcFiles[i].getName(), overwrite);
			}
		}
	}

//	/**
//	 * 压缩文件。注意：中文文件名称和中文的评论会乱码。
//	 * 
//	 * @param srcFilename
//	 * @param destFilename
//	 * @param overwrite
//	 * @throws IOException
//	 */
//	public static void zipFile(String srcFilename, String destFilename,
//			boolean overwrite) throws IOException {
//
//		File srcFile = new File(srcFilename);
//		// 首先判断源文件是否存在
//		if (!srcFile.exists()) {
//			throw new FileNotFoundException("Cannot find the source file: "
//					+ srcFile.getAbsolutePath());
//		}
//		// 判断源文件是否可读
//		if (!srcFile.canRead()) {
//			throw new IOException("Cannot read the source file: "
//					+ srcFile.getAbsolutePath());
//		}
//		if (destFilename == null || destFilename.trim().equals("")) {
//			destFilename = srcFilename + ".zip";
//		} else {
//			destFilename += ".zip";
//		}
//		File destFile = new File(destFilename);
//		if (overwrite == false) {
//			// 目标文件存在就不覆盖
//			if (destFile.exists())
//				return;
//		} else {
//			// 如果要覆盖已经存在的目标文件，首先判断是否目标文件可写。
//			if (destFile.exists()) {
//				if (!destFile.canWrite()) {
//					throw new IOException("Cannot write the destination file: "
//							+ destFile.getAbsolutePath());
//				}
//			} else {
//				// 不存在就创建一个新的空文件。
//				if (!destFile.createNewFile()) {
//					throw new IOException("Cannot write the destination file: "
//							+ destFile.getAbsolutePath());
//				}
//			}
//		}
//		BufferedInputStream inputStream = null;
//		BufferedOutputStream outputStream = null;
//		ZipOutputStream zipOutputStream = null;
//		byte[] block = new byte[1024];
//		try {
//			inputStream = new BufferedInputStream(new FileInputStream(srcFile));
//			outputStream = new BufferedOutputStream(new FileOutputStream(
//					destFile));
//			zipOutputStream = new ZipOutputStream(outputStream);
//
//			zipOutputStream.setComment("通过java程序压缩的");
//			ZipEntry zipEntry = new ZipEntry(srcFile.getName());
//			zipEntry.setComment(" zipEntry通过java程序压缩的");
//			zipOutputStream.putNextEntry(zipEntry);
//			while (true) {
//				int readLength = inputStream.read(block);
//				if (readLength == -1)
//					break;// end of file
//				zipOutputStream.write(block, 0, readLength);
//			}
//			zipOutputStream.flush();
//			zipOutputStream.finish();
//		} finally {
//			if (inputStream != null) {
//				try {
//					inputStream.close();
//				} catch (IOException ex) {
//					// just ignore
//				}
//			}
//			if (outputStream != null) {
//				try {
//					outputStream.close();
//				} catch (IOException ex) {
//					// just ignore
//				}
//			}
//			if (zipOutputStream != null) {
//				try {
//					zipOutputStream.close();
//				} catch (IOException ex) {
//					// just ignore
//				}
//			}
//		}
//	}
	
	public static File saveFile(InputStream is, String filePath, String fileName) {
		OutputStream bos = null;
		try {
			bos = new FileOutputStream(filePath + "/" + fileName);
			IOUtils.copy(is, bos);
		} catch (IOException ioe) {
			log.error("保存文件失败");
		} finally {
			IOUtils.closeQuietly(bos);
			IOUtils.closeQuietly(is);
		}
		return new File(filePath + "/" + fileName);
	}
	public static boolean existsFile(String fileName) {
		File file = new File(fileName);
		return file.exists();
	}
	/**
	 * 得到文件夹里面的所有文件名，不包含目录
	 * 
	 * @param path
	 */
	public static List getAllFileNames(String path) {
		List list = new ArrayList();
		File file = new File(path);
		if (!file.exists()) {
			return null;
		}
		if (!file.isDirectory()) {
			return null;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				list.add(tempList[i]);
			}

		}
		log.debug("取得文件操作成功执行");
		return list;
	}

	/**
	* @描述: 取文件的行数
	* @作者：WXJ
	* @日期：2011-8-2 下午01:13:45
	* @param filename
	* @return
	* @return int
	*/
	public static long getFileLineNumber(String filename) {
		long lines = 0;
		File test = new File(filename);
		long fileLength = test.length();

		LineNumberReader rf = null;
		try {
			rf = new LineNumberReader(new FileReader(test));
			if (rf != null) {
				rf.skip(fileLength);
				lines = rf.getLineNumber();
				rf.close();
			}
		} catch (IOException e) {
			if (rf != null) {
				try {
					rf.close();
				} catch (IOException ee) {
				}
			}
		}finally {
			IOUtils.closeQuietly(rf);
		}
		return lines;
	}
	/**
	* @描述: 新建文件 
	* @作者：rhj
	* @日期：2011-10-11 上午11:08:56
	* @param filePath
	* @param isOverrideOld
	* @return
	* @throws IOException
	* @return File
	*/
	public static File createNewFile(String filePath, boolean isOverrideOld)
			throws IOException {
		File file = new File(filePath);
		File parent = file.getParentFile();
		if (!parent.exists()) {
			parent.mkdirs();
		}
		if (isOverrideOld && file.exists()) {
			file.delete();
		}
		if (!file.exists()) {
			file.createNewFile();
		}
		return file;
	}
	/**
	* @描述: 获取文件大小
	* @作者：WXJ
	* @日期：2012-3-27 上午11:21:34
	* @param f
	* @return
	* @throws Exception
	* @return long
	*/
	public static long getFileSizes(File f) throws Exception{//取得文件大小 
		long s=0; 
		if (f.exists()) { 
			FileInputStream fis = null; 
			fis = new FileInputStream(f); 
			s= fis.available(); 
		} else { 
			log.debug("文件不存在");
		} 
		return s; 
	}
	/**
	* @描述: 格式化文件大小
	* @作者：WXJ
	* @日期：2012-3-27 上午11:22:04
	* @param fileS
	* @return
	* @return String
	*/
	public static String formetFileSize(long fileS) {//转换文件大小 
		DecimalFormat df = new DecimalFormat("#.00"); 
		String fileSizeString = ""; 
		if (fileS < 1024) { 
			fileSizeString = df.format((double) fileS) + "B"; 
		} else if (fileS < 1048576) { 
			fileSizeString = df.format((double) fileS / 1024) + "K"; 
		} else if (fileS < 1073741824) { 
			fileSizeString = df.format((double) fileS / 1048576) + "M"; 
		} else { 
			fileSizeString = df.format((double) fileS / 1073741824) + "G"; 
		} 
		return fileSizeString; 
	} 

	/**
	* @描述: 获取文件个数，包括子目录文件
	* @作者：WXJ
	* @日期：2012-3-27 上午11:22:39
	* @param f
	* @return
	* @return long
	*/
	public static long getlist(File f){//递归求取目录文件个数 
		long size = 0; 
		File flist[] = f.listFiles(); 
		size=flist.length; 
		for (int i = 0; i < flist.length; i++) { 
			if (flist[i].isDirectory()) { 
				size = size + getlist(flist[i]); 
				size--; 
			} 
		}
		return size; 
	}
	
	/**
	* @描述: 直接获取格式化后的文件大小
	* @作者：WXJ
	* @日期：2012-3-27 上午11:26:50
	* @param f FIle
	* @return
	* @throws Exception
	* @return String
	*/
	public static String getFormetFileSizes(File f) throws Exception {
		String formetFileSize = formetFileSize(getFileSizes(f));
		return formetFileSize;
	}
	
	/**
	* @描述: 直接获取格式化后的文件大小
	* @作者：WXJ
	* @日期：2012-3-27 上午11:42:01
	* @param f String
	* @return
	* @throws Exception
	* @return String
	*/
	public static String getFormetFileSizes(String f) throws Exception {
		String formetFileSize = formetFileSize(getFileSizes(new File(f)));
		return formetFileSize;
	}
}