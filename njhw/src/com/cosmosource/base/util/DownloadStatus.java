package com.cosmosource.base.util;

public class DownloadStatus {
	String sflag = null;
	public DownloadStatus(String flag){ 
		sflag = flag;
	}
	public static final DownloadStatus Remote_File_Noexist = new DownloadStatus("1");
	public static final DownloadStatus Local_Bigger_Remote = new DownloadStatus("2");
	public static final DownloadStatus Download_From_Break_Success = new DownloadStatus("3");
	public static final DownloadStatus Download_From_Break_Failed = new DownloadStatus("4");
	public static final DownloadStatus Download_New_Success = new DownloadStatus("5");
	public static final DownloadStatus Download_New_Failed = new DownloadStatus("6");

}
