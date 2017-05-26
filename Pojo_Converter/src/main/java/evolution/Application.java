package evolution;

import java.text.SimpleDateFormat;

import evolution.dto.AlphaDto;
import evolution.dto.AnotherDto;
import evolution.dto.AnyDto;
import evolution.dto.BetaDto;

public class Application {
	public static void main(String[] args) throws Exception {
		// Alias on Target
		AnyDto anyDto = new AnyDto();
		anyDto.setName("Chen");
		anyDto.setDate("2017-05-22");
		anyDto.setDecimal("3.14");
		anyDto.setInteger(3);
		PojoConverter c = new PojoConverter(new SimpleDateFormat("yyyy-MM-dd"));
		AnotherDto anotherDto = c.targetAliasConvert(anyDto, AnotherDto.class);
		System.out.println(anotherDto);
		// Alias on Source
		AlphaDto alphaDto = new AlphaDto();
		alphaDto.setName("Chen");
		alphaDto.setGender("M");
		BetaDto betaDto = c.sourceAliasConvert(alphaDto, BetaDto.class);
		System.out.println(betaDto);
	}
}
