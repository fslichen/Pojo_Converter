package evolution.dto;

import evolution.Alias;

public class AlphaDto {
	@Alias("nickName")
	private String name;
	private String gender;
	@Override
	public String toString() {
		return "AlphaDto [name=" + name + ", gender=" + gender + "]";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
}
