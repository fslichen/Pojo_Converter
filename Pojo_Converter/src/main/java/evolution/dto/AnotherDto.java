package evolution.dto;

import java.util.Date;

import evolution.Alias;

public class AnotherDto {
	@Alias("name")
	private String nickName;
	private Date date;
	private double decimal;
	private String integer;
	
	public double getDecimal() {
		return decimal;
	}

	public void setDecimal(double decimal) {
		this.decimal = decimal;
	}

	public String getInteger() {
		return integer;
	}

	public void setInteger(String integer) {
		this.integer = integer;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "AnotherDto [nickName=" + nickName + ", date=" + date + ", decimal=" + decimal + ", integer=" + integer
				+ "]";
	}
}
