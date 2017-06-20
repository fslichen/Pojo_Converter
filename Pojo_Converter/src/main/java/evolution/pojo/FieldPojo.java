package evolution.pojo;

import java.lang.reflect.Field;
import java.util.Map;

public class FieldPojo {
	private Map<String, Field> fields;
	private Map<String, Field> aliasFields;
	public Map<String, Field> getFields() {
		return fields;
	}
	public void setFields(Map<String, Field> fields) {
		this.fields = fields;
	}
	public Map<String, Field> getAliasFields() {
		return aliasFields;
	}
	public void setAliasFields(Map<String, Field> aliasFields) {
		this.aliasFields = aliasFields;
	}
}
