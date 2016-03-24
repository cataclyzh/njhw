package com.cosmosource.app.port.model;

/**
 * @Description：
 * @Author：qyq
 * @Date：2013-4-10
 * @param tagNameKeyword
 *            标签名称关键字
 * @param tagGroupIdArray
 *            所在标签分组
 * @param mapId
 *            地图ID
 * @param locatingOnly
 *            是否只获取处于定位的标签
 * @param absentOnly
 *            是否只获取处于消失状态的标签
 * @param lowerBatteryOnly
 *            是否只获取处于低电状态的标签
 * @param areaEventOnly
 *            是否只获取处于区域报警的标签
 * @param buttonPressedOnly
 *            是否只获取处于按钮报警的标签
 * @param wristletBrokenOnly
 *            是否只获取处于腕带报警的标签
 * @param sortField
 *            排序字段
 * @param sortDirection
 *            排序方向，0顺序，1倒序
 * @param fetchCount
 *            要获得的记录数
 * @param skipOffset
 *            跳过前面记录数 (用于分页)
 * @return
 * 
 * 
 */
public class WirelessLocation {

	private String tagNameKeyword;
	private int[] tagGroupIdArray;
	private int mapId;
	private Boolean locatingOnly;
	private Boolean absentOnly;
	private Boolean lowerBatteryOnly;
	private Boolean areaEventOnly;
	private Boolean buttonPressedOnly;
	private Boolean wristletBrokenOnly;
	private String sortField;
	private int sortDirection;
	private int fetchCount;
	private int skipOffset;

	public WirelessLocation() {

	}

	public WirelessLocation(String tagNameKeyword, int[] tagGroupIdArray,
	        int mapId, Boolean locatingOnly, Boolean absentOnly,
			Boolean lowerBatteryOnly, Boolean areaEventOnly,
			Boolean buttonPressedOnly, Boolean wristletBrokenOnly,
			String sortField, int sortDirection, int fetchCount, int skipOffset) {
		
		      this.tagNameKeyword = tagNameKeyword;
		      this.tagGroupIdArray = tagGroupIdArray;
		      this.mapId = mapId;
		      this.locatingOnly = locatingOnly;
		      this.absentOnly = absentOnly;
		      this.lowerBatteryOnly = lowerBatteryOnly;
		      this.areaEventOnly = areaEventOnly;
		      this.buttonPressedOnly = buttonPressedOnly;
		      this.wristletBrokenOnly = wristletBrokenOnly;
		      this.sortField = sortField;
		      this.sortDirection = sortDirection;
		      this.fetchCount = fetchCount;
		      this.skipOffset = skipOffset;
		    
	}

	public String getTagNameKeyword() {
		return tagNameKeyword;
	}

	public void setTagNameKeyword(String tagNameKeyword) {
		this.tagNameKeyword = tagNameKeyword;
	}

	public int[] getTagGroupIdArray() {
		return tagGroupIdArray;
	}

	public void setTagGroupIdArray(int[] tagGroupIdArray) {
		this.tagGroupIdArray = tagGroupIdArray;
	}

	public int getMapId() {
		return mapId;
	}

	public void setMapId(int mapId) {
		this.mapId = mapId;
	}

	public Boolean getLocatingOnly() {
		return locatingOnly;
	}

	public void setLocatingOnly(Boolean locatingOnly) {
		this.locatingOnly = locatingOnly;
	}

	public Boolean getAbsentOnly() {
		return absentOnly;
	}

	public void setAbsentOnly(Boolean absentOnly) {
		this.absentOnly = absentOnly;
	}

	public Boolean getLowerBatteryOnly() {
		return lowerBatteryOnly;
	}

	public void setLowerBatteryOnly(Boolean lowerBatteryOnly) {
		this.lowerBatteryOnly = lowerBatteryOnly;
	}

	public Boolean getAreaEventOnly() {
		return areaEventOnly;
	}

	public void setAreaEventOnly(Boolean areaEventOnly) {
		this.areaEventOnly = areaEventOnly;
	}

	public Boolean getButtonPressedOnly() {
		return buttonPressedOnly;
	}

	public void setButtonPressedOnly(Boolean buttonPressedOnly) {
		this.buttonPressedOnly = buttonPressedOnly;
	}

	public Boolean getWristletBrokenOnly() {
		return wristletBrokenOnly;
	}

	public void setWristletBrokenOnly(Boolean wristletBrokenOnly) {
		this.wristletBrokenOnly = wristletBrokenOnly;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public int getSortDirection() {
		return sortDirection;
	}

	public void setSortDirection(int sortDirection) {
		this.sortDirection = sortDirection;
	}

	public int getFetchCount() {
		return fetchCount;
	}

	public void setFetchCount(int fetchCount) {
		this.fetchCount = fetchCount;
	}

	public int getSkipOffset() {
		return skipOffset;
	}

	public void setSkipOffset(int skipOffset) {
		this.skipOffset = skipOffset;
	}

}
