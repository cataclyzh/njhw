package com.cosmosource.base.util;

import java.io.UnsupportedEncodingException;

import org.apache.struts.util.ResponseUtils;

import com.sun.tools.xjc.reader.xmlschema.bindinfo.BIConversion.Static;



/**
 * String处理工具类
 * @author WXJ	 
 * 
 **/
public class StringUtil  extends org.apache.commons.lang.StringUtils{

    public static void main(String[] args) {
    	System.out.println(StringUtil.formatRMB(new Double(33333)));
    	System.out.println(StringUtil.SplitWithChar("8478ACEDE6A2", 2, "-"));
	}
    
	/**
	 *  nullTo - 把null转化为指定的字符串
	 */
	public static String nullTo(Object s, String replace) {
		return s == null ? replace : s.toString();
	}

	/**
	 *  nullToEmpty - 把null转化为空字符串
	 */
	public static String nullToEmpty(Object s) {
		return nullTo(s, "");
	}
	
	/*
	 * empty2 null
	 */
	public static String empty2Null(String s)
	{
		if (isEmpty(s))
		{
			return null;
		}
		else
		{
			return s;
		}
	}
	
	/**
	 * filter - 对字符串进行html编码，接受null值 
	 */
	public static String filter(Object obj) {
		return ResponseUtils.filter(nullToEmpty(obj));
	}
	
	/**
	 * filter - 对字符串进行html编码，接受null值

	 * 返回值用&nbsp;代替空字符串 
	 */
	public static String htmlFilter(Object obj) {
		String ret = filter(obj);
		if ("".equals(ret)) ret = "&nbsp;";
		return ret;
	}

	/**
	 * left - 返回字符串指定位数的左边字符串，一个中文算两位
	 */
	public static String left(String s, int n) {
		if (s == null) {
			return null;
		} else if (n < 1) {
			return "";
		} else {
			StringBuilder buf = new StringBuilder();
			int len = 0;
			int length = s.length();

			for (int i = 0; i < length; i++) {
				String ch = s.substring(i, i + 1);
				byte[] b;
				try {
					b = ch.getBytes("GBK"); //GBK编码中文会返回两个字节

				} catch (UnsupportedEncodingException e) {
					return null;
				}

				len += b.length;

				if (len > n) {
					break;
				} else {
					buf.append(ch);
				}
			}

			return buf.toString();
		}

	}

	/**
	 * left - 返回字符串指定位数的左边字符串，一个中文算两位
	 */
	public static String right(String s, int n) {
		if (s == null) {
			return null;
		} else if (n < 1) {
			return "";
		} else {
			String buf = new String();
			int len = 0;
			int length = s.length();
	
			for (int i = length; i > 0; i--) {
				String ch = s.substring(i - 1, i);
				byte[] b;
				try {
					b = ch.getBytes("GBK"); //GBK编码中文会返回两个字节

				} catch (UnsupportedEncodingException e) {
					return null;
				}	
				len += b.length;	
				if (len > n) {
					break;
				} else {
					buf = ch + buf;
				}
			}
	
			return buf;
		}
	
	}
	 
	/**
	 * @描述: 转半角的函数(DBC case)
	 * 全角空格为12288，半角空格为32
	 * 其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
	 */
	public static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}
	/**
	 * @描述: 半角转全角
	 * @param input
	 * @return
	 */
	public static String ToSBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 32) {
				c[i] = (char) 12288;
				continue;
			}
			if (c[i] < 127)
				c[i] = (char) (c[i] + 65248);
		}
		return new String(c);
	}
	private static int Minimum(int a, int b, int c) {
		int mi;

		mi = a;
		if (b < mi) {
			mi = b;
		}
		if (c < mi) {
			mi = c;
		}
		return mi;

	}

	public static int LD(String s, String t) {
		int d[][]; // matrix
		int n; // length of s
		int m; // length of t
		int i; // iterates through s
		int j; // iterates through t
		char s_i; // ith character of s
		char t_j; // jth character of t
		int cost; // cost
		// Step 1

		n = s.length();
		m = t.length();
		if (n == 0) {
			return m;
		}
		if (m == 0) {
			return n;
		}
		d = new int[n + 1][m + 1];

		// Step 2

		for (i = 0; i <= n; i++) {
			d[i][0] = i;
		}

		for (j = 0; j <= m; j++) {
			d[0][j] = j;
		}
		// Step 3
		for (i = 1; i <= n; i++) {
			s_i = s.charAt(i - 1);
			// Step 4
			for (j = 1; j <= m; j++) {
				t_j = t.charAt(j - 1);
				// Step 5
				if (s_i == t_j) {
					cost = 0;
				} else {
					cost = 1;
				}
				// Step 6
				d[i][j] = Minimum(d[i - 1][j] + 1, d[i][j - 1] + 1,
						d[i - 1][j - 1] + cost);
			}
		}
		// Step 7
		return d[n][m];

	}
	
	public static String SplitWithChar(String input,int len,String t) {
		if(input == null){
			return "";
		}
		char[] c = input.toCharArray();
		int length = c.length;
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < length; i++) {
			s.append(c[i]);
			if((i+1) % len == 0&&(i+1)<length){
				s.append(t);
			}
		}
		return s.toString();
	}
	
	public static String formatRMB(Double data) {
		RmbCapitalFormatter formatter = new RmbCapitalFormatter();
		return formatter.format(data);
		
	}

}
