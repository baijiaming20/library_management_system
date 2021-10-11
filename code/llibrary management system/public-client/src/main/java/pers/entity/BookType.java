package pers.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BookType {
	private int type_id;
	private String type_name;

	public static final String idName = "type_id",
			typeName = "type_name";

	public static final String[] originKeys = {
			"type_id",
			"type_name"
	};
}
