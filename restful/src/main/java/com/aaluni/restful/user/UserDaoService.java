package com.aaluni.restful.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class UserDaoService {
	
	private static List<User> users = new ArrayList<>();
	private static int userCount = 3;

	static {
		users.add(new User(1, "Arindam", new Date()));
		users.add(new User(2, "Jayita", new Date()));
		users.add(new User(3, "Migu", new Date()));
		
	}
	
	public List<User> findAll(){
		return users;
	}
	
	public User save(User user) {
		++userCount;
		if (user.getId() == null) {
			user.setId(userCount);
			
		}	
		users.add(user);
		return user;
	}
	
	public User findById(int id) {
		
		return users.stream()
				.filter(user-> user.getId().equals(id))
				.findFirst()
				.orElse(null);
	}
	
	public User deleteUser(int id) {
		
		Iterator<User> iter = users.iterator();
		while (iter.hasNext()) {
			User usr = iter.next();
			if (usr.getId() == id) {
				iter.remove();
				return usr;
			}
		}
		return null;
	}
	


}
