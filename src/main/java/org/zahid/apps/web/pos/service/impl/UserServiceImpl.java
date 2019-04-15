package org.zahid.apps.web.pos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zahid.apps.web.pos.entity.User;
import org.zahid.apps.web.pos.repo.UserRepo;
import org.zahid.apps.web.pos.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Override
	public List<User> getUsers() {
		return userRepo.findAll();
	}

	@Override
	public void save(User user) {
		userRepo.save(user);

	}

}
