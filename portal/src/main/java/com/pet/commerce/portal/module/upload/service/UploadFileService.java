package com.pet.commerce.portal.module.upload.service;

import com.pet.commerce.core.module.media.service.MediaCoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * UploadFileService
 *
 * @author : ray
 * @since : 1.0 2023/09/22
 **/
@Slf4j
@Service()
@Transactional
public class UploadFileService {

    @Autowired
    private MediaCoreService mediaCoreService;

}
