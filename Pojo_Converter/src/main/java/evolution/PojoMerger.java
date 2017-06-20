package evolution;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import evolution.annotation.Alias;
import evolution.pojo.FieldPojo;

public class PojoMerger {
	private SimpleDateFormat simpleDateFormat;
	
	public PojoMerger() {
		
	}
	
	public PojoMerger(SimpleDateFormat simpleDateFormat) {
		this.simpleDateFormat = simpleDateFormat;
	}
	
	public Field sourceFieldByTargetField(Field targetField, Map<String, Field> sourceFieldMap) {
		return sourceFieldMap.get(targetField.getName());
	}
	
	public Field sourceFieldByTargetAliasField(Field targetField, Map<String, Field> sourceFieldMap) {
		Alias alias = targetField.getDeclaredAnnotation(Alias.class);
		return alias == null ? null : sourceFieldMap.get(alias.value());
	}
	
	public void set(Field sourceField, Object sourceObject, Field targetField, Object targetObject) throws Exception {
		if (sourceField == null || targetField == null) {
			return;
		}
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
				} else if (targetFieldClass == long.class || targetFieldClass == Long.class) {
					revisedSourceFieldObject = new Long(sourceFieldObjectInString);
				} else if (targetFieldClass == boolean.class || targetFieldClass == Boolean.class) {
					revisedSourceFieldObject = new Boolean(sourceFieldObjectInString);
				} else if (targetFieldClass == float.class || targetFieldClass == Float.class) {
					revisedSourceFieldObject = new Float(sourceFieldObjectInString);
				} else if (targetFieldClass == short.class || targetFieldClass == Short.class) {
					revisedSourceFieldObject = new Short(sourceFieldObjectInString);
				} else if (targetFieldClass == byte.class || targetFieldClass == Byte.class) {
					revisedSourceFieldObject = new Byte(sourceFieldObjectInString);
				} else if (targetFieldClass == char.class || targetFieldClass == Character.class) {
					revisedSourceFieldObject = new Character(sourceFieldObjectInString.charAt(0));
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
		}
	}
	
	public void merge(Object sourceObject, Object targetObject) {
		FieldPojo fieldPojo = fieldAndAliasFieldMap(sourceObject.getClass().getDeclaredFields());
		Map<String, Field> sourceFieldMap = fieldPojo.getFields();
		Map<String, Field> sourceAliasFieldMap = fieldPojo.getAliasFields();
		Field[] targetFields = targetObject.getClass().getDeclaredFields();
		for (Field targetField : targetFields) {
			try {
				targetField.setAccessible(true);
				if (targetField.get(targetObject) == null) {// Non-Invasive Merge
					// Field name matches field name.
					Field sourceField = sourceFieldByTargetField(targetField, sourceFieldMap);
					if (sourceField == null) {
						sourceField = sourceFieldByTargetAliasField(targetField, sourceFieldMap);
						if (sourceField == null) {
							sourceField = sourceFieldByTargetField(targetField, sourceAliasFieldMap);
							if (sourceField == null) {
								sourceField = sourceFieldByTargetAliasField(targetField, sourceAliasFieldMap);
							}
						}
					}
					set(sourceField, sourceObject, targetField, targetObject);
				}
			} catch (Exception e) {}
		}
	}
	
	public FieldPojo fieldAndAliasFieldMap(Field[] fields) {
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
		FieldPojo fieldPojo = new FieldPojo();
		fieldPojo.setFields(fieldMap);
		fieldPojo.setAliasFields(aliasFieldMap);
		return fieldPojo;
	}
}
