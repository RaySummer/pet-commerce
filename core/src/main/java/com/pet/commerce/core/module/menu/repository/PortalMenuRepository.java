package com.pet.commerce.core.module.menu.repository;

import com.pet.commerce.core.module.base.repository.BaseRepository;
import com.pet.commerce.core.module.menu.model.PortalMenu;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Ray
 * @since 2023/2/20
 */
public interface PortalMenuRepository extends BaseRepository<PortalMenu, Long> {

    @Query(value = "from PortalMenu where deletedBy is null and enable = true order by displayOrder")
    List<PortalMenu> findPortalMenu();

}
