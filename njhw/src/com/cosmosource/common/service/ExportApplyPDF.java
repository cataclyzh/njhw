package com.cosmosource.common.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import com.cosmosource.base.util.DateUtil;
import com.cosmosource.common.entity.TAcCaapply;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

public class ExportApplyPDF extends PdfPageEventHelper implements IExport {
	
	private Map<String, Map<String, String>> dictMap; //字典信息
	
	private static Font fontNormal_12; //宋体12号字
	private static Font fontBold_12; //黑体12号字
	private static Font fontBold_18; //黑体18号字
	
	static {
		BaseFont bfSong = null;
		try {
			bfSong = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			fontNormal_12 = new Font(bfSong, 12, Font.NORMAL); //宋体12号字
			fontBold_12 = new Font(bfSong, 12, Font.BOLD); //宋体12号字加粗
			fontBold_18 = new Font(bfSong, 18, Font.BOLD); //黑体18号字
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}
	
	public PdfPCell createBodyFirstLeftRightTopBottomBorderCell(String text, Font font){
		PdfPCell cell = new PdfPCell(new Paragraph(text, font));
		cell.setBorder(Rectangle.LEFT + Rectangle.RIGHT + Rectangle.TOP + Rectangle.BOTTOM);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setFixedHeight(10f);
		cell.setMinimumHeight(10f);
		
		return cell;
	}

	public PdfPCell createRightBottomBorderCellLeft(String text, Font font){
		PdfPCell cell = new PdfPCell(new Paragraph(text, font));
		cell.setBorder(Rectangle.RIGHT + Rectangle.BOTTOM);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);  
		cell.setFixedHeight(10f);
		cell.setMinimumHeight(10f);
		
		return cell;
	}
	
	public PdfPCell createRightBottomBorderCellLeft(String text, Font font, int span){
		PdfPCell cell = new PdfPCell(new Paragraph(text, font));
		cell.setBorder(Rectangle.RIGHT + Rectangle.BOTTOM);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);  
		cell.setFixedHeight(10f);
		cell.setMinimumHeight(10f);
		cell.setColspan(span);
		
