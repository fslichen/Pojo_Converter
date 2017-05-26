package evolution;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class PojoConverter {
	private SimpleDateFormat simpleDateFormat;
	
	public PojoConverter() {
		
	}
	
	public PojoConverter(SimpleDateFormat simpleDateFormat) {
		this.simpleDateFormat = simpleDateFormat;
	}
	
	public <T> T sourceAliasConvert(Object sourceObject, Class<T> targetClass) throws Exception {
		Field[] sourceFields = sourceObject.getClass().getDeclaredFields();
		Map<String, Map<String, Field>> fieldAndAliasFieldMap = fieldAndAliasFieldMap(sourceFields);
		Map<String, Field> sourceFieldMap = fieldAndAliasFieldMap.get("fieldMap");
		Map<String, Field> sourceAliasFieldMap = fieldAndAliasFieldMap.get("aliasFieldMap");
		Field[] targetFields = targetClass.getDeclaredFields();
		T targetObject = targetClass.newInstance();
		for (Field targetField : targetFields) {
			try {
				targetField.setAccessible(true);
				String targetFieldName = targetField.getName();
				Field sourceField = sourceFieldMap.get(targetFieldName);
				if (sourceField == null) {
					sourceField = sourceAliasFieldMap.get(targetFieldName);
				}
				if (sourceField != null) {
					copy(sourceField, sourceObject, targetField, targetObject);
				}
			} catch (Exception e) {}
		}
		return targetObject;
	}
	
	public void copy(Field sourceField, Object sourceObject, Field targetField, Object targetObject) throws Exception {
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
	
	public Map<String, Map<String, Field>> fieldAndAliasFieldMap(Field[] fields) {
		Map<String, Field> fieldMap = new HashMap<>();
		Map<String, Field> aliasFieldMap = new HashMap<>();
		for (Field field : fields) {
			field.setAccessible(true);
			fieldMap.put(field.getName(), field);
			Alias alias = field.getAnnotation(Alias.class);
			if (alias != null) {
				aliasFieldMap.put(alias.value(), field);
			}
		}
		Map<String, Map<String, Field>> result = new LinkedHashMap<>();
		result.put("fieldMap", fieldMap);
		result.put("aliasFieldMap", aliasFieldMap);
		return result;
	}
	
	public <T> T targetAliasConvert(Object sourceObject, Class<T> targetClass) throws Exception {
		Field[] targetFields = targetClass.getDeclaredFields();
		Map<String, Map<String, Field>> fieldAndAliasFieldMap = fieldAndAliasFieldMap(targetFields);
		Map<String, Field> targetFieldMap = fieldAndAliasFieldMap.get("fieldMap");
		Map<String, Field> targetAliasFieldMap = fieldAndAliasFieldMap.get("aliasFieldMap");
		T targetObject = targetClass.newInstance();
		Field[] sourceFields = sourceObject.getClass().getDeclaredFields();
		for (Field sourceField : sourceFields) {
			try {
				sourceField.setAccessible(true);
				String sourceFieldName = sourceField.getName();
				Field targetField = targetFieldMap.get(sourceFieldName);
				if (targetField == null) {
					targetField = targetAliasFieldMap.get(sourceFieldName);
				}
				if (targetField != null) {
					copy(sourceField, sourceObject, targetField, targetObject);
				}
			} catch (Exception e) {}
		}
		return targetObject;
	}
}
