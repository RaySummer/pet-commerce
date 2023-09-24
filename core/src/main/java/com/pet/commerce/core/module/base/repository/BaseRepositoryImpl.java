package com.pet.commerce.core.module.base.repository;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.QuerydslJpaRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;

/**
 * @author Ray
 * @since 2023/02/14
 **/
public class BaseRepositoryImpl<T1, ID extends Serializable>
        extends QuerydslJpaRepository<T1, ID>
        implements BaseRepository<T1, ID> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private final EntityManager entityManager;
    private final Class<T1> entityClass;

    private final String queryFromTable;

    private final String queryByUidSql;

    /**
     * @param entityInformation additional JPA specific information about entities
     * @param entityManager     Interface used to interact with the persistence context
     */
    public BaseRepositoryImpl(JpaEntityInformation<T1, ID> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
        this.entityClass = entityInformation.getJavaType();
        this.queryFromTable = String.format("select t from %s as t where t.deletedTime is null", entityClass.getSimpleName());
        this.queryByUidSql = String.format("%s and t.uid=:uid", this.queryFromTable);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T1 findOneByIdAndDeletedTimeNull(Serializable serializable) {
        try {
            return (T1) entityManager.createQuery(queryFromTable + " and t.id=:id")
                    .setParameter("id", serializable)
                    .getSingleResult();
        } catch (Exception e) {
            logger.warn("No entity found for id {} ", serializable);
            return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T1> findAllByIdsAndDeletedTimeNull(List<ID> ids) {
        if (ids == null || ids.isEmpty()) {
            return Lists.newArrayList();
        }
        try {
            return entityManager.createQuery(queryFromTable + " and t.id in :ids")
                    .setParameter("ids", ids)
                    .getResultList();
        } catch (Exception e) {
            logger.warn("No entity found for ids {} ", ids);
            return Lists.newArrayList();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public T1 findOneByReferenceAndDeletedTimeNull(String reference) {
        try {
            return (T1) entityManager.createQuery(queryFromTable + " and t.reference=:reference")
                    .setParameter("reference", reference).getSingleResult();
        } catch (Exception e) {
            logger.info("No entity found for reference: {} from {}", reference, entityClass.getSimpleName());
            return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public T1 findOneByUidAndDeletedTimeNull(String uid) {
        try {
            return (T1) entityManager.createQuery(queryByUidSql)
                    .setParameter("uid", UUID.fromString(uid)).getSingleResult();
        } catch (Exception e) {
            logger.error("javax.persistence.NoResultException: No entity found for query with uid: {} from {}",
                    uid, entityClass.getSimpleName());
            return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public T1 findOneByUidAndDeletedTimeNull(UUID uid) {
        try {
            return (T1) entityManager.createQuery(queryByUidSql).setParameter("uid", uid)
                    .getSingleResult();
        } catch (Exception e) {
            logger.error("javax.persistence.NoResultException: No entity found for query with uid: {} from {}", uid,
                    entityClass.getSimpleName());
            return null;
        }
    }

    @Override
    public Long countOneByUidAndDeletedTimeNull(UUID uid) {
        return (Long) entityManager.createQuery(queryByUidSql).setParameter("uid", uid)
                .getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T1> findAllByDeletedTimeNull() {
        try {
            return entityManager.createQuery(queryFromTable).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public Class<T1> getEntityClass() {
        return entityClass;
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}
