package com.cosmosource.app.personnel.model;

public class PersonModel {
	
	private Long userid;			// 用户ID
	private String name;			// 用户名称
	private String loginUid; 		// 用户登陆名
	private String ucode;			// 用户编码
	private Long orgId;				// 部门
	private String residentNo;		// 身份证号
	private String sex;				// 性别
	private String phone; 			// 手机号
	private String email;			// Email
	private String userPhoto;		// 用户图片
	private Long isSystem;			// 是否系统管理员
	
	private String cityCardType; 	// 市民卡类型
	private String cityCard; 		// 市民卡
	private String cityCardStatus;	// 市民卡状态
	private String cardLosted;		// 卡是否挂失
	
	private String carNum;			// 车牌号
	private String isFastenCarNum;	// 是否固定车牌
	
	//private String ipTelNo;		// IP电话编号
	private Long ipTelId;			// IP电话ID
	private String ipTelNum;		// IP电话号码
	private String ipTelMac;		// IP电话MAC
	
	private String fax;				// 传真号
	private String fax_web;			// 网络传真号
	
	private Long roomId;			// 房间ID
	private String roomNum;			// 房间号
	
	private String cityCardold;      // 老市民卡号
	
	private String dRoomName;       //门锁权限name
	
	private String dRoomId;   		// 门锁权限id
	
	private String cardUID;          // 市民卡uid
	
	private String isTempUser;       // 人员类型
	private String dateFrom;         // 临时人员开始时间
	private String dateTo;           // 临时人员结束时间
	private String tmpCardStartDate; // 临时卡开始时间
	
	private String loginUidOld; 	// 老的登陆号 for ldap
	
	private Long stage; 		// 2014年4月2日16:49:28 for 考勤
	
	private int isAttendanceAdmin; // 1是,0不是
	
	private long PId;
	
	
	
	public long getPId() {
		return PId;
	}
	public void setPId(long pId) {
		PId = pId;
	}
	public int getIsAttendanceAdmin() {
		return isAttendanceAdmin;
	}
	public void setIsAttendanceAdmin(int isAttendanceAdmin) {
		this.isAttendanceAdmin = isAttendanceAdmin;
	}
	
	
	/** 
	 * loginUidOld 
	 * 
	 * @return the loginUidOld 
	 * @since   CodingExample Ver(编码范例查看) 1.0 
	 */
	
	public String getLoginUidOld()
	{
		return loginUidOld;
	}
	/** 
	 * @param loginUidOld the loginUidOld to set 
	 */
	
	public void setLoginUidOld(String loginUidOld)
	{
		this.loginUidOld = loginUidOld;
	}
	/** 
	 * cardUID 
	 * 
	 * @return the cardUID 
	 * @since   CodingExample Ver(编码范例查看) 1.0 
	 */
	
	public String getCardUID()
	{
		return cardUID;
	}
	/** 
	 * @param cardUID the cardUID to set 
	 */
	
	public void setCardUID(String cardUID)
	{
		this.cardUID = cardUID;
	}
	/** 
	 * cityCardold 
	 * 
	 * @return the cityCardold 
	 * @since   CodingExample Ver(编码范例查看) 1.0 
	 */
	
	public String getCityCardold()
	{
		return cityCardold;
	}
	/** 
	 * @param cityCardold the cityCardold to set 
	 */
	
	public void setCityCardold(String cityCardold)
	{
		this.cityCardold = cityCardold;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getResidentNo() {
		return residentNo;
	}
	public void setResidentNo(String residentNo) {
		this.residentNo = residentNo;
	}
	public String getCityCardType() {
		return cityCardType;
	}
	public void setCityCardType(String cityCardType) {
		this.cityCardType = cityCardType;
	}
	public String getCityCard() {
		return cityCard;
	}
	public void setCityCard(String cityCard) {
		this.cityCard = cityCard;
	}
	public String getCityCardStatus() {
		return cityCardStatus;
	}
	public void setCityCardStatus(String cityCardStatus) {
		this.cityCardStatus = cityCardStatus;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCarNum() {
		return carNum;
	}
	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}
	public String getIpTelNum() {
		return ipTelNum;
	}
	public void setIpTelNum(String ipTelNum) {
		this.ipTelNum = ipTelNum;
	}
	public String getIpTelMac() {
		return ipTelMac;
	}
	public void setIpTelMac(String ipTelMac) {
		this.ipTelMac = ipTelMac;
	}
	public Long getRoomId() {
		return roomId;
	}
	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}
	public String getRoomNum() {
		return roomNum;
	}
	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}
	public String getIsFastenCarNum() {
		return isFastenCarNum;
	}
	public void setIsFastenCarNum(String isFastenCarNum) {
		this.isFastenCarNum = isFastenCarNum;
	}
	public Long getIpTelId() {
		return ipTelId;
	}
	public void setIpTelId(Long ipTelId) {
		this.ipTelId = ipTelId;
	}
	public String getUserPhoto() {
		return userPhoto;
	}
	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}
	public Long getIsSystem() {
		return isSystem;
	}
	public void setIsSystem(Long isSystem) {
		this.isSystem = isSystem;
	}
	public String getCardLosted() {
		return cardLosted;
	}
	public void setCardLosted(String cardLosted) {
		this.cardLosted = cardLosted;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getLoginUid() {
		return loginUid;
	}
	public void setLoginUid(String loginUid) {
		this.loginUid = loginUid;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public String getUcode() {
		return ucode;
	}
	public void setUcode(String ucode) {
		this.ucode = ucode;
	}
	/** 
	 * fax_web 
	 * 
	 * @return the fax_web 
	 * @since   CodingExample Ver(编码范例查看) 1.0 
	 */
	
	public String getFax_web()
	{
		return fax_web;
	}
	/** 
	 * @param faxWeb the fax_web to set 
	 */
	
	public void setFax_web(String faxWeb)
	{
		fax_web = faxWeb;
	}
	public void setIsTempUser(String isTempUser) {
		this.isTempUser = isTempUser;
	}
	public String getIsTempUser() {
		return isTempUser;
	}
	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}
	public String getDateFrom() {
		return dateFrom;
	}
	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}
	public String getDateTo() {
		return dateTo;
	}
	public void setTmpCardStartDate(String tmpCardStartDate) {
		this.tmpCardStartDate = tmpCardStartDate;
	}
	public String getTmpCardStartDate() {
		return tmpCardStartDate;
	}
	public Long getStage() {
		return stage;
	}
	public void setStage(Long stage) {
		this.stage = stage;
	}
	
	
}
