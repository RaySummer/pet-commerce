package com.pet.commerce.portal.module.upload.vo;

import com.pet.commerce.core.module.media.dto.MediaDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * UploadVo
 *
 * @author : ray
 * @since : 1.0 2023/09/22
 **/
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UploadVo implements Serializable {
    private static final long serialVersionUID = -4886284984420941663L;

    private List<MediaDto> mediaDtoList;

}
