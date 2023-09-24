package com.pet.commerce.core.module.configuration.service;

import com.pet.commerce.core.module.base.service.impl.BaseCrudServiceImpl;
import com.pet.commerce.core.module.configuration.model.Configuration;
import com.pet.commerce.core.module.configuration.repository.ConfigurationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ray
 * @since 2023/2/21
 */
@Slf4j
@Service
public class ConfigurationCoreService extends BaseCrudServiceImpl<ConfigurationRepository, Configuration, Long> {

    public List<Configuration> findAllByPlatform(String platform) {
        return baseRepository.findAllByPlatformAndDeletedByIsNull(platform);
    }

    public boolean isExistByKeyAndPlatform(String key, String platform) {
        return baseRepository.findByKeyAndPlatform(key, platform) != null;
    }

    public Configuration findByKeyAndPlatform(String key, String platform) {
        return baseRepository.findByKeyAndPlatform(key, platform);
    }
}
