package evolution.pojo;

import evolution.annotation.Alias;

public class SourceDto {
	@Override
	public String toString() {
		return "SourceDto [name=" + name + ", gender=" + gender + "]";
	}
	private String name;
	@Alias("myGender")
	private String gender;
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
