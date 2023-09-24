package com.pet.commerce.portal.cache;

import com.alibaba.fastjson.JSONObject;
import com.pet.commerce.core.module.configuration.enums.ConfigurationPlatformEnum;
import com.pet.commerce.core.module.configuration.model.Configuration;
import com.pet.commerce.portal.module.configuration.dto.vo.ConfigurationVO;
import com.pet.commerce.portal.module.configuration.service.ConfigurationService;
import com.pet.commerce.portal.module.menu.dto.vo.PortalMenuVO;
import com.pet.commerce.portal.module.menu.service.PortalMenuService;
import com.pet.commerce.portal.module.openai.dto.vo.OpenAIConfigVO;
import com.pet.commerce.core.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author Ray
 * @since 2023/3/13
 */
@Component
public class CacheDataUtil {

    public static List<ConfigurationVO> configurationList = new ArrayList<>();

    public static OpenAIConfigVO openAIConfigVO = new OpenAIConfigVO();

    public static List<PortalMenuVO> portalMenuVOList = new ArrayList<>();

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private PortalMenuService portalMenuService;


    @PostConstruct
    public void init() {
        System.out.println("系统启动中。。。加载数据到缓存");
        //启动时加载configuration数据到缓存
        configurationList = configurationService.findPlatformConfig();

        //open ai config
        Configuration openAIConfig = configurationService.findByKeyAndPlatform(Constants.CONFIG_REF_OPEN_AI_KEY, ConfigurationPlatformEnum.PORTAL.name());
        CacheDataUtil.setOpenAIConfigVO(JSONObject.parseObject(openAIConfig.getValue(), OpenAIConfigVO.class));

        //portal menu
        portalMenuVOList = portalMenuService.findMenu();

    }

    @PreDestroy
    public void destroy() {
        System.out.println("系统运行结束");
    }


    public static List<ConfigurationVO> getConfigurationList() {
        return configurationList;
    }

    public static void setConfigurationList(List<ConfigurationVO> configurationList) {
        CacheDataUtil.configurationList = configurationList;
    }

    public static OpenAIConfigVO getOpenAIConfigVO() {
        return openAIConfigVO;
    }

    public static void setOpenAIConfigVO(OpenAIConfigVO openAIConfigVO) {
        CacheDataUtil.openAIConfigVO = openAIConfigVO;
    }

    public static List<PortalMenuVO> getPortalMenuVOList() {
        return portalMenuVOList;
    }

    public static void setPortalMenuVOList(List<PortalMenuVO> portalMenuVOList) {
        CacheDataUtil.portalMenuVOList = portalMenuVOList;
    }

}
