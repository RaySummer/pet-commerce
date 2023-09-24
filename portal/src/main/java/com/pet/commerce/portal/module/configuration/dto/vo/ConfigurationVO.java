package com.pet.commerce.portal.module.configuration.dto.vo;

import com.pet.commerce.core.module.configuration.model.Configuration;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ray
 * @since 2023/3/13
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConfigurationVO {

    private String key;
    private String value;
    private String description;
    private String platform;

    public static ConfigurationVO of(Configuration configuration) {
        ConfigurationVO vo = new ConfigurationVO();
        BeanUtils.copyProperties(configuration, vo);
        return vo;
    }

    public static List<ConfigurationVO> ofList(List<Configuration> list) {
        return list.stream().map(ConfigurationVO::of).collect(Collectors.toList());
    }
}
