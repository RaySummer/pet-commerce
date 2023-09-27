package com.pet.commerce.portal.module.user.service;

import com.pet.commerce.core.module.base.vo.PageVO;
import com.pet.commerce.core.module.user.dto.SysUserCreateDto;
import com.pet.commerce.core.module.user.dto.SysUserUpdatePasswordDto;
import com.pet.commerce.core.module.user.model.QSysUser;
import com.pet.commerce.core.module.user.model.SysRole;
import com.pet.commerce.core.module.user.model.SysUser;
import com.pet.commerce.core.module.user.model.SysUserRSysRole;
import com.pet.commerce.core.module.user.service.SysRoleCoreService;
import com.pet.commerce.core.module.user.service.SysUserCoreService;
import com.pet.commerce.core.utils.CustomizeException;
import com.pet.commerce.core.utils.WebThreadLocal;
import com.pet.commerce.portal.module.user.dto.ro.SysUserPageRO;
import com.pet.commerce.portal.module.user.dto.ro.SysUserRegisterRO;
import com.pet.commerce.portal.module.user.dto.ro.SysUserUpdatePasswordRO;
import com.pet.commerce.portal.module.user.dto.ro.SysUserUpdateRO;
import com.pet.commerce.portal.module.user.dto.vo.SysRoleDetailVO;
import com.pet.commerce.portal.module.user.dto.vo.SysUserDetailVO;
import com.pet.commerce.portal.module.user.dto.vo.SysUserPageVO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * SysUserService
 *
 * @author : ray
 * @since : 1.0 2023/09/25
 **/
@Slf4j
@Service
@Transactional
public class SysUserService {

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    private SysUserCoreService sysUserCoreService;

    @Autowired
    private SysRoleCoreService sysRoleCoreService;

    public SysUserCreateDto registerUser(SysUserRegisterRO ro) {
        SysUserCreateDto dto = new SysUserCreateDto();

        dto.setAccount(ro.getAccount());
        dto.setEmail(ro.getEmail());
        dto.setMobileNumber(ro.getMobileNumber());
        dto.setName(ro.getName());
        dto.setPassword(ro.getPassword());
        dto.getRoleCodes().addAll(ro.getRoleCodes());
        sysUserCoreService.createUser(dto);
        return dto;
    }

    public SysUser modifyUser(String account, SysUserUpdateRO ro) {
        SysUser sysUser = sysUserCoreService.findByAccount(account);
        if (ObjectUtils.isEmpty(sysUser)) {
            throw new CustomizeException("Cannot found the user by this account");
        }
        if (StringUtils.isNotBlank(ro.getAvatar())) {
            sysUser.setAvatar(ro.getAvatar());
        }
        if (StringUtils.isNotBlank(ro.getEmail())) {
            sysUser.setEmail(ro.getEmail());
        }
        if (StringUtils.isNotBlank(ro.getName())) {
            sysUser.setName(ro.getName());
        }
        if (StringUtils.isNotBlank(ro.getMobileNumber())) {
            sysUser.setMobileNumber(ro.getMobileNumber());
        }
        if (!CollectionUtils.isEmpty(ro.getRoleCodes())) {
            List<SysUserRSysRole> sysUserRSysRoleList = new ArrayList<>();
            for (String roleCode : ro.getRoleCodes()) {
                SysRole sysRole = sysRoleCoreService.findByCode(roleCode);
                if (ObjectUtils.isNotEmpty(sysRole)) {
                    SysUserRSysRole sysUserRSysRole = new SysUserRSysRole();
                    sysUserRSysRole.setSysUser(sysUser);
                    sysUserRSysRole.setSysRole(sysRole);
                    sysUserRSysRole.setCreatedBy(WebThreadLocal.getOperatorName());
                    sysUserRSysRole.setCreatedTime(new Date());

                    sysUserRSysRoleList.add(sysUserRSysRole);
                }
            }
            sysUser.getSysUserRSysRoles().clear();
            sysUserCoreService.update(sysUser);
            sysUser.getSysUserRSysRoles().addAll(sysUserRSysRoleList);
        }

        return sysUserCoreService.updateUserProfile(sysUser);
    }

    public void deleteUser(String account) {
        SysUser sysUser = sysUserCoreService.findByAccount(account);
        if (ObjectUtils.isEmpty(sysUser)) {
            throw new CustomizeException("Cannot found the user by this account");
        }
        sysUserCoreService.deleteUser(sysUser);
    }

    public SysUserDetailVO getUser(String uid) {
        SysUser sysUser = sysUserCoreService.findByUid(uid);
        return convertVO(sysUser);
    }


