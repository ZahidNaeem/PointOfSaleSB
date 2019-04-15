package org.zahid.apps.web.pos.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zahid.apps.web.pos.entity.UserDetail;

import java.util.List;

public interface UserDetailRepo extends JpaRepository<UserDetail, Long> {
    public List<UserDetail> findByUName(String uName);

//    public UserDetail findUniqueUserByUName(String uName);
}
