package com.pet.commerce.core.module.base.service.impl;

import com.google.common.collect.Lists;
import com.pet.commerce.core.exception.BusinessException;
import com.pet.commerce.core.exception.NotFoundException;
import com.pet.commerce.core.module.base.model.BaseAuditEntity;
import com.pet.commerce.core.module.base.model.QBaseAuditEntity;
import com.pet.commerce.core.module.base.repository.BaseRepository;
import com.pet.commerce.core.module.base.service.BaseCrudService;
import com.pet.commerce.core.utils.WebThreadLocal;
import com.querydsl.codegen.Keywords;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.pet.commerce.core.constants.Constants.FIELD_DELETED_TIME;
import static com.pet.commerce.core.constants.Constants.PROPERTY_ID;
import static com.pet.commerce.core.constants.Constants.PROPERTY_UID;

/**
 * @author Ray
 * @since 2023/02/14
 **/
@Transactional
public abstract class BaseCrudServiceImpl<X extends BaseRepository<T1, ID>, T1 extends BaseAuditEntity, ID extends Serializable> implements BaseCrudService<T1, ID> {

    @PersistenceContext
    protected EntityManager entityManager;

    private final Class<T1> entityClazz;
    private final PathBuilder<T1> entityPath; // QueryDsl path builder

    private static final String PROPERTY_DELETED_TIME = QBaseAuditEntity.baseAuditEntity.deletedTime.getMetadata().getName();
    private static final String PROPERTY_ID = QBaseAuditEntity.baseAuditEntity.id.getMetadata().getName();
    private static final String PROPERTY_UID = QBaseAuditEntity.baseAuditEntity.uid.getMetadata().getName();

    @Autowired
    protected X baseRepository;

    protected BaseCrudServiceImpl() {
        entityClazz = (Class<T1>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        entityPath = new PathBuilder<>(entityClazz, getEntityPathAlias(entityClazz));
    }

    @Transactional(readOnly = true)
    @Override
    public T1 findById(Long id) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(entityPath.get(PROPERTY_ID).eq(id));
        addDeleteCondition(builder);
        return baseRepository.findOne(builder).orElseThrow(() -> new NotFoundException(entityClazz, id));
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<T1> findByIdOptional(Long id) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(entityPath.get(PROPERTY_ID).eq(id));
        addDeleteCondition(builder);
        return baseRepository.findOne(builder);
    }