    public PageVO<SysUserPageVO> searchPage(SysUserPageRO ro) {

        QSysUser qSysUser = QSysUser.sysUser;

        BooleanBuilder builder = new BooleanBuilder();

        String keyword = ro.getKeyword();
        if (StringUtils.isNotBlank(keyword)) {
            builder.andAnyOf(
                    qSysUser.name.containsIgnoreCase(keyword),
                    qSysUser.email.containsIgnoreCase(keyword),
                    qSysUser.mobileNumber.containsIgnoreCase(keyword)
            );
        }
        String roleCode = ro.getRoleCode();
        if (StringUtils.isNotBlank(roleCode)) {
            builder.and(qSysUser.sysUserRSysRoles.any().sysRole.code.eq(roleCode));
        }
        builder.and(qSysUser.deletedBy.isNull());

        JPAQuery<Tuple> query = new JPAQuery<>(entityManager);

        query.select(qSysUser);
        query.from(qSysUser);

        query.distinct().where(builder);

        //order by
        String sort = ro.getSort();
        ArrayList<OrderSpecifier<?>> sortOrders = new ArrayList<>();

        if (StringUtils.isNotBlank(sort) && sort.equalsIgnoreCase("byId")) {
            sortOrders.add(qSysUser.id.desc());
        } else {
            sortOrders.add(qSysUser.createdTime.desc());
        }
        Pageable pageable = ro.getPageable();
        QueryResults<SysUser> tupleQueryResults = new JPAQueryFactory(entityManager)
                .select(qSysUser)
                .from(qSysUser)
                .where(builder)
                .orderBy(sortOrders.toArray(new OrderSpecifier[sortOrders.size()]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<SysUser> results = tupleQueryResults.getResults();

        PageImpl<SysUser> sysUserPage = new PageImpl<>(results, pageable, tupleQueryResults.getTotal());

        List<SysUserPageVO> voContent = new ArrayList<>();

        results.forEach(sysUser -> {
            SysUserPageVO vo = new SysUserPageVO();
            vo.setUid(sysUser.getUidStr());
            vo.setCreatedTime(sysUser.getCreatedTime());
            vo.setUpdatedTime(sysUser.getUpdatedTime());
            vo.setAvatar(sysUser.getAvatar());
            vo.setEmail(sysUser.getEmail());
            vo.setName(sysUser.getName());
            vo.setMobileNumber(sysUser.getMobileNumber());
            List<SysRoleDetailVO> sysRoleDetailVOList = new ArrayList<>();
            for (SysUserRSysRole sysUserRSysRole : sysUser.getSysUserRSysRoles()) {
                SysRole sysRole = sysUserRSysRole.getSysRole();
                if (ObjectUtils.isNotEmpty(sysRole)) {
                    SysRoleDetailVO sysRoleDetailVO = new SysRoleDetailVO();
                    sysRoleDetailVO.setCode(sysRole.getCode());
                    sysRoleDetailVO.setName(sysRole.getName());
                    sysRoleDetailVO.setDescription(sysRole.getDescription());
                    sysRoleDetailVOList.add(sysRoleDetailVO);
                }
            }
            vo.getSysRoleList().clear();
            vo.getSysRoleList().addAll(sysRoleDetailVOList);
            voContent.add(vo);
        });

        return PageVO.convert(sysUserPage, voContent);
    }


    public void modifyUserPassword(SysUserUpdatePasswordRO ro) {
        if (StringUtils.isBlank(ro.getAccount())) {
            throw new CustomizeException("User account cannot be null");
        }
        if (StringUtils.isBlank(ro.getOldPassword())) {
            throw new CustomizeException("User old password account cannot be null");
        }
        if (StringUtils.isBlank(ro.getNewPassword())) {
            throw new CustomizeException("User new password account cannot be null");
        }
        SysUser sysUser = sysUserCoreService.findByAccount(ro.getAccount());
        if (ObjectUtils.isEmpty(sysUser)) {
            throw new CustomizeException("Cannot found user");
        }
        SysUserUpdatePasswordDto dto = new SysUserUpdatePasswordDto();
        dto.setAccount(ro.getAccount());
        dto.setOldPassword(ro.getOldPassword());
        dto.setNewPassword(ro.getNewPassword());
        sysUserCoreService.updatePassword(dto);
    }

    public SysUserDetailVO findByAccount(String account) {
        SysUser sysUser = sysUserCoreService.findByAccount(account);
        return convertVO(sysUser);
    }

    private SysUserDetailVO convertVO(SysUser sysUser) {
        if (ObjectUtils.isEmpty(sysUser)) {
            throw new CustomizeException("Cannot found the user");
        }
        SysUserDetailVO vo = new SysUserDetailVO();
        vo.setAvatar(sysUser.getAvatar());
        vo.setEmail(sysUser.getEmail());
        vo.setName(sysUser.getName());
        vo.setMobileNumber(sysUser.getMobileNumber());
        vo.setAccount(sysUser.getAccount());
        vo.setUid(sysUser.getUidStr());

        if (!CollectionUtils.isEmpty(sysUser.getSysUserRSysRoles())) {
            List<SysRoleDetailVO> sysRoleDetailVOList = new ArrayList<>();
            for (SysUserRSysRole sysUserRSysRole : sysUser.getSysUserRSysRoles()) {
                SysRole sysRole = sysUserRSysRole.getSysRole();
                if (ObjectUtils.isNotEmpty(sysRole)) {
                    SysRoleDetailVO sysRoleDetailVO = new SysRoleDetailVO();
                    sysRoleDetailVO.setCode(sysRole.getCode());
                    sysRoleDetailVO.setName(sysRole.getName());
                    sysRoleDetailVO.setDescription(sysRole.getDescription());
                    sysRoleDetailVO.setStatus(sysRole.isStatus());
                    sysRoleDetailVOList.add(sysRoleDetailVO);
                }
            }
            vo.getSysRoleList().clear();
            vo.getSysRoleList().addAll(sysRoleDetailVOList);
        }
        return vo;
    }

}
