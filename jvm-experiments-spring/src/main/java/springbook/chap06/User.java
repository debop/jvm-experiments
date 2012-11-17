package springbook.chap06;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * springbook.chap05.User
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 17.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	String id;
	String name;
	String password;

	Level level;
	int login;
	int recommend;

}
