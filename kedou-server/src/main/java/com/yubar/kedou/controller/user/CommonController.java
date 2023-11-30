package com.yubar.kedou.controller.user;
/**
 * Author:LanBao
 * Date:2023/10/23-22:13
 */


import com.yubar.kedou.constant.MessageConstant;
import com.yubar.kedou.enumeration.UploadMode;
import com.yubar.kedou.result.Result;
import com.yubar.kedou.utils.AliOssUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @ClassName CommonController
 * @Description 通用接口
 * @Author 兰豹基
 * @Date 2023/10/23 22:13
 * @Version 1.0
 **/
@RestController
@RequestMapping("/user/common")
@Tag(name = "通用接口", description = "文件上传相关接口")
@Slf4j
public class CommonController {
    @Autowired
    private AliOssUtil aliOssUtil;
    @Value("${kedou.upload.mode}")
    private UploadMode uploadMode;
    @Value("${kedou.upload.base-path}")
    private String basePath;
    @Value("${kedou.upload.return-base-path}")
    private String returnBasePath;

    @PostMapping("/upload")
    @Operation(description = "文件上传")
    public Result<String> upload(MultipartFile file) {
        log.info("文件上传：{}", file);
        //获取原始文件名称
        String originalFilename = file.getOriginalFilename();

        //构造唯一的文件名
        int index = originalFilename.lastIndexOf(".");
        String extendName = originalFilename.substring(index); //获取文件扩展名
        String newFileName = UUID.randomUUID().toString() + extendName;
        log.info("上传的新文件名为{}", newFileName);
        try {
            switch (uploadMode) {
                //将文件存储在服务器的磁盘目录中
                case LOCAL -> {
                    file.transferTo(new File(basePath + newFileName));
                    return Result.success(returnBasePath + newFileName);
                }
                //文件上传OSS
                case CLOUD -> {
                    String filePath = aliOssUtil.upload(file.getBytes(), newFileName);
                    return Result.success(filePath);
                }
            }
        } catch (IOException e) {
            log.info("文件上传失败：{}", e);
        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }

}
