package evolution;

import java.text.SimpleDateFormat;
import java.util.Date;

import evolution.dto.AlphaDto;
import evolution.dto.AnotherDto;
import evolution.dto.AnyDto;
import evolution.dto.BetaDto;

public class Application {
	public static void main(String[] args) throws Exception {
//		// Alias on Target
//		AnyDto anyDto = new AnyDto();
//		anyDto.setName("Chen");
//		anyDto.setDate("2017-05-22");
//		anyDto.setDecimal("3.14");
//		anyDto.setInteger(3);
//		PojoConverter c = new PojoConverter(new SimpleDateFormat("yyyy-MM-dd"));
//		AnotherDto anotherDto = c.targetAliasConvert(anyDto, AnotherDto.class);
//		System.out.println(anotherDto);
//		// Alias on Source
//		AlphaDto alphaDto = new AlphaDto();
//		alphaDto.setName("Chen");
//		alphaDto.setGender("M");
//		BetaDto betaDto = c.sourceAliasConvert(alphaDto, BetaDto.class);
//		System.out.println(betaDto);
//		// Merge
//		AnyDto anyDto0 = new AnyDto();
//		anyDto0.setDate(new Date().toString());
//		AnyDto anyDto1 = new AnyDto();
//		anyDto1.setDecimal("3.14");
//		c.merge(anyDto0, anyDto1);
//		System.out.println(anyDto1);
		// Merge with Annotation
		PojoConverter c = new PojoConverter(new SimpleDateFormat("yyyy-MM-dd"));
		AlphaDto alpha = new AlphaDto();
		alpha.setName("Chen");
		BetaDto beta = new BetaDto();
		c.merge(alpha, beta);
		System.out.println(beta);
	}
}
