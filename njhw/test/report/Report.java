package report;

import java.io.Reader;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class Report {
	
	protected static SqlMapClient sqlMapClient;
	
	//当天访问量
	long currentDayPV;
	
	//累计访问量
	long totalPV;
	
	//识别总量
	long totalNum;
	
	//成功的识别总量
	long correctNum;
	
	//所有评论的用户量
	long allCommentNum;
	
	//
	private StringBuilder sb;
	
	public Report() throws Exception{
		Reader reader = Resources.getResourceAsReader ("report/ReportSqlMapConfig.xml");
		sqlMapClient = SqlMapClientBuilder.buildSqlMapClient(reader);
		sb = new StringBuilder();
	}
	
	private void addLine(String s){
		sb.append(s);
		sb.append("\r\n");
	}
	
	public void countCompareTotalNum(String day) throws Exception{		
		totalNum = Long.valueOf(sqlMapClient.queryForObject("report.getCompareTotalNum", day).toString());		
	}
	
	public void countCorrectCompareTotalNum(String day) throws Exception{
		correctNum = Long.valueOf(sqlMapClient.queryForObject("report.getCorrectCompareTotalNum", day).toString());
	}
	
	public long getNum1(String day, String p1) throws Exception{
		Map param = new HashMap();
		param.put("point", p1);
		param.put("day", day);
		long times = Long.valueOf(sqlMapClient.queryForObject("report.getNum1", param).toString());
		return times;
	}
	
	public long getNum2(String day, String p1, String p2) throws Exception{
		Map param = new HashMap();
		param.put("point1", p1);
		param.put("point2", p2);
		param.put("day", day);
		long times = Long.valueOf(sqlMapClient.queryForObject("report.getNum2", param).toString());
		return times;
	}
	
	public long getNum3(String day, String p1) throws Exception{
		Map param = new HashMap();
		param.put("point1", p1);
		param.put("day", day);
		long times = Long.valueOf(sqlMapClient.queryForObject("report.getNum3", param).toString());
		return times;
	}
	
	public long getNum4(String day) throws Exception{
		Map param = new HashMap();
		param.put("day", day);
		long times = Long.valueOf(sqlMapClient.queryForObject("report.getNum4", param).toString());
		return times;
	}
	
	public long getNum5(String day) throws Exception{
		Map param = new HashMap();
		param.put("day", day);
		long times = Long.valueOf(sqlMapClient.queryForObject("report.getNum5", param).toString());
		return times;
	}
	
	public long getNumShared(String day) throws Exception{
		long times = Long.valueOf(sqlMapClient.queryForObject("report.getNumShared", day).toString());
		return times;
	}
	
	public long getNumComment(String day, String comment) throws Exception{
		Map param = new HashMap();
		param.put("day", day);
		param.put("comment", comment);
		long times = Long.valueOf(sqlMapClient.queryForObject("report.getNumComment", param).toString());
		return times;
	}
	
	public void countNumAllComment(String day) throws Exception{
		allCommentNum = Long.valueOf(sqlMapClient.queryForObject("report.getNumAllComment", day).toString());
	}
	
	public void getTotalNum(String day) throws Exception{
		List<Map> list = sqlMapClient.queryForList("report.getTotalNum", day);
		if(list != null && list.size() > 0){
			Map m = list.get(0);
			totalPV = Long.valueOf(m.get("TOTAL_NUMBER").toString());
			currentDayPV = Long.valueOf(m.get("CURRENTDAY_NUMBER").toString());
		}
	}
	
	public String getReport(String day) throws Exception{
		countCompareTotalNum(day);
		countCorrectCompareTotalNum(day);
		countNumAllComment(day);
		getTotalNum(day);
		
		addLine("截止今天" + Utils.getTimeString1(new Date()) + ",人脸识别访问量"+currentDayPV+"人次(累计访问人次"+totalPV+")");
		addLine("识别量"+totalNum+"人次.");
		addLine("正常完成"+correctNum+"人次.");
		addLine("识别相似度主要集中在70%以上,比例达到"+printPercent1(getNum1(day, "70"))+"%");
		addLine("其中达90% 占 "+printPercent1(getNum1(day, "90"))+"%");
		addLine("其中达80%-90% 占 "+printPercent1(getNum2(day, "80", "90"))+"%");
		addLine("其中达70%-80% 占 "+printPercent1(getNum2(day, "70", "80"))+"%");
		addLine("其中达10%-70% 占 "+printPercent1(getNum2(day, "10", "70"))+"%");
		addLine("其中 10%以下 占 "+printPercent1(getNum3(day, "10"))+"%");
		addLine("由于拍摄照片质量不好导致识别失败的有"+getNum5(day)+",占总识别比例的"+printPercent2(getNum5(day))+"%");
		addLine("由于库中没有对应使用者照片的"+getNum4(day)+"个,占总识别比例"+printPercent2(getNum4(day))+"%");
		addLine("参与评价的用户数量"+allCommentNum+",占成功识别总量的"+printPercent1(allCommentNum)+"%");
		addLine("其中评价5星的占了"+printPercent3(getNumComment(day, "5"))+"%,4星的占"+printPercent3(getNumComment(day, "4"))+"%");
		addLine("分享识别结果的有"+getNumShared(day)+"个,占成功识别总量的"+printPercent1(getNumShared(day))+"%");
		
		return sb.toString();
	}
	
	public String getCurrentDayReport() throws Exception{
		String day = Utils.getDateString(new Date());
		return getReport(day);
	}
	
	private String printPercent1(long p){
//		String s =String.valueOf(); 
//		return s.substring(0, s.indexOf(".") + 3);
		double d = p*100.0/correctNum;
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(d);
	}
	
	private String printPercent2(long p){
		double d = p*100.0/totalNum;
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(d);
		//return s.substring(0, s.indexOf(".") + 3);
	}
	
	
	
	private String printPercent3(long p){
//		String s =String.valueOf(p*100.0/allCommentNum); 
//		return s.substring(0, s.indexOf(".") + 3);
		double d = p*100.0/allCommentNum;
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(d);
	}
	
	public static void main(String[] args) throws Exception{
		Report p = new Report();
		String s = p.getCurrentDayReport();
		System.out.println(s);
	}

}
