/**
 * <p>文件名: ExportAction.java</p>
 * <p>:描述：导出Action</p>
 * <p>版权: Copyright (c) 2010 Beijing Cosmosource Co. Ltd.</p>
 * <p>公司: Cosmosource Beijing Office</p>
 * <p>All right reserved.</p>
 * @创建时间： 2012-2-29 20:24:10 
 * @作者： WXJ
 * @版本： V1.0
 * <p>类修改者		 修改日期			修改说明   </p>   
 * 
 */
package com.cosmosource.base.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.cosmosource.base.model.Attendance;
import com.cosmosource.base.util.DateUtil;
import com.simple.tool.elapse.TimeFormatorZh;
import com.simple.tool.elapse.WatchElapse;
import com.simple.tool.export.ExportExcelExecutor;
import com.simple.tool.export.util.FileReader;
import com.simple.tool.export.util.IFileReader;

/**
 * 
 * @类描述: 导出Action
 * @作者： WXJ
 */
@SuppressWarnings("serial")
public abstract class ExportAction<T> extends BaseAction<T> {

	protected static Map<String, String> contentTypes = new HashMap<String, String>();

	static {
		contentTypes.put("doc", "application/msword");
		contentTypes.put("docx", "application/msword");
		contentTypes.put("xls", "application/vnd.ms-excel");
		contentTypes.put("xlsx", "application/vnd.ms-excel");
		contentTypes.put("ppt", "application/vnd.ms-powerpoint");
		contentTypes.put("pdf", "application/pdf");
		contentTypes.put("exe", "application/octet-stream");
		contentTypes.put("bin", "application/octet-stream");
		contentTypes.put("rar", "application/octet-stream");
		contentTypes.put("zip", "application/zip");
		contentTypes.put("htm", "text/html");
		contentTypes.put("html", "text/html");
		contentTypes.put("gif", "image/gif");
		contentTypes.put("bmp", "image/bmp");
		contentTypes.put("jpeg", "image/jpeg");
		contentTypes.put("jpg", "image/jpeg");
		contentTypes.put("png", "image/png");
		contentTypes.put("mpeg", "video/jpeg");
		contentTypes.put("mp3", "audio/mpeg");
	}

	/**
	 * @描述: 获取输出文件类型
	 * @作者： WXJ
	 * @日期：2012-2-28
	 * 
	 * @param fileExtension
	 *            包含扩展名的文件名
	 * @return
	 */
	@SuppressWarnings("static-access")
	public String getContentType(String fileExtension) {
		for (String txt : contentTypes.keySet()) {
			if (fileExtension.toLowerCase().endsWith(txt)) {
				return this.contentTypes.get(txt);
			}
		}

		return "application/octet-stream";
	}

	/**
	 * @描述: 转码下载文件名
	 * @作者： WXJ
	 * @日期：2012-2-29
	 * 
	 * @param fileName
	 *            文件名
	 * @return
	 * @throws Exception
	 */
	public String encodeFileName(String fileName) throws Exception {
		return new String(fileName.getBytes("gbk"), "ISO-8859-1");
	}

