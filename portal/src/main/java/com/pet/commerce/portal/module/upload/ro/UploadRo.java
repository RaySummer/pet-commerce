package com.pet.commerce.portal.module.upload.ro;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;

/**
 * UploadRo
 *
 * @author : ray
 * @since : 1.0 2023/09/22
 **/
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UploadRo implements Serializable {
    private static final long serialVersionUID = 7753504607648080916L;

    private String type;

    private List<MultipartFile> multipartFileList;
}
