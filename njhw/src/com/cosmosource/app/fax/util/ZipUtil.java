package com.cosmosource.app.fax.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

/**
 * @file_name ZipAndUnzip
 * @description 使用ant.jar实现压缩解压缩
 * @author
 * @date
 */
public class ZipUtil {
	/**
	 * @param zipFileName
	 *            指定压缩文件
	 * @param destDir
	 *            指定解压目录
	 * @throws Exception
	 */
	public static void unzip(String zipFileName, String destDir)
			throws Exception {
		try {
			ZipFile zipFile = new ZipFile(zipFileName);
			Enumeration<?> e = zipFile.getEntries();
			ZipEntry zipEntry = null;
			File fD = new File(destDir);
			if (!fD.exists()) {
				fD.mkdir();
			}
			while (e.hasMoreElements()) {
				zipEntry = (ZipEntry) e.nextElement();
				String entryName = zipEntry.getName();
				String names[] = entryName.split("/");
				int length = names.length;
				String path = destDir;
				for (int v = 0; v < length; v++) {
					if (v < length - 1) {
						path += "/" + names[v];
						new File(path).mkdir();
					} else {
						if (entryName.endsWith("/")) {
							new File(destDir + "/" + entryName).mkdir();
						} else {
							InputStream in = zipFile.getInputStream(zipEntry);
							OutputStream os = new FileOutputStream(new File(
									destDir + "/" + entryName));
							byte[] buf = new byte[1024];
							int len;
							while ((len = in.read(buf)) > 0) {
								os.write(buf, 0, len);
							}
							in.close();
							os.close();
						}
					}
				}
			}
			zipFile.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * @param targetZip
	 *            目标ZIP
	 * @param sourceFile
	 *            源文件
	 */
	public static void zip(String targetZip, String sourceFile) {
		Project prj = new Project();
		Zip zip = new Zip();
		zip.setProject(prj);
		zip.setDestFile(new File(targetZip));
		FileSet fileSet = new FileSet();
		fileSet.setProject(prj);
		fileSet.setDir(new File(sourceFile));
		zip.addFileset(fileSet);
		zip.execute();
	}

	public static void main(String[] args) {

		// ============
		try {
			//zip("d:/tempsrc.zip", "d:/ziptest");
			//unzip("d:/tempsrc.zip", "d:/resultZip");
			unzip("F:/Program Files/apache-tomcat-7.0.41/webapps/njhw/faxAttach/414d52adc375e4ff41ae2059a1fa4320/attach.zip","E://");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}