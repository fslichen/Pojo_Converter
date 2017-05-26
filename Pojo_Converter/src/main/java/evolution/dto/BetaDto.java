package evolution.dto;

public class BetaDto {
	private String nickName;
	private String gender;
	@Override
	public String toString() {
		return "BetaDto [nickName=" + nickName + ", gender=" + gender + "]";
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
}
