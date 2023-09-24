
package com.pet.commerce.core.module.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;


/**
 * @author Ray
 * @since 2023/02/14
 **/
@NoRepositoryBean
public interface BaseRepository<T1, ID extends Serializable>
        extends JpaRepository<T1, ID>, JpaSpecificationExecutor<T1>, QuerydslPredicateExecutor<T1> {

    T1 findOneByIdAndDeletedTimeNull(ID id);

    List<T1> findAllByIdsAndDeletedTimeNull(List<ID> ids);

    T1 findOneByReferenceAndDeletedTimeNull(String reference);

    T1 findOneByUidAndDeletedTimeNull(String uniqueId);

    T1 findOneByUidAndDeletedTimeNull(UUID uniqueId);

    Long countOneByUidAndDeletedTimeNull(UUID uniqueId);

    List<T1> findAllByDeletedTimeNull();

    Class<T1> getEntityClass();

    EntityManager getEntityManager();

}

