package evolution.pojo;

import evolution.annotation.Alias;

public class TargetDto {
	@Override
	public String toString() {
		return "TargetDto [nickName=" + nickName + ", myGender=" + myGender + "]";
	}
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