		return cell;
	}
	
	public PdfPCell createBottomBorderCellLeft(String text, Font font){
		PdfPCell cell = new PdfPCell(new Paragraph(text, font));
		cell.setBorder(Rectangle.BOTTOM);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);  
		cell.setFixedHeight(10f);
		cell.setMinimumHeight(10f);
		
		return cell;
	}
	
	public PdfPCell createBottomBorderCellLeft(String text, Font font, int span){
		PdfPCell cell = new PdfPCell(new Paragraph(text, font));
		cell.setBorder(Rectangle.BOTTOM);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);  
		cell.setFixedHeight(10f);
		cell.setMinimumHeight(10f);
		cell.setColspan(span);
		
		return cell;
	}
	
	public PdfPCell createFootRightBorderCellLeft(String text, Font font){
		PdfPCell cell = new PdfPCell(new Paragraph(text, font));
		cell.setBorder(Rectangle.RIGHT);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);  
		cell.setFixedHeight(10f);
		cell.setMinimumHeight(10f);
		
		return cell;
	}
	
	public PdfPCell createFootRightBorderCellLeft(String text, Font font, int span){
		PdfPCell cell = new PdfPCell(new Paragraph(text, font));
		cell.setBorder(Rectangle.RIGHT);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);  
		cell.setFixedHeight(10f);
		cell.setMinimumHeight(10f);
		cell.setColspan(span);
		
		return cell;
	}
	
	public PdfPCell createFootNoBorderCellLeft(String text, Font font){
		PdfPCell cell = new PdfPCell(new Paragraph(text, font));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);  
		cell.setFixedHeight(10f);
		cell.setMinimumHeight(10f);
		
		return cell;
	}
	
	public PdfPCell createFootNoBorderCellLeft(String text, Font font, int span){
		PdfPCell cell = new PdfPCell(new Paragraph(text, font));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);  
		cell.setFixedHeight(10f);
		cell.setMinimumHeight(10f);
		cell.setColspan(span);
		
		return cell;
	}
	
	/**
	 * 根据字典类别和编码获取字典名称
	 * @param dictType
	 * @param code
	 * @return
	 */
	protected String getDictName(String dictType, String code){
		if(dictMap != null && dictMap.size() > 0){
			Map<String, String> dictSubMap = dictMap.get(dictType);
			if(dictSubMap != null && dictSubMap.size() > 0){
				return dictSubMap.get(code);
			}
		}
		
		return null;
	}

	public void setDictMap(Map<String, Map<String, String>> dictMap) {
		this.dictMap = dictMap;	
	}
	
	public boolean export(TAcCaapply apply, OutputStream os) {
		PdfWriter writer = null;
		Document document = null;
		boolean result = false; // 设置文件生成标志
		try {
			document = new Document(PageSize.A4, 50, 50, 50, 50);
			writer = PdfWriter.getInstance(document, os);
			writer.setPageEvent(this);  //为了在表格分页时最后显示表格线，设置此Event

			document.open();
			Paragraph paragraphBlankLine = new Paragraph("　", fontNormal_12);  //预定义的空行
			Paragraph paragraphTitle = new Paragraph("CA申请表", fontBold_18);  //标题
			paragraphTitle.setAlignment("center");  //设置对齐方式
			document.add(paragraphTitle); //将标题增加到文档内
			document.add(paragraphBlankLine); //增加一个空行
			
			//证书申请信息
			document.add(this.createApplyInfo(apply));
			
			//申请机构信息
			document.add(this.createOrgInfo(apply));
			
			//机构经办人信息
			document.add(this.createHandlerInfo(apply));
			
			//申请机构申明
			document.add(this.createDeclareInfo(apply));
			
			//备注信息
			paragraphTitle = new Paragraph("注：本表需加盖申请机构公章。申请机构须同时提供机构证件复印件（加盖公章）、经办人身份证件复印件、机构授予经办人的授权书原件。", fontBold_12);
			document.add(paragraphTitle);
			
			result = true;
		}
		catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (document != null){
					document.close();
				}
				if (os != null){
					os.close();
				}
				if (writer != null){
					writer.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}	
		
		return result;
	}
	
	/**
	 * 生成证书申请信息
	 * @param apply
	 * @return
	 * @throws DocumentException
	 */
	private PdfPTable createApplyInfo(TAcCaapply apply) throws DocumentException{
		PdfPTable applyInfoMain = new PdfPTable(2);
		applyInfoMain.setWidthPercentage(100f);
		applyInfoMain.setWidths(new int[]{5, 95});
		applyInfoMain.addCell(this.createBodyFirstLeftRightTopBottomBorderCell("\n证\n书\n申\n请\n信\n息\n\n", fontBold_12));
		
		PdfPTable applyInfoDetail = new PdfPTable(6);
		applyInfoDetail.setWidthPercentage(100f);
		applyInfoDetail.setWidths(new int[]{20, 20, 12, 18, 16, 14});
		applyInfoDetail.addCell(this.createRightBottomBorderCellLeft("申请日期", fontNormal_12));
		applyInfoDetail.addCell(this.createRightBottomBorderCellLeft(DateUtil.date2Str(apply.getApplydate(), "yyyy年MM月dd日"), fontNormal_12));
		applyInfoDetail.addCell(this.createRightBottomBorderCellLeft("证书数量", fontNormal_12));
		applyInfoDetail.addCell(this.createRightBottomBorderCellLeft(apply.getApplynum().toString(), fontNormal_12));
		applyInfoDetail.addCell(this.createRightBottomBorderCellLeft("证书期限", fontNormal_12));
		applyInfoDetail.addCell(this.createBottomBorderCellLeft(this.getDictName("CA_CATERM", apply.getCaterm()), fontNormal_12));
		
		applyInfoDetail.addCell(this.createRightBottomBorderCellLeft("证书种类", fontNormal_12));
		applyInfoDetail.addCell(this.createBottomBorderCellLeft(this.getDictName("CA_CATYPE", apply.getCatype()), fontNormal_12, 5));
		
		applyInfoDetail.addCell(this.createFootRightBorderCellLeft("业务类型", fontNormal_12));
		applyInfoDetail.addCell(this.createFootRightBorderCellLeft(this.getDictName("CA_APPLYTYPE", apply.getApplytype()), fontNormal_12, 2));
		applyInfoDetail.addCell(this.createFootRightBorderCellLeft("证书DN", fontNormal_12));
		applyInfoDetail.addCell(this.createFootNoBorderCellLeft("0".equals(apply.getApplytype()) ? "" : apply.getCadn(), fontNormal_12, 2));
		
		applyInfoMain.addCell(applyInfoDetail);
		
		return applyInfoMain;
	}
	
	/**
	 * 生成申请机构信息
	 * @param apply
	 * @return
	 * @throws DocumentException
	 */
	private PdfPTable createOrgInfo(TAcCaapply apply) throws DocumentException{
		PdfPTable orgInfoMain = new PdfPTable(2);
		orgInfoMain.setWidthPercentage(100f);
		orgInfoMain.setWidths(new int[]{5, 95});
		orgInfoMain.addCell(this.createBodyFirstLeftRightTopBottomBorderCell("\n申\n请\n机\n构\n信\n息\n\n", fontBold_12));
		
		PdfPTable orgInfoDetail = new PdfPTable(6);
		orgInfoDetail.setWidthPercentage(100f);
		orgInfoDetail.setWidths(new int[]{20, 20, 12, 18, 16, 14});
		orgInfoDetail.addCell(this.createRightBottomBorderCellLeft("机构名称", fontNormal_12));
		orgInfoDetail.addCell(this.createRightBottomBorderCellLeft(apply.getOrgname(), fontNormal_12, 2));
		orgInfoDetail.addCell(this.createRightBottomBorderCellLeft("英文/拼音简称", fontNormal_12));
		orgInfoDetail.addCell(this.createBottomBorderCellLeft(apply.getOrgnameen(), fontNormal_12, 2));
		
		orgInfoDetail.addCell(this.createRightBottomBorderCellLeft("机构证件类型", fontNormal_12));
		if("2".equals(apply.getOrgidtype())){
			orgInfoDetail.addCell(this.createRightBottomBorderCellLeft(this.getDictName("CA_ORGIDTYPE", apply.getOrgidtype()), fontNormal_12, 2));
			orgInfoDetail.addCell(this.createRightBottomBorderCellLeft("请注明", fontNormal_12));
			orgInfoDetail.addCell(this.createBottomBorderCellLeft(apply.getOrgidname(), fontNormal_12, 2));
		}
		else {
			orgInfoDetail.addCell(this.createBottomBorderCellLeft(this.getDictName("CA_ORGIDTYPE", apply.getOrgidtype()), fontNormal_12, 5));			
		}
		
		orgInfoDetail.addCell(this.createFootRightBorderCellLeft("机构证件号码", fontNormal_12));
		orgInfoDetail.addCell(this.createFootNoBorderCellLeft(apply.getOrgidnum(), fontNormal_12, 5));
		
		orgInfoMain.addCell(orgInfoDetail);
		
		return orgInfoMain;
	}
	
	/**
	 * 生成机构经办人信息
	 * @param apply
	 * @return
	 * @throws DocumentException
	 */
	private PdfPTable createHandlerInfo(TAcCaapply apply) throws DocumentException{
		PdfPTable handlerInfoMain = new PdfPTable(2);
		handlerInfoMain.setWidthPercentage(100f);
		handlerInfoMain.setWidths(new int[]{5, 95});
		handlerInfoMain.addCell(this.createBodyFirstLeftRightTopBottomBorderCell("\n机\n构\n经\n办\n人\n信\n息\n\n", fontBold_12));
		
		PdfPTable handlerInfoDetail = new PdfPTable(6);
		handlerInfoDetail.setWidthPercentage(100f);
		handlerInfoDetail.setWidths(new int[]{20, 20, 12, 18, 16, 14});
		handlerInfoDetail.addCell(this.createRightBottomBorderCellLeft("经办人姓名", fontNormal_12));
		handlerInfoDetail.addCell(this.createBottomBorderCellLeft(apply.getHandler(), fontNormal_12, 5));
		
		handlerInfoDetail.addCell(this.createRightBottomBorderCellLeft("经办人证件类型", fontNormal_12));
		handlerInfoDetail.addCell(this.createBottomBorderCellLeft(this.getDictName("CA_HANDLERIDTYPE", apply.getHandleridtype()), fontNormal_12, 5));
		
		handlerInfoDetail.addCell(this.createRightBottomBorderCellLeft("经办人证件号码", fontNormal_12));
		handlerInfoDetail.addCell(this.createBottomBorderCellLeft(apply.getHandleridnum(), fontNormal_12, 5));
		
		handlerInfoDetail.addCell(this.createRightBottomBorderCellLeft("电话", fontNormal_12));
		handlerInfoDetail.addCell(this.createRightBottomBorderCellLeft(apply.getHandlertel(), fontNormal_12, 2));
		handlerInfoDetail.addCell(this.createRightBottomBorderCellLeft("传真", fontNormal_12));
		handlerInfoDetail.addCell(this.createBottomBorderCellLeft(apply.getHandlerfax(), fontNormal_12, 2));
		
		handlerInfoDetail.addCell(this.createRightBottomBorderCellLeft("电子邮件", fontNormal_12));
		handlerInfoDetail.addCell(this.createBottomBorderCellLeft(apply.getHandlermail(), fontNormal_12, 5));
		
		handlerInfoDetail.addCell(this.createFootRightBorderCellLeft("USB Key寄送地址", fontNormal_12));
		handlerInfoDetail.addCell(this.createFootRightBorderCellLeft(apply.getHandleraddr(), fontNormal_12, 3));	
		handlerInfoDetail.addCell(this.createFootRightBorderCellLeft("邮政编码", fontNormal_12));
		handlerInfoDetail.addCell(this.createFootNoBorderCellLeft(apply.getHandlerpostcode(), fontNormal_12));
		
		handlerInfoMain.addCell(handlerInfoDetail);
		
		return handlerInfoMain;
	}
	
	/**
	 * 生成申请机构申明
	 * @param apply
	 * @return
	 * @throws DocumentException
	 */
	private PdfPTable createDeclareInfo(TAcCaapply apply) throws DocumentException{
		PdfPTable declareInfoMain = new PdfPTable(2);
		declareInfoMain.setWidthPercentage(100f);
		declareInfoMain.setWidths(new int[]{5, 95});
		declareInfoMain.addCell(this.createBodyFirstLeftRightTopBottomBorderCell("\n\n\n\n申\n请\n机\n构\n申\n明\n\n\n\n", fontBold_12));
		
		PdfPTable declareInfoDetail = new PdfPTable(6);
		declareInfoDetail.setWidthPercentage(100f);
		declareInfoDetail.setWidths(new int[]{20, 20, 12, 18, 16, 14});
		
		declareInfoDetail.addCell(this.createBottomBorderCellLeft("本机构承诺以上所填写的申请资料真实、有效。本机构已认真阅读并同意遵守中金金融认证中心有限公司（CFCA）网站（http://www.cfca.com.cn)发布的《数字证书服务协议》、《电子认证业务规则（CPS））》中规定的相关义务。", fontNormal_12, 6));
		
		declareInfoDetail.addCell(this.createFootRightBorderCellLeft("申请机构盖章", fontNormal_12));
		declareInfoDetail.addCell(this.createFootRightBorderCellLeft("", fontNormal_12, 3));
		declareInfoDetail.addCell(this.createFootRightBorderCellLeft("日期", fontNormal_12));
		declareInfoDetail.addCell(this.createFootNoBorderCellLeft("", fontNormal_12));
		
		declareInfoMain.addCell(declareInfoDetail);
		
		return declareInfoMain;
	}
}
