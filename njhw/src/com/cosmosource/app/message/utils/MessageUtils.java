package com.cosmosource.app.message.utils;


public class MessageUtils {
	
	public static String getVsFlag(String status){
		String result = "";
		/**
		 * if("00".equals(status))
			result = "初始预约";
		else 
		 */
		if("01".equals(status))
			result = "已申请";
		else if("02".equals(status))
			result = "已确认";
		else if("03".equals(status))
			result = "已拒绝";
		else if("04".equals(status))
			result = "已到访";
		else if("05".equals(status))
			result = "已结束";
		else if("99".equals(status))
			result = "已取消";
		else if("06".equals(status))
			result = "异常结束";
		else{
			result = "异常状态";
		}
		return result;
	}
	
	public static String getVsType(String vsType){
		String result = "";
		if("1".equals(vsType)){
			result = "主动预约";
		}else if("2".equals(vsType)){
			result = "公网预约";
		}else if("3".equals(vsType)){
			result = "前台预约";
		}else{
			result = "异常状态";
		}
		return result;
	}
}
