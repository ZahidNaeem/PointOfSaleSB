package org.zahid.apps.web.pos.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zahid.apps.web.pos.entity.Groupmember;

public interface GroupmemberRepo extends JpaRepository<Groupmember, Long> {

}
