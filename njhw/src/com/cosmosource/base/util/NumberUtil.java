package com.cosmosource.base.util;

import java.text.DecimalFormat;

import org.apache.commons.lang.math.NumberUtils;

/**
 * 
 * 说明：Number处理工具类
 * @author WXJ
 **/

public class NumberUtil {	
	
    /**
     * 把指定的字符串转换成int，如果输入的是null 或  非数字 返回 0
     * <pre>
     *   NumberUtil.strToInt(null) = 0
     *   NumberUtil.strToInt("")   = 0
     *   NumberUtil.strToInt("aa")   = 0
     *   NumberUtil.strToInt("1")  = 1
     * </pre>
     * @param String 
     * @return int
     */
    public static int strToInt(String str) {
        return NumberUtils.toInt(str);
    }
	
    /**
     * 把指定的字符串转换成long，如果输入的是null 或  非数字 返回 0
     * <pre>
     *   NumberUtil.strToLong(null) = 0
     *   NumberUtil.strToLong("")   = 0
     *   NumberUtil.strToLong("aa")   = 0
     *   NumberUtil.strToLong("1")  = 1
     * </pre>
     * @param String 
     * @return long
     */
    public static long strToLong(String str) {
        return NumberUtils.toLong(str);
    }
    /**
     * 把指定的字符串转换成double，如果输入的是null 或  非数字 返回 0
     * <pre>
     *   NumberUtil.strToDouble(null) = 0.0
     *   NumberUtil.strToDouble("")   = 0.0
     *   NumberUtil.strToDouble("aa")   = 0.0
     *   NumberUtil.strToDouble("1")  = 1.0
     * </pre>
     * @param String 
     * @return double
     */
    public static double strToDouble(String str) {
        return NumberUtils.toDouble(str);
    }
    /**
     * 把指定的字符串转换成Long
     * @param String 
     * @return Long
     */
    public static Long createLong(String str) {
        return NumberUtils.createLong(str);
    }
    /**
     * 把指定的字符串转换成Double
     * @param String 
     * @return Double
     */
    public static Double createDouble(String str) {
        return NumberUtils.createDouble(str);
    }  
    /**
     * 把指定的字符串转换成Integer
     * @param String 
     * @return Integer
     */
    public static Integer createInteger(String str) {
        return NumberUtils.createInteger(str);
    }   
    /**
	 * 按指定的格式将数值型数据转化为字符型 格式必须是 0.00几位小数就写几个0
	 * @param num
	 * @param pattern
	 * @return String
	 */
	public static String format(Double num, String pattern) {

		Double dbl = new Double(0.0);
		dbl = (num == null ? dbl : num);
		DecimalFormat c = new DecimalFormat(pattern);
		return c.format(dbl.doubleValue());
	}
	/**
	 * 按指定的格式将数值型数据转化为字符型 格式必须是 0.00几位小数就写几个0
	 * @param num
	 * @param pattern
	 * @return String
	 */
	public static String format(String num, String pattern) {		
		DecimalFormat c = new DecimalFormat(pattern);
		return c.format(NumberUtil.strToDouble(num));
	}
	
	public static void main(String[] args) {
	
	}
}
