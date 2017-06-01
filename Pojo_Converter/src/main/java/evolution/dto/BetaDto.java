package evolution.dto;

import java.util.Date;

import evolution.Alias;

public class BetaDto {
	private String nickName;
	private String gender;
	private Double decimal;
	@Alias("myHobby")
	private String hobby;
	@Alias("date")
	private Date b;
	public Date getB() {
		return b;
	}
	public void setB(Date b) {
		this.b = b;
	}
	public Double getDecimal() {
		return decimal;
	}
	public String getGender() {
		return gender;
	}
	public String getHobby() {
		return hobby;
	}
	public String getNickName() {
		return nickName;
	}
	public void setDecimal(Double decimal) {
		this.decimal = decimal;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	@Override
	public String toString() {
		return "BetaDto [nickName=" + nickName + ", gender=" + gender + ", decimal=" + decimal + ", hobby=" + hobby
				+ ", b=" + b + "]";
	}
}
