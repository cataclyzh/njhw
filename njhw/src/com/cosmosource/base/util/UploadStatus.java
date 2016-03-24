package com.cosmosource.base.util;

public class UploadStatus {
	
	String sflag = null;
	public UploadStatus(String flag){ 
		sflag = flag;
	}

	public static final UploadStatus Create_Directory_Success = new UploadStatus("1");
	public static final UploadStatus Create_Directory_Fail = new UploadStatus("2");;
	public static final UploadStatus File_Exits = new UploadStatus("3");;
	public static final UploadStatus Remote_Bigger_Local = new UploadStatus("4");;
	public static final UploadStatus Upload_From_Break_Failed = new UploadStatus("6");;
	public static final UploadStatus Delete_Remote_Faild = new UploadStatus("6");;
	public static final UploadStatus Upload_From_Break_Success = new UploadStatus("7");;
	public static final UploadStatus Upload_New_File_Success = new UploadStatus("8");;
	public static final UploadStatus Upload_New_File_Failed = new UploadStatus("9");;
	
	

}
