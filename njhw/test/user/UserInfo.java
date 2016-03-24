package user;

public class UserInfo {
	private String name;
	private String orgName;
	private String citizenNo;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getCitizenNo() {
		return citizenNo;
	}
	public void setCitizenNo(String citizenNo) {
		this.citizenNo = citizenNo;
	}
	public String toString(){
		return name + " | " + orgName + " | " + citizenNo; 
	}
}
