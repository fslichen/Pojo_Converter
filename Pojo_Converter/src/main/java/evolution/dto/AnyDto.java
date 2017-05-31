package evolution.dto;

public class AnyDto {
	private String name;
	private String date;
	private String decimal;
	private int integer;

	public String getDecimal() {
		return decimal;
	}

	public void setDecimal(String decimal) {
		this.decimal = decimal;
	}

	public int getInteger() {
		return integer;
	}

	public void setInteger(int integer) {
		this.integer = integer;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "AnyDto [name=" + name + ", date=" + date + ", decimal=" + decimal + ", integer=" + integer + "]";
	}
}
