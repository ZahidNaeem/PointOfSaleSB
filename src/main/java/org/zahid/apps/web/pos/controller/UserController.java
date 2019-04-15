package org.zahid.apps.web.pos.controller;

//import org.ocpsoft.rewrite.annotation.Join;
//import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zahid.apps.web.pos.entity.User;
import org.zahid.apps.web.pos.service.UserService;

@Scope(value = "session")
@Component(value = "userController")
//@ELBeanName(value = "userController")
//@Join(path = "/user", to = "views/user-form.jsf")
public class UserController {
	@Autowired
	private UserService userService;

	private User user = new User();

	public String save() {
		userService.save(user);
		user = new User();
		return "user-list.xhtml?faces-redirect=true";
	}

	public User getUser() {
		return user;
	}

}
