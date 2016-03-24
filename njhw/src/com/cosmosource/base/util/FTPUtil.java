/**
* <p>文件名: FTPUtil.java</p>
* <p>描述：FTP工具类</p>
* <p>版权: Copyright (c) 2010 Beijing Cosmosource Co. Ltd.</p>
* <p>公司: Cosmosource Beijing Office</p>
* <p>All right reserved.</p>
* @创建时间： 2011-07-15 上午10:27:11 
* @作者： WXJ
* @版本： V1.0
* <p>类修改者		 修改日期			修改说明   </p>   
* 
*/
package com.cosmosource.base.util;
import java.io.File;   
import java.io.FileOutputStream;   
import java.io.IOException;   
import java.io.InputStream;   
import java.io.OutputStream;   
import java.io.PrintWriter;   
import java.io.RandomAccessFile;   
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
  
import org.apache.commons.net.PrintCommandListener;   
import org.apache.commons.net.ftp.FTP;   
import org.apache.commons.net.ftp.FTPClient;   
import org.apache.commons.net.ftp.FTPFile;    
import org.apache.commons.net.ftp.FTPReply;   

  
/**  
 * @类描述: FTP工具类
 * 1.支持上传下载。支持断点续传
 * 2.支持进度汇报
 * 3.支持对于中文目录及中文文件创建的支持。
 * @作者： WXJ
 */  
public class FTPUtil {   
    public FTPClient ftpClient = new FTPClient();   
    private final long TIMEOUT = 5000;
       
