package evolution.dto;

import java.util.Date;

import evolution.Alias;

public class AlphaDto {
	@Alias("nickName")
	private String name;
	private String gender;
	private String decimal;
	private String myHobby;
	@Alias("date")
	private Date a;
	public Date getA() {
		return a;
	}
	public void setA(Date a) {
		this.a = a;
	}
	public String getDecimal() {
		return decimal;
	}
	public String getGender() {
		return gender;
	}
	public String getMyHobby() {
		return myHobby;
	}
	public String getName() {
		return name;
	}
	public void setDecimal(String decimal) {
		this.decimal = decimal;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public void setMyHobby(String myHobby) {
		this.myHobby = myHobby;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "AlphaDto [name=" + name + ", gender=" + gender + ", decimal=" + decimal + ", myHobby=" + myHobby
				+ ", a=" + a + "]";
	}
}
