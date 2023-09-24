package com.pet.commerce.core.module.base.service;


import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Ray
 * @since 2023/02/14
 **/
public interface BaseCrudService<T1, ID extends Serializable> {

    T1 findById(Long id);

    Optional<T1> findByIdOptional(Long id);

    T1 findByUid(UUID uid);

    T1 findByUid(String uid);

    Optional<T1> findByUidOptional(UUID uid);

    Optional<T1> findByUidOptional(String uid);

    T1 findOne(Predicate predicate);

    List<T1> search();

    List<T1> search(Predicate predicate);

    List<T1> search(OrderSpecifier<?>... orders);

    Page<T1> search(Pageable pageable);

    Page<T1> search(Pageable pageable, Predicate predicate);

    List<T1> search(Predicate predicate, OrderSpecifier<?>... orders);

    Long count(Predicate predicate);

    boolean exists(Predicate predicate);

    T1 save(T1 entity);

    List<T1> save(Iterable<T1> entities);

    T1 create(T1 entity);

    List<T1> create(Iterable<T1> entities);

    T1 update(T1 entity);

    List<T1> update(Iterable<T1> entities);

    T1 delete(Long id);

    T1 delete(UUID uid);

    T1 delete(String uid);

    List<T1> delete(Predicate predicate);

    T1 delete(T1 entity);

    List<T1> delete(Iterable<T1> entities);

    void physicalDelete(T1 entity);

    void physicalDelete(ID id);

    void physicalDelete(UUID uid);

    void physicalDelete(String uid);

    void physicalDelete(Predicate predicate);

    void physicalDelete(Iterable<T1> entities);
}
