package com.pet.commerce.core.utils;

import com.pet.commerce.core.module.base.dto.WebThreadLocalDto;
import com.pet.commerce.core.module.member.dto.MemberDto;
import com.pet.commerce.core.module.user.dto.SysUserDto;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author Ray
 * @since 2023/2/15
 */
@Slf4j
public class WebThreadLocal {

    private static final String ANYONE = "ANYONE";

    private static final ThreadLocal<SysUserDto> userThreadLocal = new ThreadLocal<>();

    private static final ThreadLocal<MemberDto> memberThreadLocal = new ThreadLocal<>();

    private static final ThreadLocal<Date> timeStampThreadLocal = new ThreadLocal<>();

    private static final ThreadLocal<String> browserFingerprintThreadLocal = new ThreadLocal<>();

    public static SysUserDto getUser() {
        return userThreadLocal.get();
    }

    public static void setUser(SysUserDto sysUserDto) {
        userThreadLocal.set(sysUserDto);
    }

    public static MemberDto getMember() {
        return memberThreadLocal.get();
    }

    public static void setMember(MemberDto memberDTO) {
        memberThreadLocal.set(memberDTO);
    }

    public static Date getTime() {
        return timeStampThreadLocal.get();
    }

    public static void setTime(Date time) {
        timeStampThreadLocal.set(time);
    }

    public static String getBrowserFingerprint() {
        return browserFingerprintThreadLocal.get();
    }

    public static void setBrowserFingerprint(String browserFingerprint) {
        browserFingerprintThreadLocal.set(browserFingerprint);
    }

    /**
     * 获取操作人的名字
     *
     * @return 当前发起请求的操作者
     */
    public static String getOperatorName() {
        SysUserDto user = getUser();
        if (user != null) {
            return user.getName();
        }

        MemberDto member = getMember();
        if (member != null) {
            return member.getNickName();
        }
        return ANYONE;
    }

    /**
     * 获取操作时间
     */
    public static Date getOperatorTime() {
        Date date = timeStampThreadLocal.get();
        return date != null ? date : DateUtil.now();
    }

    /**
     * 清理所有的thread的变量，防止内存泄露
     */
    public static void remove() {
        userThreadLocal.remove();
        timeStampThreadLocal.remove();
        memberThreadLocal.remove();
        browserFingerprintThreadLocal.remove();
    }

    /**
     * 初始化默认的值
     */
    public static void init() {
        setUser(null);
        setTime(DateUtil.now());
        setMember(null);
        setBrowserFingerprint(null);
    }

    /**
     * 初始化值
     */
    public static void init(WebThreadLocalDto data) {
        setUser(data.getUser());
        setTime(data.getTime());
        setBrowserFingerprint(data.getBrowserFingerprint());
        setMember(data.getMemberDto());
    }

    /**
     * 获取所有值
     */
    public static WebThreadLocalDto getData() {
        WebThreadLocalDto dto = new WebThreadLocalDto();
        dto.setUser(WebThreadLocal.getUser());
        dto.setTime(WebThreadLocal.getTime());
        dto.setMemberDto(WebThreadLocal.getMember());
        dto.setBrowserFingerprint(WebThreadLocal.getBrowserFingerprint());
        return dto;
    }
}
