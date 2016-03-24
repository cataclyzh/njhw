package com.cosmosource.app.energyint.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class EIUtils {

	// 默认除法运算精度
	private static final int DEF_DIV_SCALE = 10;

	/**
	 * 
	 * 提供精确的加法运算。
	 * 
	 * @param v1
	 *            被加数
	 * 
	 * @param v2
	 *            加数
	 * 
	 * @return 两个参数的和
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.add(b2).doubleValue();
	}

	/**
	 * 
	 * 提供精确的加法运算。
	 * 
	 * @param v1
	 *            被加数
	 * 
	 * @param v2
	 *            加数
	 * 
	 * @return 两个参数的和
	 */
	public static double add(double v1, double v2, double v3) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		BigDecimal b3 = new BigDecimal(Double.toString(v3));

		return b1.add(b2).add(b3).doubleValue();
	}

	/**
	 * 
	 * 提供精确的减法运算。
	 * 
	 * @param v1
	 *            被减数
	 * 
	 * @param v2
	 *            减数
	 * 
	 * @return 两个参数的差
	 */

	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 
	 * 提供精确的乘法运算。
	 * 
	 * @param v1
	 *            被乘数
	 * 
	 * @param v2
	 *            乘数
	 * 
	 * @return 两个参数的积
	 */

	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
	 * 
	 * 定精度，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * 
	 * @param v2
	 *            除数
	 * 
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
	 * 
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}

		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
	 * 
	 * 小数点以后10位，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * 
	 * @param v2
	 *            除数
	 * 
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}

	/**
	 * 
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * 
	 * @param scale
	 *            小数点后保留几位
	 * 
	 * @return 四舍五入后的结果
	 */

	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}

		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");

		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 计算总能耗(Kwh)
	 */
	public static double calcTotalConsume(double water, double kwh, double flow) {
		double waterCoal = mul(water, EIConstrants.WATER_TO_STANDARD_COAL);
		double kwhCoal = mul(kwh, EIConstrants.POWER_TO_STANDARD_COAL);
		double flowCoal = mul(flow, EIConstrants.HEATING_POWER_TO_STANDARD_COAL);

		double totalCoal = add(waterCoal, kwhCoal, flowCoal);
		double totalKwh = div(totalCoal, EIConstrants.POWER_TO_STANDARD_COAL);

		return totalKwh;
	}

	/**
	 * 根据已经算出的各种能耗的标准煤计算总能耗（Kwh）
	 */
	public static double calcTotalConsumeWithCoal(double waterCoal,
			double kwhCoal, double flowCoal) {
		double totalCoal = add(waterCoal, kwhCoal, flowCoal);
		double totalKwh = div(totalCoal, EIConstrants.POWER_TO_STANDARD_COAL);

		return totalKwh;
	}
	
	/**
	 * 根据已经算出的各种能耗的标准煤计算总能耗（Kwh）
	 */
	public static double calcTotalConsumeWithCoalNew(double waterCoal,
			double kwhCoal, double flowCoal, double gasolinCoal, double doilCoal) {
		BigDecimal b1 = new BigDecimal(Double.toString(waterCoal));
		BigDecimal b2 = new BigDecimal(Double.toString(kwhCoal));
		BigDecimal b3 = new BigDecimal(Double.toString(flowCoal));
		BigDecimal b4 = new BigDecimal(Double.toString(gasolinCoal));
		BigDecimal b5 = new BigDecimal(Double.toString(doilCoal));

		double totalCoal = b1.add(b2).add(b3).add(b4).add(b5).doubleValue();
		double totalKwh = div(totalCoal, EIConstrants.POWER_TO_STANDARD_COAL);

		return totalKwh;
	}
	
	/**
	 * 格式化数据保留1位小数
	 * 
	 * @param d
	 * @return
	 */
	public static double doubleFormat(Object d) {
		return Double.parseDouble(new DecimalFormat("##0.0").format(d));
	}
}
