package evolution.dto;

import evolution.Alias;

public class TargetDto {
	@Alias("name")
	private String nickName;
	private String myGender;
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getMyGender() {
		return myGender;
	}
	public void setMyGender(String myGender) {
		this.myGender = myGender;
	}
}
