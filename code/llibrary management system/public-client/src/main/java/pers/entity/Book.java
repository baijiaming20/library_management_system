package pers.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Book {
	private int book_id, book_type;
	private double book_price;
	private String book_name, book_author, book_publish, book_introduction;

	private String book_type_name;

	public static final String typeIdName = "book_type",
								typeNameName = "book_type_name";

	// 原始键名
	public static final String[] originKeys = {
			"book_id",
			"book_name",
			"book_author",
			"book_publish",
			"book_type",
			"book_price",
			"book_introduction"
	};
}
