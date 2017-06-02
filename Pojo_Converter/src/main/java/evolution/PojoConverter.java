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
	
	public Field fieldByMatchingFieldNameWithAlias(Class<?> clazz, Field currentField) {
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			Alias alias = field.getDeclaredAnnotation(Alias.class);
			if (alias != null && currentField.getName().equals(alias.value())) {
				return field;
			}
		}
		return null;
	}
	
	public Field sourceFieldByTargetField(Field currentField, Map<String, Field> sourceFieldMap) {
		return sourceFieldMap.get(currentField.getName());
	}
	
	public Field fieldByMatchingAliasWithFieldName(Class<?> clazz, Field currentField) {
		Alias alias = currentField.getDeclaredAnnotation(Alias.class);
		if (alias == null) {
			return null;
		}
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			if (field.getName().equals(alias.value())) {
				return field;
			}
		}
		return null;
	}
	
	public Field sourceFieldByTargetAliasField(Field currentField, Map<String, Field> sourceFieldMap) {
		Alias alias = currentField.getDeclaredAnnotation(Alias.class);
		if (alias == null) {
			return null;
		}
		return sourceFieldMap.get(alias.value());
	}
	
	public Field fieldByMatchingFieldNameWithFieldName(Class<?> clazz, Field currentField) {
		try {
			return clazz.getDeclaredField(currentField.getName());
		} catch (NoSuchFieldException | SecurityException e) {
			return null;
		}
	}
	
	public Field fieldByMatchingAliasWithAlias(Class<?> clazz, Field currentField) {
		Alias currentAlias = currentField.getDeclaredAnnotation(Alias.class);
		if (currentAlias == null) {
			return null;
		}
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			Alias alias = field.getDeclaredAnnotation(Alias.class);
			if (alias != null && alias.value().equals(currentAlias.value())) {
				return field;
			}
		}
		return null;
	}
	
	public boolean set(Field sourceField, Object sourceObject, Field targetField, Object targetObject) throws Exception {
		if (sourceField != null) {
			sourceField.setAccessible(true);
			Object sourceFieldObject = sourceField.get(sourceObject);
			if (sourceFieldObject != null) {
				Class<?> sourceFieldClass = sourceField.getType();
				Class<?> targetFieldClass = targetField.getType();
				if (sourceFieldClass == targetFieldClass) {
					targetField.set(targetObject, sourceFieldObject);
				} else if (sourceFieldClass == String.class) {
					Object revisedSourceFieldObject = null;
					String sourceFieldObjectInString = sourceFieldObject.toString();
					if (targetFieldClass == int.class || targetFieldClass == Integer.class) {
						revisedSourceFieldObject = new Integer(sourceFieldObjectInString);
					} else if (targetFieldClass == double.class || targetFieldClass == Double.class) {
						revisedSourceFieldObject = new Double(sourceFieldObjectInString);
					} else if (targetFieldClass == Date.class) {
						revisedSourceFieldObject = simpleDateFormat.parse(sourceFieldObjectInString);
					} 
					targetField.set(targetObject, revisedSourceFieldObject);
				} else if (targetFieldClass == String.class) {
					targetField.set(targetObject, sourceFieldObject.toString());
				} else {
					Object targetFieldObject = targetFieldClass.newInstance();
					merge(sourceFieldObject, targetFieldObject);
					targetField.set(targetObject, targetFieldObject);
				}
				return true;
			}
		}
		return false;
	}
	
	public void merge(Object sourceObject, Object targetObject) {
		Map<String, Map<String, Field>> sourceFieldAndAliasFieldMap = fieldAndAliasFieldMap(sourceObject.getClass().getDeclaredFields());
		Map<String, Field> sourceFieldMap = sourceFieldAndAliasFieldMap.get("fieldMap");
		Map<String, Field> sourceAliasFieldMap = sourceFieldAndAliasFieldMap.get("aliasFieldMap");
		Field[] fields = targetObject.getClass().getDeclaredFields();
		for (Field targetField : fields) {
			try {
				targetField.setAccessible(true);
				if (targetField.get(targetObject) == null) {// Ready to be Merged
					// Field name matches field name.
					Field sourceField = sourceFieldByTargetField(targetField, sourceFieldMap);
					if (set(sourceField, sourceObject, targetField, targetObject)) {
						continue;
					}
					// Alias matches field name.
					sourceField = sourceFieldByTargetAliasField(targetField, sourceFieldMap);
					if (set(sourceField, sourceObject, targetField, targetObject)) {
						continue;
					}
					// Field name matches alias.
					sourceField = sourceFieldByTargetField(targetField, sourceAliasFieldMap);
					if (set(sourceField, sourceObject, targetField, targetObject)) {
						continue;
					}
					// Alias matches alias.
					sourceField = sourceFieldByTargetAliasField(targetField, sourceAliasFieldMap);
					if (set(sourceField, sourceObject, targetField, targetObject)) {
						continue;
					}
				}
			} catch (Exception e) {}
		}
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
