package com.pet.commerce.portal.module.upload.controller;

import com.pet.commerce.core.module.media.dto.MediaDto;
import com.pet.commerce.core.module.media.service.MediaCoreService;
import com.pet.commerce.core.utils.CustomizeException;
import com.pet.commerce.core.utils.Response;
import com.pet.commerce.portal.module.upload.ro.UploadRo;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * UploadFileController
 *
 * @author : ray
 * @since : 1.0 2023/09/22
 **/
@RestController
@Configuration
@RequestMapping("/upload")
public class UploadFileController {

    @Value("${file.uploadFolder}")
    private String uploadFolder;

    @Value("${file.downloadFolder}")
    private String downloadFolder;

    @Value("${file.uploadVideo}")
    private String uploadVideo;

    @Autowired
    private MediaCoreService mediaCoreService;


    @PostMapping(value = "/file")
    public Response uploadImages(@RequestBody UploadRo ro) {

        List<MultipartFile> multipartFileList = ro.getMultipartFileList();

        if (CollectionUtils.isEmpty(multipartFileList)) {
            throw new CustomizeException("上传失败! 没有文件");
        }
        List<MediaDto> dtoList = new ArrayList<>();
        List<String> imageLinks = new ArrayList<>();
        for (MultipartFile file : multipartFileList) {

            //获取原文件名
            String filename = file.getOriginalFilename();
            //文件后缀名
            String prefix = filename.substring(filename.lastIndexOf(".") + 1);
            //生成新文件名
            String newName = UUID.randomUUID().toString().replace("-", "") + "." + prefix;

            File imageFolder = new File(uploadFolder);
            if (!imageFolder.exists()) {
                imageFolder.mkdirs();
            }
            try {
                //原图
                File original = new File(imageFolder, newName);
                //压缩图
                File reduce = new File(imageFolder, "reduce_" + newName);
                //缩略图
                File thumbnail = new File(imageFolder, "thumbnail_" + newName);

                IOUtils.write(file.getBytes(), new FileOutputStream(original));
                //原图地址
                String originalName = File.pathSeparator + newName;
                //压缩图地址
                String reduceName = File.pathSeparator + "reduce_" + newName;
                //缩略图地址
                String thumbnailName = File.pathSeparator + "thumbnail_" + newName;

                //压缩图片
                Thumbnails.of(original).scale(1f).outputQuality(0.25f).toFile(reduce);
                //缩略图
                Thumbnails.of(original).scale(0.2).toFile(thumbnail);

                dtoList.add(MediaDto.builder().type(ro.getType()).size(file.getSize()).original(originalName).reduce(reduceName).thumbnail(thumbnailName).build());

                imageLinks.add(originalName);
            } catch (IOException e) {
                e.printStackTrace();
                throw new CustomizeException("上传失败!");
            }
        }
        mediaCoreService.saveMedias(dtoList);
        if (imageLinks.size() > 1) {
            return Response.of(imageLinks);
        } else {
            return Response.of(imageLinks.get(0));
        }
    }

}
