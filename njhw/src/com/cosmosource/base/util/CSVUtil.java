package com.cosmosource.base.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import com.Ostermiller.util.CSVPrinter;

public class CSVUtil{
	
//	private static String filePath = System.getProperty("file.separator") + "root" + System.getProperty("file.separator") + "maintence";
	private static String filePath = System.getProperty("file.separator") + "home" + 
			File.separator + "njhw" + 
			System.getProperty("file.separator") + "maintence";
	
	public boolean CheckFileExit(String fileName, boolean isDaily) {
		if (isDaily) {
			fileName = filePath + System.getProperty("file.separator") + DateUtil.date2Str(DateUtil.getSysDate(), "yyyy-MM-dd") + System.getProperty("file.separator") + fileName + ".csv";
		} else {
			fileName = filePath + System.getProperty("file.separator") + fileName + ".csv";
		}
		return new File(fileName).exists();
	}
	
	public void DeleteCsvFile(String fileName) {
		fileName = filePath + System.getProperty("file.separator") + fileName + ".csv";
		File f = new File(fileName);
		if (f.exists()) {
			f.delete();
		}
	}
	
	public void WriteHeader(String[] headers, String fileName, boolean isDaily){
		String folder = "";
		if (isDaily) {
			fileName = filePath + System.getProperty("file.separator") + DateUtil.date2Str(DateUtil.getSysDate(), "yyyy-MM-dd") + System.getProperty("file.separator") + fileName + ".csv";
			folder = filePath + System.getProperty("file.separator") + DateUtil.date2Str(DateUtil.getSysDate(), "yyyy-MM-dd");
		} else {
			fileName = filePath + System.getProperty("file.separator") + fileName + ".csv";
			folder = filePath;
		}
		File logFolder = new File(folder);
        try {
            if (!logFolder.exists()) {
                logFolder.mkdirs();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        File logFile = new File(fileName);
        try {
            if (!logFile.exists()) {
                logFile.createNewFile();
            } else {
            	return;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

		FileOutputStream fos = null;  
        OutputStreamWriter osw = null;  
		try {
			fos = new FileOutputStream(fileName);  
            osw = new OutputStreamWriter(fos, "gb2312");  
			CSVPrinter csvOut = new CSVPrinter(osw, '#');
			csvOut.println(headers);
			csvOut.close();
			osw.close();
			fos.close();
		} catch (Exception x){
			throw new RuntimeException(x);
		}
	}
	
	
	public void WriteOneLine(String[] results,String fileName, boolean isDaily){
		if (isDaily) {
			fileName = filePath + System.getProperty("file.separator") + DateUtil.date2Str(DateUtil.getSysDate(), "yyyy-MM-dd") + System.getProperty("file.separator") + fileName + ".csv";
		} else {
			fileName = filePath + System.getProperty("file.separator") + fileName + ".csv";
		}
		
		FileOutputStream fos = null;  
        OutputStreamWriter osw = null;  
		try {
			fos = new FileOutputStream(fileName,true);  
            osw = new OutputStreamWriter(fos, "gb2312");  
			CSVPrinter csvOut = new CSVPrinter(osw, '#');
			csvOut.writeln(results);
			csvOut.close();
			osw.close();
			fos.close();
		} catch (Exception x){
			throw new RuntimeException(x);
		}
	}
	
	public void WriteLineList(List<String[]> resultList,String fileName, boolean isDaily){
		if (isDaily) {
			fileName = filePath + System.getProperty("file.separator") + DateUtil.date2Str(DateUtil.getSysDate(), "yyyy-MM-dd") + System.getProperty("file.separator") + fileName + ".csv";
		} else {
			fileName = filePath + System.getProperty("file.separator") + fileName + ".csv";
		}
		
		FileOutputStream fos = null;  
        OutputStreamWriter osw = null;  
		try {
			fos = new FileOutputStream(fileName,true);  
            osw = new OutputStreamWriter(fos, "gb2312");  
			CSVPrinter csvOut = new CSVPrinter(osw, '#');
			for(int i=0;i<resultList.size();i++){
				csvOut.writeln(resultList.get(i));
			}
			csvOut.close();
			osw.close();
			fos.close();
		} catch (Exception x){
			throw new RuntimeException(x);
		}
	}
	
}
