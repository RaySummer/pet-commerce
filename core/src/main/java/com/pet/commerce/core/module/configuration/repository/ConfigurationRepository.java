package com.pet.commerce.core.module.configuration.repository;

import com.pet.commerce.core.module.base.repository.BaseRepository;
import com.pet.commerce.core.module.configuration.model.Configuration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Ray
 * @since 2023/2/21
 */
public interface ConfigurationRepository extends BaseRepository<Configuration, Long> {

    @Query(value = "select conf from Configuration conf where conf.deletedBy is null and conf.platform = :#{#platform}")
    List<Configuration> findAllByPlatformAndDeletedByIsNull(@Param("platform") String platform);

    @Query(value = "select conf from Configuration conf where conf.key = :#{#key} and conf.platform = :#{#platform}")
    Configuration findByKeyAndPlatform(@Param("key") String key, @Param("platform") String platform);
}
