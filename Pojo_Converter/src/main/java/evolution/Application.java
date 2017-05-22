package evolution;

import java.text.SimpleDateFormat;

import evolution.dto.AnotherDto;
import evolution.dto.AnyDto;

public class Application {
	public static void main(String[] args) throws Exception {
		AnyDto anyDto = new AnyDto();
		anyDto.setName("Chen");
		anyDto.setDate("2017-05-22");
		anyDto.setDecimal("3.14");
		anyDto.setInteger(3);
		Converter c = new Converter(new SimpleDateFormat("yyyy-MM-dd"));
		AnotherDto anotherDto = c.convert(anyDto, AnotherDto.class);
		System.out.println(anotherDto);
	}
}