    @Transactional(readOnly = true)
    @Override
    public T1 findByUid(UUID uid) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(entityPath.get(PROPERTY_UID).eq(uid));
        addDeleteCondition(builder);
        return baseRepository.findOne(builder).orElseThrow(() -> new NotFoundException(entityClazz, uid));
    }

    @Transactional(readOnly = true)
    @Override
    public T1 findByUid(String uid) {
        UUID uuid = formatUid(uid);
        return findByUid(uuid);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<T1> findByUidOptional(UUID uid) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(entityPath.get(PROPERTY_UID).eq(uid));
        addDeleteCondition(builder);
        return baseRepository.findOne(builder);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<T1> findByUidOptional(String uid) {
        UUID uuid = formatUid(uid);
        return findByUidOptional(uuid);
    }

    @Transactional(readOnly = true)
    @Override
    public T1 findOne(Predicate predicate) {
        BooleanBuilder builder =  toCondition(predicate);
        return baseRepository.findOne(builder).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<T1> search() {
        return search((Predicate) null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<T1> search(Predicate predicate) {
        BooleanBuilder builder =  toCondition(predicate);
        return Lists.newArrayList(baseRepository.findAll(builder, idDesc()));
    }

    @Transactional(readOnly = true)
    @Override
    public List<T1> search(OrderSpecifier<?>... orders) {
        return search(null, orders);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<T1> search(Pageable pageable) {
        return search(pageable, null);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<T1> search(Pageable pageable, Predicate predicate) {
        BooleanBuilder builder = toCondition(predicate);
        return baseRepository.findAll(builder, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public List<T1> search(Predicate predicate, OrderSpecifier<?>... orders) {
        BooleanBuilder builder = toCondition(predicate);
        return Lists.newArrayList(baseRepository.findAll(builder, orders));
    }

    @Transactional(readOnly = true)
    @Override
    public Long count(Predicate predicate) {
        BooleanBuilder builder =  toCondition(predicate);
        return baseRepository.count(builder);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean exists(Predicate predicate) {
        BooleanBuilder builder = toCondition(predicate);
        return baseRepository.exists(builder);
    }

    @Transactional
    @Override
    public T1 save(T1 entity) {
        if (entity.getId() == null) {
            return create(entity);
        } else {
            return update(entity);
        }
    }

    @Transactional
    @Override
    public List<T1> save(Iterable<T1> entities) {
        for (T1 entity : entities) {
            if (entity.getId() == null) {
                setCreateMsg(entity);
            } else {
                setUpdateMsg(entity);
            }
        }
        return baseRepository.saveAllAndFlush(entities);
    }

    @Transactional
    @Override
    public T1 create(T1 entity) {
        setCreateMsg(entity);
        return baseRepository.saveAndFlush(entity);
    }

    @Transactional
    @Override
    public List<T1> create(Iterable<T1> entities) {
        for (T1 entity : entities) {
            setCreateMsg(entity);
        }
        return baseRepository.saveAllAndFlush(entities);
    }

    @Transactional
    @Override
    public T1 update(T1 entity) {
        setUpdateMsg(entity);
        return baseRepository.saveAndFlush(entity);
    }

    @Transactional
    @Override
    public List<T1> update(Iterable<T1> entities) {
        for (T1 entity : entities) {
            setUpdateMsg(entity);
        }
        return baseRepository.saveAllAndFlush(entities);
    }

    @Transactional
    @Override
    public T1 delete(Long id) {
        Optional<T1> byIdOptional = findByIdOptional(id);
        T1 entity = byIdOptional.orElseThrow(() -> new NotFoundException(entityClazz, id));
        return delete(entity);
    }

    @Transactional
    @Override
    public T1 delete(UUID uid) {
        Optional<T1> byIdOptional = findByUidOptional(uid);
        T1 entity = byIdOptional.orElseThrow(() -> new NotFoundException(entityClazz, uid));
        return delete(entity);
    }

    @Transactional
    @Override
    public T1 delete(String uid) {
        UUID uuid = formatUid(uid);
        return delete(uuid);
    }

    @Transactional
    @Override
    public List<T1> delete(Predicate predicate) {
        List<T1> list = search(predicate);
        return delete(list);
    }

    @Transactional
    @Override
    public T1 delete(T1 entity) {
        setDeleteMsg(entity);
        return baseRepository.saveAndFlush(entity);
    }

    @Transactional
    @Override
    public List<T1> delete(Iterable<T1> entities) {
        for (T1 entity : entities) {
            setDeleteMsg(entity);
        }
        return baseRepository.saveAllAndFlush(entities);
    }

    @Transactional
    @Override
    public void physicalDelete(T1 entity) {
        baseRepository.delete(entity);
    }

    @Transactional
    @Override
    public void physicalDelete(ID id) {
        baseRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void physicalDelete(UUID uid) {
        Optional<T1> byUidOptional = findByUidOptional(uid);
        byUidOptional.ifPresent(this::physicalDelete);
    }

    @Transactional
    @Override
    public void physicalDelete(String uid) {
        Optional<T1> byUidOptional = findByUidOptional(uid);
        byUidOptional.ifPresent(this::physicalDelete);
    }

    @Transactional
    @Override
    public void physicalDelete(Predicate predicate) {
        Iterable<T1> iterable = baseRepository.findAll(predicate);
        baseRepository.deleteAll(iterable);
        baseRepository.flush();
    }

    @Transactional
    @Override
    public void physicalDelete(Iterable<T1> entities) {
        baseRepository.deleteAll(entities);
    }

    /**
     * get querydsl entity path alias
     *
     * @param entityClass 实体类
     * @return 查询实体路径别名
     */
    private String getEntityPathAlias(Class<T1> entityClass) {
        Collection<String> keywords = new HashSet<>();
        Collections.addAll(keywords, Keywords.JPA.toArray(new String[0]));
        Collections.addAll(keywords, Keywords.JDO.toArray(new String[0]));

        String simpleName = entityClass.getSimpleName();
        String alias = StringUtils.uncapitalize(simpleName);

        if (keywords.contains(simpleName.toUpperCase())) {
            alias = alias + "1";
        }
        return alias;
    }

    private void addDeleteCondition(BooleanBuilder builder) {
        builder.and(entityPath.get(PROPERTY_DELETED_TIME).isNull());
    }

    private Sort idDesc() {
        return Sort.by(Sort.Direction.DESC, PROPERTY_ID);
    }

    protected BooleanBuilder toCondition(Predicate predicate) {
        BooleanBuilder builder =  new BooleanBuilder();
        if (predicate != null) {
            builder.and(predicate);
        }
        addDeleteCondition(builder);
        return builder;
    }

    private void setCreateMsg(T1 e) {
        if (StringUtils.isBlank(e.getCreatedBy())) {
            e.setCreatedBy(WebThreadLocal.getOperatorName());
        }
    }

    private void setUpdateMsg(T1 e) {
        if (StringUtils.isBlank(e.getUpdatedBy())) {
            e.setUpdatedBy(WebThreadLocal.getOperatorName());
        }
    }

    private void setDeleteMsg(T1 e) {
        if (e.getDeletedTime() == null) {
            e.setDeletedTime(WebThreadLocal.getOperatorTime());
        }
        if (StringUtils.isBlank(e.getDeletedBy())) {
            e.setDeletedBy(WebThreadLocal.getOperatorName());
        }
    }


    public UUID formatUid(String uid) {
        try {
            return UUID.fromString(uid);
        } catch (Exception e) {
            throw new BusinessException("ERROR_UUID_FORMAT_INVALID", uid);
        }
    }

    public PathBuilder<T1> getEntityPath() {
        return entityPath;
    }
}
