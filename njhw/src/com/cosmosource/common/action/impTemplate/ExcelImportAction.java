package com.cosmosource.common.action.impTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.springframework.jdbc.UncategorizedSQLException;

import com.cosmosource.base.action.BaseAction;
import com.cosmosource.common.service.impTemplate.ImpTemplateManager;
import com.simple.tool.imp.excel.ExcelDataLoader;
import com.simple.tool.imp.excel.ExcelSource;
import com.simple.tool.imp.service.ImportService;

@SuppressWarnings("serial")
public class ExcelImportAction extends BaseAction<Object>{

	
	private ImpTemplateManager impTemplateManager;
	private ImportService importService;
	private ExcelDataLoader excelDataLoader;
	private File file;
	
	public ExcelDataLoader getExcelDataLoader() {
		return excelDataLoader;
	}

	public void setExcelDataLoader(ExcelDataLoader excelDataLoader) {
		this.excelDataLoader = excelDataLoader;
	}

	@Override
	protected void prepareModel() throws Exception {
		
	}

	@Override
	public Object getModel() {
		return null;
	}

	public String init(){
		return INIT;
	}
	
	public String userImportexl(){
		try{
			File file2 = new File(file.getPath());
			InputStream in = new FileInputStream(file2);
			ExcelSource excelSource = new ExcelSource();
			excelSource.setInputStream(in); 	
			excelSource.setId("usersImport");
			excelSource.setType("excel");
			excelSource.setName("用户导入");
			importService.imp(excelSource);
			addActionMessage("导入成功！");
//			antiManager.updateBySql("JdSQL.updateBatch", null);
//			antiManager.updateBySql("JdSQL.updateSum", null);
		}catch(FileNotFoundException e){
			e.printStackTrace();
			addActionMessage("您要导入的文件不存在!");
		}catch(UncategorizedSQLException e){
			addActionMessage("不能更新库表！请联系管理员");
			e.printStackTrace();
		}catch(NumberFormatException e){
			e.printStackTrace();
			addActionMessage("非法的数字格式"+e.getMessage().substring(16, e.getMessage().length()));
		}catch(Exception e){
			e.printStackTrace();
			addActionMessage(e.getMessage());
		}
		return SUCCESS;
	}

	public ImportService getImportService() {
		return importService;
	}

	public void setImportService(ImportService importService) {
		this.importService = importService;
	}

	public ImpTemplateManager getImpTemplateManager() {
		return impTemplateManager;
	}

	public void setImpTemplateManager(ImpTemplateManager impTemplateManager) {
		this.impTemplateManager = impTemplateManager;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	
}
