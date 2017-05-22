package evolution;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class Converter {
	private SimpleDateFormat simpleDateFormat;
	
	public Converter() {
		
	}
	
	public Converter(SimpleDateFormat simpleDateFormat) {
		this.simpleDateFormat = simpleDateFormat;
	}
	
	public <T> T convert(Object sourceObject, Class<T> targetClass) throws Exception {
		Map<String, Field> targetFieldMap = new LinkedHashMap<>();
		Map<String, Field> targetAliasFieldMap = new LinkedHashMap<>();
		Field[] targetFields = targetClass.getDeclaredFields();
		for (Field targetField : targetFields) {
			targetField.setAccessible(true);
			targetFieldMap.put(targetField.getName(), targetField);
			Alias alias = targetField.getAnnotation(Alias.class);
			if (alias != null) {
				targetAliasFieldMap.put(alias.value(), targetField);
			}
		}
		T targetObject = targetClass.newInstance();
		Field[] sourceFields = sourceObject.getClass().getDeclaredFields();
		for (Field sourceField : sourceFields) {
			sourceField.setAccessible(true);
			String sourceFieldName = sourceField.getName();
			Field targetField = targetFieldMap.get(sourceFieldName);
			if (targetField == null) {
				targetField = targetAliasFieldMap.get(sourceFieldName);
			}
			if (targetField != null) {
				if (sourceField.getType() == targetField.getType()) {
					targetField.set(targetObject, sourceField.get(sourceObject));
				} else if (sourceField.getType() == String.class) {
					Class<?> targetFieldClass = targetField.getType();
					if (targetFieldClass == Date.class) {
						targetField.set(targetObject, simpleDateFormat.parse(sourceField.get(sourceObject).toString()));
					} else if (targetFieldClass == Double.class || targetFieldClass == double.class) {
						targetField.set(targetObject, new Double(sourceField.get(sourceObject).toString()));
					} else if (targetFieldClass == Integer.class || targetFieldClass == int.class) {
						targetField.set(targetObject, new Integer(sourceField.get(sourceObject).toString()));
					}
				} else if (targetField.getType() == String.class) {
					targetField.set(targetObject, sourceField.get(sourceObject).toString());
				}
			}
		}
		return targetObject;
	}
}