	/**
	 * @描述: 转码下载文件名
	 * @作者： ChunJing
	 * @日期：2012-10-28
	 * @param filename
	 * @param dataMap
	 * @param path 
	 * @param localFileName 
	 * @param output
	 */
	@SuppressWarnings("unchecked")
	public void writeExceltoLocal(String fileName,
			Map<String, Object> dataMap, String path, String localFileName) {

		IFileReader r = new FileReader();
		logger.info("start load template file!");
		InputStream input = r.readClassPathFileStream(fileName);

		if (input == null) {
			throw new RuntimeException("load templateFile[" + fileName
					+ "] error!");
		}
		logger.info("load template file success!");

		Workbook templateExcel = null;
		WritableWorkbook wbook = null;
		// 模板excel文件
		try {
			templateExcel = Workbook.getWorkbook(input);
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 打开一个模板文件的副本，并且指定数据写回到输出流
		// 将WritableWorkbook直接写入到输出流
		try {
			logger.info(path + File.separator + localFileName);
			File directory = new File(path);
			//创建目录
			if (!directory.exists()){
				directory.mkdirs();
			}
			//创建文件
			File file = new File(path + File.separator + localFileName);
			//如果成功创建文件
			if (file.createNewFile()){
				wbook = Workbook.createWorkbook(file, templateExcel);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 获取第一个工作表
		WritableSheet wsheet = wbook.getSheet(0);
		// 工作表名字，一些信息
		// logger.info(wsheet.getName());
		// logger.info("工作表名称：" + wsheet.getName());
		// logger.info("工作表列数：" + wsheet.getColumns());
		// logger.info("工作表行数：" + wsheet.getRows());

		// 解析数据，写入excel
		if (dataMap != null) {
			// logger.info(dataMap.toString());
			/*
			 * {list=[{ATTENDANCE_ORGNAME=物业管理部, ATTENDANCE_SCHEDULE_ID=70,
			 * ATTENDANCE_SCHEDULE_ADMINNAME=许瑞 , ATTENDANCE_USERNAME=邱绍鹏,
			 * ATTENDANCE_SCHEDULE_INTIME=2013-10-09 08:30:00, RES_BAK4=null,
			 * RES_BAK3=null, ATTENDANCE_OUTTIME=2013-10-09 12:27:14,
			 * RES_BAK2=2013-10-09, ATTENDANCE_SCHEDULE_OUTTIME=2013-10-09
			 * 12:00:00, RES_BAK1=正常, ATTENDANCE_USERID=4522,
			 * ATTENDANCE_ORGID=218, ATTENDANCE_INTIME=2013-10-09 08:27:44,
			 * ATTENDANCE_ID=52, ATTENDANCE_SCHEDULE_ADMINID=4533},....}],
			 * dateStr=2013-10-28}
			 */

			// 遍历 Map,有 list 跟 dateStr 两个值
			// List<Attendance> attenList = (List<Attendance>)
			// dataMap.get("list");
			// List<Map<String,Object>> resultList = new ArrayList();

			List<Map<String, Object>> attenList = (List<Map<String, Object>>) dataMap
					.get("list");
			Iterator<Map<String, Object>> iter = attenList.iterator();

			/**
			 * 1、获取 getXxx方法数目，循环调用其get方法获取值 2、获取所有fileds值，结合getter方法使用
			 */
			int getMethodsCount = 0;
			Method[] methods = Attendance.class.getMethods();
			for (Method method : methods) {
				if (method.getName().startsWith("get")
						&& !method.getName().startsWith("getClass")) {
					getMethodsCount++;
					// logger.info(method.getName());
				}
			}
			LinkedList<String> list = this.fieldsOperation(Attendance.class);
			// logger.info(list.toString());

			// 表格字体样式
			WritableCellFormat cellStyle = new WritableCellFormat(
					new WritableFont(WritableFont.createFont("宋体"), 10));
			try {
				// 垂直居中
				cellStyle.setVerticalAlignment(VerticalAlignment.CENTRE);
				// 水平居中
				cellStyle.setAlignment(Alignment.CENTRE);
			} catch (WriteException e) {
				e.printStackTrace();
			}

			// 创建表格日期
			// 日期样式
			WritableCellFormat dateStyle = new WritableCellFormat(
					new WritableFont(WritableFont.createFont("宋体"), 10));
			try {
				// 垂直居中
				dateStyle.setVerticalAlignment(VerticalAlignment.CENTRE);
				wsheet.addCell(new Label(1, 2, DateUtil.date2Str(new Date(),
						"yyyy-MM-dd"), dateStyle));
			} catch (WriteException e) {
				e.printStackTrace();
			}


			int column = 0;
			int row = 0;
			while (iter.hasNext()) {
				// attendance = iter.next();
				Map<String, Object> map = iter.next();
				// 往excel表里写数据
				for (int k = 0; k < getMethodsCount; k++) {
					// 从第5行第1列开始 (column 0,row 4)
					// 调用 Attendance.getAttendance_schedule_adminname() 等方法

					try {
						wsheet.addCell(new Label(column, row + 4, (map.get(list
								.get(k)).toString()).trim(), cellStyle));
					} catch (RowsExceededException e) {
						e.printStackTrace();
					} catch (WriteException e) {
						e.printStackTrace();
					}
					column++;
					/*
					 * wsheet.addCell(new Label(column, row + 4, this.getter(
					 * Attendance.class, this.upperFirstLetter(list.get(k)))));
					 */
				}
				// 从下个行，第一列开始写数据
				column = 0;
				row++;
				// Map.Entry entry = (Map.Entry) iter.next();
				// entry.getKey() 获取 key 的值
				// entry.getValue() 获取 value 的值
			}
		}
		try {
			//写到输出流
			wbook.write();
			//输出流转换为输入流
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				wbook.close();
			} catch (WriteException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 
	 * @param OutputStream
	 * @return InputStream
	 * @throws Exception
	 */
    public InputStream parseStream(OutputStream out) throws Exception
    {
        ByteArrayOutputStream   baos = new   ByteArrayOutputStream();
        baos = (ByteArrayOutputStream) out;
//        ByteArrayInputStream swapStream = new ByteArrayInputStream(((ByteArrayOutputStream) out).toByteArray());
        return new ByteArrayInputStream(baos.toByteArray());
    }

	/**
	 * @param obj
	 *            操作的对象
	 * @param att
	 *            操作的属性
	 * */
	public String getter(Object obj, String att) {
		String value = null;
		try {
			Method method = obj.getClass().getMethod("get" + att);
			value = method.invoke(obj).toString();
			// logger.info(method.invoke(obj).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * 获取方法中所有属性名字
	 * 
	 * @param cls
	 * @return list
	 */
	@SuppressWarnings("rawtypes")
	public LinkedList<String> fieldsOperation(Class cls) {
		LinkedList<String> fieldsList = new LinkedList<String>();
		// 取得本类的全部属性
		Field[] fields = cls.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			// 权限修饰符
			int mo = fields[i].getModifiers();
			String priv = Modifier.toString(mo);
			// 属性类型
			Class<?> type = fields[i].getType();
			/*
			 * logger.info(priv + " " + type.getName() + " " +
			 * fields[i].getName() + ";");
			 */

			fieldsList.add(fields[i].getName().toUpperCase());
		}

		return fieldsList;

	}

	/**
	 * 首字母大写
	 * 
	 * @param str
	 * @return String
	 */
	public String upperFirstLetter(String str) {
		char firstLetter = str.charAt(0);
		return (firstLetter + "").toUpperCase() + str.substring(1);
	}

	/**
	 * @描述: 执行导出
	 * @作者： WXJ
	 * @日期：2012-2-29
	 * 
	 * @param templateFileName
	 *            模板文件名（含模板文件路径）
	 * @param dataMap
	 *            数据map
	 * @param os
	 *            输出流
	 * @throws Exception
	 */
	public void executeExportTemplate(String templateFileName,
			Map<String, Object> dataMap, OutputStream os) throws Exception {
		try {
			WatchElapse we = new WatchElapse(new TimeFormatorZh());
			we.start();
			ExportExcelExecutor executor = new ExportExcelExecutor();
			executor.evaluate(templateFileName, dataMap, os);
			we.end();
			logger.info("generate excel elapsed time:" + we.getElapsedTime());
		} catch (Exception e) {
			logger.error("导出Excel失败");
			e.printStackTrace();
			throw new RuntimeException("导出Excel失败....");
		} finally {
			os.flush();
			os.close();
		}
	}

	/**
	 * @描述: 执行导出Excel(多个sheet)
	 * @作者： WXJ
	 * @日期：2012-7-10
	 * 
	 * @param templateFileName
	 *            模板文件名（含模板文件路径）
	 * @param dataMap
	 *            数据map
	 * @param os
	 *            输出流
	 * @throws Exception
	 */
	public void executeExportManySheetTemplate(String templateFileName,
			Map<String, Object> dataMap, OutputStream os) throws Exception {
		try {
			WatchElapse we = new WatchElapse(new TimeFormatorZh());
			we.start();
			ExportExcelExecutor executor = new ExportExcelExecutor();
			executor.evaluateManySheet(templateFileName, dataMap, os);
			we.end();
			logger.info("generate excel elapsed time:" + we.getElapsedTime());
		} catch (Exception e) {
			logger.error("导出Excel失败");
			e.printStackTrace();
			throw new RuntimeException("导出Excel失败");
		} finally {
			os.flush();
			os.close();
		}
	}

	/**
	 * @描述: 导出Excel文件
	 * @作者： WXJ
	 * @日期：2012-2-29
	 * 
	 * @param fileName
	 *            导出文件名（不含扩展名）
	 * @param templateFileName
	 *            模板文件名（含模板文件路径）
	 * @param dataMap
	 *            数据map
	 * @throws Exception
	 */
	public void executeExportExcel(String fileName, String templateFileName,
			Map<String, Object> dataMap) {
		String xlsFilename = fileName + ".xls";

		try {
			 this.getResponse().setContentType(this.getContentType(xlsFilename));
			 this.getResponse().addHeader("Content-disposition",
			 "attachment;filename="
			 + encodeFileName(xlsFilename));
			 
			// response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			// response.setHeader("Content-Type", "application/octet-stream");

		} catch (Exception e) {
			logger.info("addHeader() 异常！");
			e.printStackTrace();
		}
		OutputStream output = null;
		try {
			output = this.getResponse().getOutputStream();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		// output = new ByteArrayOutputStream();
		// 清空response
		// this.getResponse().reset();

		try {
			 this.executeExportTemplate(templateFileName, dataMap, output);

			/**
			 * 2013-10-28 改用 JExcel 实现 之前封装 apache poi 有时候出错，原因不明 by ChunJing
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @描述: 导出Excel(多个sheet)文件
	 * @作者： WXJ
	 * @日期：2012-7-10
	 * 
	 * @param fileName
	 *            导出文件名（不含扩展名）
	 * @param templateFileName
	 *            模板文件名（含模板文件路径）
	 * @param dataMap
	 *            数据map
	 * @throws Exception
	 */
	public void executeExportExcelManySheet(String fileName,
			String templateFileName, Map<String, Object> dataMap)
			throws Exception {
		String xlsFilename = fileName + ".xls";

		this.getResponse().setContentType(this.getContentType(xlsFilename));
		this.getResponse().addHeader("Content-disposition",
				"attachment;filename=" + encodeFileName(xlsFilename));
		OutputStream os = this.getResponse().getOutputStream();

		this.executeExportManySheetTemplate(templateFileName, dataMap, os);
	}

	/**
	 * @描述: 导出Excel压缩文件
	 * @作者： WXJ
	 * @日期：2012-2-28
	 * 
	 * @param fileName
	 *            导出文件名（不含扩展名）
	 * @param templateFileName
	 *            模板文件名（含模板文件路径）
	 * @param dataMap
	 *            数据map
	 * @throws Exception
	 */
	public void executeExportExcelZip(String fileName, String templateFileName,
			Map<String, Object> dataMap) throws Exception {
		String xlsFilename = fileName + ".xls";
		String downloadFilename = fileName + ".zip";

		this.getResponse()
				.setContentType(this.getContentType(downloadFilename));
		this.getResponse().addHeader("Content-disposition",
				"attachment;filename=" + encodeFileName(downloadFilename));
		ZipOutputStream os = new ZipOutputStream(this.getResponse()
				.getOutputStream());
		os.putNextEntry(new ZipEntry(xlsFilename));

		this.executeExportTemplate(templateFileName, dataMap, os);
	}

	/**
	 * @描述: 导出Excel(多个sheet)压缩文件
	 * @作者： WXJ
	 * @日期：2012-7-10
	 * 
	 * @param fileName
	 *            导出文件名（不含扩展名）
	 * @param templateFileName
	 *            模板文件名（含模板文件路径）
	 * @param dataMap
	 *            数据map
	 * @throws Exception
	 */
	public void executeExportExcelManySheetZip(String fileName,
			String templateFileName, Map<String, Object> dataMap)
			throws Exception {
		String xlsFilename = fileName + ".xls";
		String downloadFilename = fileName + ".zip";

		this.getResponse()
				.setContentType(this.getContentType(downloadFilename));
		this.getResponse().addHeader("Content-disposition",
				"attachment;filename=" + encodeFileName(downloadFilename));
		ZipOutputStream os = new ZipOutputStream(this.getResponse()
				.getOutputStream());
		os.putNextEntry(new ZipEntry(xlsFilename));

		this.executeExportManySheetTemplate(templateFileName, dataMap, os);
	}
}
