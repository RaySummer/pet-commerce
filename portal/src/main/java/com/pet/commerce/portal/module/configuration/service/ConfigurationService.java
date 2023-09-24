package com.pet.commerce.portal.module.configuration.service;

import com.alibaba.fastjson.JSONObject;
import com.pet.commerce.core.module.configuration.enums.ConfigurationPlatformEnum;
import com.pet.commerce.core.module.configuration.model.Configuration;
import com.pet.commerce.core.module.configuration.service.ConfigurationCoreService;
import com.pet.commerce.core.utils.CustomizeException;
import com.pet.commerce.portal.cache.CacheDataUtil;
import com.pet.commerce.portal.module.configuration.dto.ro.ConfigurationRO;
import com.pet.commerce.portal.module.configuration.dto.vo.ConfigurationVO;
import com.pet.commerce.portal.module.menu.dto.vo.PortalMenuVO;
import com.pet.commerce.portal.module.menu.service.PortalMenuService;
import com.pet.commerce.portal.module.openai.dto.vo.OpenAIConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.pet.commerce.core.constants.Constants.CONFIG_REF_OPEN_AI_KEY;

/**
 * @author Ray
 * @since 2023/3/13
 */
@Service
public class ConfigurationService {

    @Autowired
    private ConfigurationCoreService configurationCoreService;

    @Autowired
    private PortalMenuService portalMenuService;

    public List<ConfigurationVO> findPlatformConfig() {
        return ConfigurationVO.ofList(configurationCoreService.findAllByPlatform(ConfigurationPlatformEnum.PORTAL.name()));
    }

    public void refreshAllConfig() {

        //刷新所有configuration缓存
        List<ConfigurationVO> configurationVOList = findPlatformConfig();
        CacheDataUtil.setConfigurationList(configurationVOList);

        //刷新OpenAI缓存
        Configuration openAIConfig = configurationCoreService.findByKeyAndPlatform(CONFIG_REF_OPEN_AI_KEY, ConfigurationPlatformEnum.PORTAL.name());
        CacheDataUtil.setOpenAIConfigVO(JSONObject.parseObject(openAIConfig.getValue(), OpenAIConfigVO.class));

        //刷新portal menu缓存
        List<PortalMenuVO> menu = portalMenuService.findMenu();
        CacheDataUtil.setPortalMenuVOList(menu);

    }

    @Transactional
    public ConfigurationVO updateConfiguration(String uid, ConfigurationRO ro) {
        Configuration configuration = configurationCoreService.findByUid(uid);
        if (configuration == null) {
            throw new CustomizeException("Cannot find configuration by uid");
        }
        configuration.setDescription(ro.getDescription());
        configuration.setValue(ro.getValue());
        configurationCoreService.update(configuration);
        return ConfigurationVO.of(configuration);
    }

    @Transactional
    public ConfigurationVO createConfiguration(ConfigurationRO ro) {
        if (configurationCoreService.isExistByKeyAndPlatform(ro.getKey(), ro.getPlatform())) {
            throw new CustomizeException("This Key and Platform is exist!!");
        }
        Configuration configuration = new Configuration();
        configuration.setKey(ro.getKey());
        configuration.setValue(ro.getValue());
        configuration.setPlatform(ro.getPlatform());
        configuration.setDescription(ro.getDescription());
        configurationCoreService.create(configuration);

        return ConfigurationVO.of(configuration);
    }

    public Configuration findByKeyAndPlatform(String key, String platform) {
        return configurationCoreService.findByKeyAndPlatform(key, platform);
    }

}
