package pers.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Borrow {
	// user_id、user_name、book_name、date、return_date

	private int id, user_id, book_id;
	private String date, return_date;

	private String user_name, book_name;

	public static final String userName = "user_name",
			bookName = "book_name",
			userIdName = "user_id",
			bookIdName = "book_id";

	// 原始键名
	public static final String[] originKeys = {
			"id",
			"user_id",
			"book_id",
			"date",
			"return_date"
	};
}
