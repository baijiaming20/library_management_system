package pers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.entity.*;
import pers.service.PublicDaoService;

@Configuration
public class Beans {
	/**
	 * admin表默认service
	 */
	@Bean("AdminPublicService")
	public PublicDaoService<Admin> adminService() {
		return new PublicDaoService<>("admin", "admin_id", Admin.class);
	}

	/**
	 * user表默认service
	 */
	@Bean("UserPublicService")
	public PublicDaoService<User> userService() {
		return new PublicDaoService<>("user", "user_id", User.class);
	}

	/**
	 * borrow表默认service
	 */
	@Bean("BorrowPublicService")
	public PublicDaoService<Borrow> borrowService() {
		return new PublicDaoService<>("borrow", "id", Borrow.class);
	}

	/**
	 * book表默认service
	 */
	@Bean("BookPublicService")
	public PublicDaoService<Book> bookService() {
		return new PublicDaoService<>("book", "book_id", Book.class);
	}

	/**
	 * book_type表默认service
	 */
	@Bean("BookTypePublicService")
	public PublicDaoService<BookType> bookTypeService() {
		return new PublicDaoService<>("book_type", "type_id", BookType.class);
	}
}