    public FTPUtil(){  
    	
        //设置将过程中使用到的命令输出到控制台   
        this.ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));   
    }   
       
    /**  
     * 连接到FTP服务器  
     * @param hostname 主机名  
     * @param port 端口  
     * @param username 用户名  
     * @param password 密码  
     * @return 是否连接成功  
     * @throws IOException  
     */  
    public boolean connect(final String hostname,final int port,final String username,final String password) throws IOException{   
    	ExecutorService executor = Executors.newSingleThreadExecutor();
    	FutureTask<Boolean> futureTask =
			new FutureTask<Boolean>(new Callable<Boolean>() {
		         public Boolean call() {
		        	 try {
		        	    	ftpClient.connect(hostname, port);   
		        	        ftpClient.setControlEncoding("GBK");
		        	        
		        	        if(FTPReply.isPositiveCompletion(ftpClient.getReplyCode())){   
		        	            if(ftpClient.login(username, password)){ 
		        	                return true;   
		        	            }   
		        	        }   
		        	        
		        	        disconnect();   
		        	        return false; 
					} catch (IOException e) {
						return false;
					}
		       }});
			executor.execute(futureTask);
			
		try {
			// 取得结果，同时设置超时执行时间为5秒
			boolean r = futureTask.get(TIMEOUT, TimeUnit.MILLISECONDS);
			if (r) {
				return true;
			}
			
		} catch (Exception e) {
			futureTask.cancel(true);
			throw new IOException();
		}  finally {
			executor.shutdown();
		}
		throw new IOException();
    }   
       
    /**  
     * 从FTP服务器上下载文件,支持断点续传，上传百分比汇报  
     * @param remote 远程文件路径  
     * @param local 本地文件路径  
     * @return 上传的状态  
     * @throws IOException  
     */  
    public DownloadStatus download(final String remote,final String local) throws IOException{   
    	ExecutorService executor = Executors.newSingleThreadExecutor();
    	FutureTask<DownloadStatus> futureTask =
			new FutureTask<DownloadStatus>(new Callable<DownloadStatus>() {
		         public DownloadStatus call() {
		        	 try {
		        		//设置被动模式   
		        	        ftpClient.enterLocalPassiveMode();   
		        	        //设置以二进制方式传输   
		        	        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);   
		        	        DownloadStatus result;   
		        	           
		        	        //检查远程文件是否存在   
		        	        FTPFile[] files = ftpClient.listFiles(new String(remote.getBytes("GBK"),"iso-8859-1"));   
		        	        if(files.length != 1){   
		        	            System.out.println("远程文件不存在");   
		        	            return DownloadStatus.Remote_File_Noexist;   
		        	        }   
		        	           
		        	        long lRemoteSize = files[0].getSize();   
		        	        File f = new File(local);   
		        	        //本地存在文件，进行断点下载   
		        	        if(f.exists()){   
		        	            long localSize = f.length();   
		        	            //判断本地文件大小是否大于远程文件大小   
		        	            if(localSize >= lRemoteSize){   
		        	                System.out.println("本地文件大于远程文件，下载中止");   
		        	                return DownloadStatus.Local_Bigger_Remote;   
		        	            }   
		        	               
		        	            //进行断点续传，并记录状态   
		        	            FileOutputStream out = new FileOutputStream(f,true);   
		        	            ftpClient.setRestartOffset(localSize);   
		        	            InputStream in = ftpClient.retrieveFileStream(new String(remote.getBytes("GBK"),"iso-8859-1"));   
		        	            byte[] bytes = new byte[1024];   
		        	            long step = (lRemoteSize<100?100:lRemoteSize)/100;   
		        	            long process=localSize /step;   
		        	            int c;   
		        	            while((c = in.read(bytes))!= -1){   
		        	                out.write(bytes,0,c);   
		        	                localSize+=c;   
		        	                long nowProcess = localSize /step;   
		        	                if(nowProcess > process){   
		        	                    process = nowProcess;   
		        	                    if(process % 10 == 0) {
//		        	                    	System.out.println("下载进度："+process);
		        	                    }
		        	                           
		        	                    //TODO 更新文件下载进度,值存放在process变量中   
		        	                }   
		        	            }   
		        	            in.close();   
		        	            out.close();   
		        	            boolean isDo = ftpClient.completePendingCommand();   
		        	            if(isDo){   
		        	                result = DownloadStatus.Download_From_Break_Success;   
		        	            }else {   
		        	                result = DownloadStatus.Download_From_Break_Failed;   
		        	            }   
		        	        }else {   
		        	            OutputStream out = new FileOutputStream(f);   
		        	            InputStream in= ftpClient.retrieveFileStream(new String(remote.getBytes("GBK"),"iso-8859-1"));   
		        	            byte[] bytes = new byte[1024];   
		        	            long step = (lRemoteSize<100?100:lRemoteSize)/100;   
		        	            long process=0;   
		        	            long localSize = 0L;   
		        	            int c;   
		        	            while((c = in.read(bytes))!= -1){   
		        	                out.write(bytes, 0, c);   
		        	                localSize+=c;   
		        	                long nowProcess = localSize /step;   
		        	                if(nowProcess > process){   
		        	                    process = nowProcess;   
		        	                    if(process % 10 == 0){
//		        	                    	System.out.println("下载进度："+process);   
		        	                    }
		        	                        
		        	                    //TODO 更新文件下载进度,值存放在process变量中   
		        	                }   
		        	            }   
		        	            in.close();   
		        	            out.close();   
		        	            boolean upNewStatus = ftpClient.completePendingCommand();   
		        	            if(upNewStatus){   
		        	                result = DownloadStatus.Download_New_Success;   
		        	            }else {   
		        	                result = DownloadStatus.Download_New_Failed;   
		        	            }   
		        	        }   
		        	        return result;   
					} catch (IOException e) {
						return null;
					}
		       }});
			executor.execute(futureTask);
			
		try {
			//取得结果，同时设置超时执行时间为5秒
			DownloadStatus r = futureTask.get(TIMEOUT, TimeUnit.MILLISECONDS);
			if (r!=null) {
				return r;
			}
			
		} catch (Exception e) {
			futureTask.cancel(true);
			throw new IOException();
		}  finally {
			executor.shutdown();
		}
		throw new IOException();
    }   
       
    /**  
     * 上传文件到FTP服务器，支持断点续传  
     * @param local 本地文件名称，绝对路径  
     * @param remote 远程文件路径，使用/home/directory1/subdirectory/file.ext或是 http://www.guihua.org /subdirectory/file.ext 按照Linux上的路径指定方式，支持多级目录嵌套，支持递归创建不存在的目录结构  
     * @return 上传结果  
     * @throws IOException  
     */  
    public UploadStatus upload(final String local,final String remote) throws IOException{
    	ExecutorService executor = Executors.newSingleThreadExecutor();
    	FutureTask<UploadStatus> futureTask =
			new FutureTask<UploadStatus>(new Callable<UploadStatus>() {
		         public UploadStatus call() {
		        	 try {
		        		//设置PassiveMode传输   
		        	        ftpClient.enterLocalPassiveMode();//
		        	        
		        	        //设置以二进制流的方式传输   
		        	        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);   
		        	        ftpClient.setControlEncoding("GBK");   
		        	        UploadStatus result;   
		        	        //对远程目录的处理   
		        	        String remoteFileName = remote;   
		        	        if(remote.contains("/")){   
		        	            remoteFileName = remote.substring(remote.lastIndexOf("/")+1);   
		        	            //创建服务器远程目录结构，创建失败直接返回   
		        	            if(CreateDirecroty(remote, ftpClient)==UploadStatus.Create_Directory_Fail){   
		        	                return UploadStatus.Create_Directory_Fail;   
		        	            }   
		        	        }   
		        	           
		        	        //检查远程是否存在文件   
		        	        FTPFile[] files = ftpClient.listFiles(new String(remoteFileName.getBytes("GBK"),"iso-8859-1"));   
		        	        if(files.length == 1){   
		        	            long remoteSize = files[0].getSize();   
		        	            File f = new File(local);   
		        	            long localSize = f.length();   
		        	            if(remoteSize==localSize){   
		        	                return UploadStatus.File_Exits;   
		        	            }else if(remoteSize > localSize){   
		        	                return UploadStatus.Remote_Bigger_Local;   
		        	            }   
		        	               
		        	            //尝试移动文件内读取指针,实现断点续传   
		        	            result = uploadFile(remoteFileName, f, ftpClient, remoteSize);   
		        	               
		        	            //如果断点续传没有成功，则删除服务器上文件，重新上传   
		        	            if(result == UploadStatus.Upload_From_Break_Failed){   
		        	                if(!ftpClient.deleteFile(remoteFileName)){   
		        	                    return UploadStatus.Delete_Remote_Faild;   
		        	                }   
		        	                result = uploadFile(remoteFileName, f, ftpClient, 0);   
		        	            }   
		        	        }else {   
		        	            result = uploadFile(remoteFileName, new File(local), ftpClient, 0);   
		        	        }   
		        	        return result;   
					} catch (IOException e) {
						return null;
					}
		       }});
			executor.execute(futureTask);
			
		try {
			// 取得结果，同时设置超时执行时间为5秒
			UploadStatus r = futureTask.get(TIMEOUT, TimeUnit.MILLISECONDS);
			if (r!=null) {
				return r;
			}
			
		} catch (Exception e) {
			futureTask.cancel(true);
			throw new IOException();
		}  finally {
			executor.shutdown();
		}
		throw new IOException();
        
    }   
    /**  
     * 断开与远程服务器的连接  
     * @throws IOException  
     */  
    public void disconnect() throws IOException{   
    	ExecutorService executor = Executors.newSingleThreadExecutor();
    	FutureTask<Boolean> futureTask =
			new FutureTask<Boolean>(new Callable<Boolean>() {
		         public Boolean call() {
		        	 try {
		        		 if(ftpClient.isConnected()){   
		        	            ftpClient.disconnect();   
		        	     } 
		        		 return true;
		        	 } catch (IOException e) {
						return false;
		        	 }
		         }});
			executor.execute(futureTask);
			
		try {
			// 取得结果，同时设置超时执行时间为5秒
			boolean r = futureTask.get(TIMEOUT, TimeUnit.MILLISECONDS);
			if (r) {
				return;
			}
			
		} catch (Exception e) {
			futureTask.cancel(true);
			//throw new IOException();//忽略断开连接失败异常
		}  finally {
			executor.shutdown();
		}
		//throw new IOException();//忽略断开连接失败异常
          
    }   
       
    /**  
     * 递归创建远程服务器目录  
     * @param remote 远程服务器文件绝对路径  
     * @param ftpClient FTPClient对象  
     * @return 目录创建是否成功  
     * @throws IOException  
     */  
    public UploadStatus CreateDirecroty(final String remote,final FTPClient ftpClient) throws IOException{
    	ExecutorService executor = Executors.newSingleThreadExecutor();
    	FutureTask<UploadStatus> futureTask =
			new FutureTask<UploadStatus>(new Callable<UploadStatus>() {
		         public UploadStatus call() {
		        	 try {
		        		 UploadStatus status = UploadStatus.Create_Directory_Success;   
		        	        String directory = remote.substring(0,remote.lastIndexOf("/")+1);   
		        	        if(!directory.equalsIgnoreCase("/")&&!ftpClient.changeWorkingDirectory(new String(directory.getBytes("GBK"),"iso-8859-1"))){   
		        	            //如果远程目录不存在，则递归创建远程服务器目录   
		        	            int start=0;   
		        	            int end = 0;   
		        	            if(directory.startsWith("/")){   
		        	                start = 1;   
		        	            }else{   
		        	                start = 0;   
		        	            }   
		        	            end = directory.indexOf("/",start);   
		        	            while(true){   
		        	                String subDirectory = new String(remote.substring(start,end).getBytes("GBK"),"iso-8859-1");   
		        	                if(!ftpClient.changeWorkingDirectory(subDirectory)){   
		        	                    if(ftpClient.makeDirectory(subDirectory)){   
		        	                        ftpClient.changeWorkingDirectory(subDirectory);   
		        	                    }else {   
		        	                        System.out.println("创建目录失败");   
		        	                        return UploadStatus.Create_Directory_Fail;   
		        	                    }   
		        	                }   
		        	                   
		        	                start = end + 1;   
		        	                end = directory.indexOf("/",start);   
		        	                   
		        	                //检查所有目录是否创建完毕   
		        	                if(end <= start){   
		        	                    break;   
		        	                }   
		        	            }   
		        	        }   
		        	        return status;   
					} catch (IOException e) {
						return null;
					}
		       }});
			executor.execute(futureTask);
			
		try {
			// 取得结果，同时设置超时执行时间为5秒
			UploadStatus r = futureTask.get(TIMEOUT, TimeUnit.MILLISECONDS);
			if (r!=null) {
				return r;
			}
			
		} catch (Exception e) {
			futureTask.cancel(true);
			throw new IOException();
		}  finally {
			executor.shutdown();
		}
		throw new IOException();
        
    }   
       
    /**  
     * 上传文件到服务器,新上传和断点续传  
     * @param remoteFile 远程文件名，在上传之前已经将服务器工作目录做了改变  
     * @param localFile 本地文件File句柄，绝对路径  
     * @param processStep 需要显示的处理进度步进值  
     * @param ftpClient FTPClient引用  
     * @return  
     * @throws IOException  
     */  
    public UploadStatus uploadFile(final String remoteFile,final File localFile,final FTPClient ftpClient,final long remoteSize) throws IOException{   
    	ExecutorService executor = Executors.newSingleThreadExecutor();
    	FutureTask<UploadStatus> futureTask =
			new FutureTask<UploadStatus>(new Callable<UploadStatus>() {
		         public UploadStatus call() {
		        	 try {
		        		UploadStatus status;   
	        	        //显示进度的上传   
	        	        long step = 0;   
	        	        if(localFile.length()<100){
	        	        	step = 100;
	        	        }else{
	        	        	step = localFile.length() / 100; 
	        	        }
	        	        long process = 0;   
	        	        long localreadbytes = 0L;   
	        	        RandomAccessFile raf = new RandomAccessFile(localFile,"r");   
	        	        OutputStream out = ftpClient.appendFileStream(new String(remoteFile.getBytes("GBK"),"iso-8859-1"));   
	        	        //断点续传   
	        	        if(remoteSize>0){   
	        	            ftpClient.setRestartOffset(remoteSize);   
	        	            process = remoteSize /step;   
	        	            raf.seek(remoteSize);   
	        	            localreadbytes = remoteSize;   
	        	        }   
	        	        byte[] bytes = new byte[1024];   
	        	        int c;   
	        	        while((c = raf.read(bytes))!= -1){   
	        	            out.write(bytes,0,c);   
	        	            localreadbytes+=c;   
	        	            if(localreadbytes / step != process){   
	        	                process = localreadbytes / step;   
	        	                System.out.println("上传进度:" + process);   
	        	            }   
	        	        }   
	        	        out.flush();   
	        	        raf.close();   
	        	        out.close();   
	        	        boolean result =ftpClient.completePendingCommand();   
	        	        if(remoteSize > 0){   
	        	            status = result?UploadStatus.Upload_From_Break_Success:UploadStatus.Upload_From_Break_Failed;   
	        	        }else {   
	        	            status = result?UploadStatus.Upload_New_File_Success:UploadStatus.Upload_New_File_Failed;   
	        	        }   
	        	        return status;   
					} catch (IOException e) {
						return null;
					}
		       }});
			executor.execute(futureTask);
			
		try {
			// 取得结果，同时设置超时执行时间为5秒
			UploadStatus r = futureTask.get(TIMEOUT, TimeUnit.MILLISECONDS);
			if (r!=null) {
				return r;
			}
			
		} catch (Exception e) {
			futureTask.cancel(true);
			throw new IOException();
		}  finally {
			executor.shutdown();
		}
		throw new IOException();
        
    }   
    
    /**  
     * 取得目录下所有的文件名列表  
     * @throws IOException  
     */  
	public List<String> getFileNames(final String path) throws IOException{  
		ExecutorService executor = Executors.newSingleThreadExecutor();
    	FutureTask<List<String>> futureTask =
			new FutureTask<List<String>>(new Callable<List<String>>() {
		         public List<String> call() {
		        	try {
		        		String[] arr = ftpClient.listNames(path);
	        			if(arr!=null){
	        				return Arrays.asList(ftpClient.listNames(path));
	        			}else{
	        				return new ArrayList<String>();
	        			}
					} catch (IOException e) {
						return null;
					}
		       }});
			executor.execute(futureTask);
			
		try {
			// 取得结果，同时设置超时执行时间为5秒
			List<String> r = futureTask.get(TIMEOUT, TimeUnit.MILLISECONDS);
			if (r!=null) {
				return r;
			}
			
		} catch (Exception e) {
			futureTask.cancel(true);
			throw new IOException();
		}  finally {
			executor.shutdown();
		}
		throw new IOException();
    }     
	/**
	* 删除ftp上的文件
	* @param ftpdirandfilename
	*/
	public void deleteFile(final String fileName) throws IOException{
		ExecutorService executor = Executors.newSingleThreadExecutor();
    	FutureTask<Boolean> futureTask =
			new FutureTask<Boolean>(new Callable<Boolean>() {
		         public Boolean call() {
		        	try {
		        		ftpClient.sendCommand("dele " + fileName + " \r\n");
		        		return true;
					} catch (IOException e) {
						return false;
					}
		       }});
			executor.execute(futureTask);
			
		try {
			// 取得结果，同时设置超时执行时间为5秒
			boolean r = futureTask.get(TIMEOUT, TimeUnit.MILLISECONDS);
			if (r) {
				return ;
			}
			
		} catch (Exception e) {
			futureTask.cancel(true);
			throw new IOException();
		}  finally {
			executor.shutdown();
		}
		throw new IOException();
		
	} 
	/**
	  * 删除ftp目录
	  * @param ftpDirectory
	  */
	public void deletedirectory(final String ftpDirectory) throws IOException{
		ExecutorService executor = Executors.newSingleThreadExecutor();
    	FutureTask<Boolean> futureTask =
			new FutureTask<Boolean>(new Callable<Boolean>() {
		         public Boolean call() {
		        	try {
		        		ftpClient.sendCommand("xrmd " + ftpDirectory + " \r\n");
		        		return true;
					} catch (IOException e) {
						return false;
					}
		       }});
			executor.execute(futureTask);
			
		try {
			// 取得结果，同时设置超时执行时间为5秒
			boolean r = futureTask.get(TIMEOUT, TimeUnit.MILLISECONDS);
			if (r) {
				return ;
			}
			
		} catch (Exception e) {
			futureTask.cancel(true);
			throw new IOException();
		}  finally {
			executor.shutdown();
		}
		throw new IOException();
		
	}
    public static void main(String[] args) {   
		FTPUtil myFtp = new FTPUtil();   
        try {   
            myFtp.connect("192.168.1.252", 21, "up", "up");   
         //   myFtp.upload("G:\\88_TAX_012167365_CSGNSA.dat", "//BF_OUT//88_TAX_012167365_CSGNSAX.dat");  
            myFtp.deleteFile("//BF_OUT//88_SAP_000000135_APTAX1.ok");
          //  myFtp.download("//BF_OUT//xx100.txt", "E:\\FPRZZJ20xd.rar");  
        } catch (IOException e) {   
            System.out.println("连接FTP出错："+e.getMessage());   
        } finally{
        	try {
				myFtp.disconnect();
				System.out.println("断开连接。。。。。。。");
			} catch (IOException e) {
				e.printStackTrace();
			} 
        }
    }  
}  