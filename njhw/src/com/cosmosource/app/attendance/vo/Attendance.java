package com.cosmosource.app.attendance.vo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


import com.cosmosource.app.attendance.util.Arith;


/**
 * 考勤统计 VO 类
 * @author Polly
 *
 */
@SuppressWarnings("rawtypes")
public class Attendance implements Comparator{
	
	/**
	 * {AFTERNOON_BUSINESS_LEAVE=6, AFTERNOON_SICK_AFFAIR_LEAVE=0, 
	 * MORNING_BUSINESS_LEAVE=5, AFTERNOON_LATE_LEAVE_EARLY=309,
	 * ORG_NAME=南京市信息中心, MORNING_SICK_AFFAIR_LEAVE=0, AFTERNOON_NORMAL=504,
	 *  AFTERNOON_ABSENT=1152, MORNING_NORMAL=435, MORNING_ABSENT=1180, 
	 *  MORNING_LATE_LEAVE_EARLY=354, MORNING_OTHER=0, AFTERNOON_OTHER=0}
	 */
	
	//单位、部门名称
	private String orgName;
	//出勤、公出
	private int normalCount;
	//考勤总数
	private int totalCount;
	//病假、事假
	private int sickAffairLeaveCount;
	//缺勤
	private int absentCount;
	//迟到、早退
	private int lateLeaveEarlyCount;
	//其它
	private int otherCount;
	
	
	
	public Attendance() {
		super();
	}

	public Attendance(String orgName, int normalCount, int totalCount,
			int sickAffairLeaveCount, int absentCount, int lateLeaveEarlyCount,
			int otherCount) {
		super();
		this.orgName = orgName;
		this.normalCount = normalCount;
		this.totalCount = totalCount;
		this.sickAffairLeaveCount = sickAffairLeaveCount;
		this.absentCount = absentCount;
		this.lateLeaveEarlyCount = lateLeaveEarlyCount;
		this.otherCount = otherCount;
	}

	@Override
	public int compare(Object o1,Object o2){
		Attendance a =  (Attendance) o1;
		Attendance b =  (Attendance) o2;
		
		//首先按照正常出勤、公出比例由高到低排序
		double percentageA = Arith.div(a.normalCount,a.totalCount, 2);
		double percentageB = Arith.div(b.normalCount,b.totalCount, 2);
		
		if (percentageA == percentageB) {
			//如果比例一样则迟到、早退比例由高到低排序
			
			int result = String.valueOf(Arith.div(a.lateLeaveEarlyCount,a.totalCount, 2)).compareTo(
					String.valueOf(Arith.div(b.lateLeaveEarlyCount,b.totalCount, 2)));
			
			if (result >= 0) {
				return -1 ;
			}else{
				return 1;
			}
		}
		
		double result = Arith.sub(percentageA, percentageB);
		
		if (result >= 0) {
			return -1 ;
		}else{
			return 1;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		/**
		 * 		this.orgName = orgName;
		this.normalCount = normalCount;
		this.totalCount = totalCount;
		this.sickAffairLeaveCount = sickAffairLeaveCount;
		this.absentCount = absentCount;
		this.lateLeaveEarlyCount = lateLeaveEarlyCount;
		this.otherCount = otherCount;
		 */
		List list  = new ArrayList<Attendance>();
		list.add(new Attendance("发改委", 101, 2000, 99, 45, 66, 77));
		list.add(new Attendance("信息中心", 220, 550, 100, 66, 45, 76));
		list.add(new Attendance("管理局", 220,550,66,55,1,88));
		list.add(new Attendance("农委", 101,1999,100,34,65,88));
		list.add(new Attendance("国资", 99,200,54,12,0,0));
		
		Collections.sort(list, new Attendance());
		
		for (Object object : list) {
			Attendance a = (Attendance) object;
			System.out.println(a);
		}
	}
	
	

	@Override
	public String toString() {
		return "Attendance [orgName=" + orgName + ", normalCount="
				+ normalCount + ", totalCount=" + totalCount
				+ ", sickAffairLeaveCount=" + sickAffairLeaveCount
				+ ", absentCount=" + absentCount + ", lateLeaveEarlyCount="
				+ lateLeaveEarlyCount + ", otherCount=" + otherCount + "]";
	}


	public String getOrgName() {
		return orgName;
	}


	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}


	public int getNormalCount() {
		return normalCount;
	}


	public void setNormalCount(int normalCount) {
		this.normalCount = normalCount;
	}


	public int getTotalCount() {
		return totalCount;
	}


	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}


	public int getSickAffairLeaveCount() {
		return sickAffairLeaveCount;
	}


	public void setSickAffairLeaveCount(int sickAffairLeaveCount) {
		this.sickAffairLeaveCount = sickAffairLeaveCount;
	}


	public int getAbsentCount() {
		return absentCount;
	}


	public void setAbsentCount(int absentCount) {
		this.absentCount = absentCount;
	}


	public int getLateLeaveEarlyCount() {
		return lateLeaveEarlyCount;
	}


	public void setLateLeaveEarlyCount(int lateLeaveEarlyCount) {
		this.lateLeaveEarlyCount = lateLeaveEarlyCount;
	}


	public int getOtherCount() {
		return otherCount;
	}


	public void setOtherCount(int otherCount) {
		this.otherCount = otherCount;
	}

}
