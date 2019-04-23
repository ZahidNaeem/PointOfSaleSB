package org.zahid.apps.web.pos.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zahid.apps.web.pos.entity.Item;

import java.util.List;

@Repository
public interface ItemRepo extends JpaRepository<Item, Long> {
    public Long generateID();

    public List<String> getCategories();
}
