/**
* <p>文件名: ScanUploadAction.java</p>
* <p>描述：压缩/解压缩Action</p>
* <p>版权: Copyright (c) 2010 Beijing Cosmosource Co. Ltd.</p>
* <p>公司: Cosmosource Beijing Office</p>
* <p>All right reserved.</p>
* @创建时间： 2010-12-06 上午10:27:11 
* @作者： WXJ
* @版本： V1.0
* 
*/
package com.cosmosource.base.util;
import java.io.File;   
import java.io.IOException;   
import java.io.InputStream;   
import java.io.OutputStream;   
import java.util.zip.ZipEntry;   
import java.util.zip.ZipInputStream;   
import java.util.zip.ZipOutputStream;   
  
import org.slf4j.Logger;   
import org.slf4j.LoggerFactory;   
import org.apache.commons.io.IOUtils;   
import org.apache.commons.io.FileUtils;   
/**
* @类描述:  压缩指定的目录以及解压指定的压缩文件(仅限ZIP格式). 
* @作者： WXJ
*/
public class ZipUtil {   
  
    /**  
     * 将指定目录下的所有文件压缩并生成指定路径的压缩文件.  
     * 如果压缩文件的路径或父路径不存在, 将会自动创建.  
     *  
     * @param src 将要进行压缩的目录  
     * @param zip 最终生成的压缩文件的路径  
     */   
    public static void zip(File src, File zip) throws IOException {   
        zip(src, FileUtils.openOutputStream(zip));   
    }   
  
    /**  
     * 将指定目录下的所有文件压缩并将流写入指定的输出流中.  
     *  
     * @param src 将要进行压缩的目录  
     * @param out 用于接收压缩产生的文件流的输出流  
     */   
    public static void zip(File src, OutputStream out) throws IOException {   
        zip(src, new ZipOutputStream(out));   
    }   
  
    /**  
     * 将指定目录下的所有文件压缩并将流写入指定的ZIP输出流中.  
     *  
     * @param src 将要进行压缩的目录  
     * @param zout 用于接收压缩产生的文件流的ZIP输出流  
     */   
    public static void zip(File src, ZipOutputStream zout) throws IOException {   
        try {   
            doZip(src, zout, "");   
        } finally {   
            IOUtils.closeQuietly(zout);   
        }   
    }   
  
    /**  
     * 将指定的压缩文件解压到指定的目标目录下.  
     * 如果指定的目标目录不存在或其父路径不存在, 将会自动创建.  
     *  
     * @param zip 将会解压的压缩文件  
     * @param dest 解压操作的目录目录  
     */   
    public static void unzip(File zip, File dest) throws IOException {   
        unzip(FileUtils.openInputStream(zip), dest);   
    }   
  
    /**  
     * 将指定的输入流解压到指定的目标目录下.  
     *  
     * @param in 将要解压的输入流  
     * @param dest 解压操作的目标目录  
     */   
    public static void unzip(InputStream in, File dest) throws IOException {   
        unzip(new ZipInputStream(in), dest);   
    }   
  
    /**  
     * 将指定的ZIP输入流解压到指定的目标目录下.  
     *  
     * @param zin 将要解压的ZIP输入流  
     * @param dest 解压操作的目标目录  
     */   
    public static void unzip(ZipInputStream zin, File dest) throws IOException {   
        try {   
            doUnzip(zin, dest);   
        } finally {   
            IOUtils.closeQuietly(zin);   
        }   
    }   
  
    /**  
     * @param src  
     * @param zout  
     * @param ns  
     */   
    private static void doZip(File src, ZipOutputStream zout, String ns)   
            throws IOException {   
    	if(src.isFile()){
    		  String entryName = ns + src.getName();   
              zout.putNextEntry(new ZipEntry(entryName));   
              fillZip(FileUtils.openInputStream(src), zout);   
    	}else{
            for (File file : src.listFiles()) {   
                String entryName = ns + file.getName();   
      
                if (file.isDirectory()) {   
                    logger.debug("adding: {}", entryName += "/");   
                    zout.putNextEntry(new ZipEntry(entryName));   
                    doZip(file, zout, entryName);   
                } else {   
                    logger.debug("adding: {}", entryName);   
                    zout.putNextEntry(new ZipEntry(entryName));   
                    fillZip(FileUtils.openInputStream(file), zout);   
                }   
            }   
    	}
    }   
  
    /**  
     * @param zin  
     * @param dest  
     */   
    private static void doUnzip(ZipInputStream zin, File dest)   
            throws IOException {   
        for (ZipEntry e; (e = zin.getNextEntry()) != null; zin.closeEntry()) {   
            File file = new File(dest, e.getName());   
  
            if (e.isDirectory()) {   
                logger.debug(" creating: {}", file + File.separator);   
                FileUtils.forceMkdir(file);   
            } else {   
                logger.debug("inflating: {}", file);   
                flushZip(zin, FileUtils.openOutputStream(file));   
            }   
        }   
    }   
  
    /**  
     * @param in  
     * @param zout  
     */   
    private static void fillZip(InputStream in, ZipOutputStream zout)   
            throws IOException {   
        try {   
            IOUtils.copy(in, zout);   
        } finally {   
            IOUtils.closeQuietly(in);   
        }   
    }   
  
    /**  
     * @param zin  
     * @param out  
     */   
    private static void flushZip(ZipInputStream zin, OutputStream out)   
            throws IOException {   
        try {   
            IOUtils.copy(zin, out);   
        } finally {   
            IOUtils.closeQuietly(out);   
        }   
    }   
    private static Logger logger = LoggerFactory.getLogger(ZipUtil.class);   
}   