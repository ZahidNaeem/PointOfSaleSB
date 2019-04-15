package org.zahid.apps.web.pos.service;

import org.zahid.apps.web.pos.entity.User;

import java.util.List;

public interface UserService {
	public List<User> getUsers();

	public void save(User user);

}
