package model;
/**
 * @author admin
 * @version 1.0
 * @created 17-3-2016 ¿ÀÈÄ 3:09:34
 */
public class UserInfo {

	private String id;
	private String password;
	private String userType;
	private String address;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		System.out.println(userType);
		this.userType = userType;
	}
	public String getId() {return id;}
	public void setId(String id) {this.id = id;}

	public String getPassword() {return password;}
	public void setPassword(String password) {this.password = password;}


	public UserInfo(){

	}

	public void finalize() throws Throwable {

	}
}//end UserInfo