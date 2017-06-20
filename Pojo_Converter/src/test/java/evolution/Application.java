package evolution;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import evolution.pojo.AlphaDto;
import evolution.pojo.BetaDto;
import evolution.pojo.SourceDto;

public class Application {
	@Test
	public void test() throws Exception {
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
		alpha.setDecimal("3.14");
		alpha.setGender("M");
		alpha.setMyHobby("Badminton");
		alpha.setA(new Date());
		alpha.setNumber(4);
		SourceDto sourceDto = new SourceDto();
		sourceDto.setName("Ling");
		sourceDto.setGender("F");
		alpha.setSourceDto(sourceDto);
		BetaDto beta = new BetaDto();
		c.merge(alpha, beta);
		System.out.println(beta);
	}
}
