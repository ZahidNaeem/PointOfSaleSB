package org.zahid.apps.web.pos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zahid.apps.web.pos.entity.User;
import org.zahid.apps.web.pos.service.UserService;

import javax.annotation.PostConstruct;
import java.util.List;

//import org.ocpsoft.rewrite.annotation.Join;
//import org.ocpsoft.rewrite.annotation.RequestAction;
//import org.ocpsoft.rewrite.el.ELBeanName;
//import org.ocpsoft.rewrite.faces.annotation.Deferred;
//import org.ocpsoft.rewrite.faces.annotation.IgnorePostback;

@Scope(value = "session")
@Component(value = "userList")
//@ELBeanName(value = "userList")
//@Join(path = "/", to = "views/user-list.jsf")
public class UserListController {

    @Autowired
    private UserService userService;
    private List<User> users;

//	@Deferred
//	@RequestAction
//	@IgnorePostback
    @PostConstruct
    public void loadData() {
        users = userService.getUsers();
    }

    public List<User> getUsers() {
        return users;
    }

}
