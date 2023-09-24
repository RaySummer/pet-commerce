package com.pet.commerce.portal.module.openai.controller;

import com.pet.commerce.core.annotation.NoRepeatSubmit;
import com.pet.commerce.core.aspect.LimitRequest;
import com.pet.commerce.core.module.member.dto.MemberDto;
import com.pet.commerce.core.utils.LockHelper;
import com.pet.commerce.core.utils.Response;
import com.pet.commerce.core.utils.WebThreadLocal;
import com.pet.commerce.portal.module.openai.dto.ro.ChatGPTRO;
import com.pet.commerce.portal.module.openai.dto.vo.OpenAISpeechToTextVO;
import com.pet.commerce.portal.module.openai.service.ChatGPTRecodeService;
import com.pet.commerce.portal.module.openai.service.GPTSpeechTextRecodeService;
import com.pet.commerce.portal.module.openai.service.OpenAIService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Ray
 * @since 2023/2/28
 */
@Slf4j
@RestController
@RequestMapping({"/open-ai"})
public class OpenAIController {

    @Autowired
    private LockHelper lockHelper;

    @Autowired
    private OpenAIService openAIService;

    @Autowired
    private ChatGPTRecodeService chatGPTRecodeService;

    @Autowired
    private GPTSpeechTextRecodeService gptSpeechTextRecodeService;

    @Value("${file.uploadFolder}")
    private String filePath;

    @LimitRequest(count = 20, time = 30000)
    @NoRepeatSubmit
    @PostMapping("chat-to-ai")
    public Response chatToAI(@RequestBody ChatGPTRO ro) {
        MemberDto memberDto = WebThreadLocal.getMember();

        RLock lock = lockHelper.getLock(LockHelper.LOCK_MEMBER_CHAT, memberDto.getUidStr());

        try {
            lock.lock();
            return Response.of(openAIService.ChatToAI(ro, memberDto));
        } catch (Exception e) {
            log.error(e.getMessage());
            return Response.ofError(e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    @LimitRequest(count = 20, time = 30000)
    @DeleteMapping("clear-recode")
    public Response clearRecode() {
        try {
            MemberDto memberDto = WebThreadLocal.getMember();
            chatGPTRecodeService.clearRecode(memberDto.getUidStr());
            return Response.of();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.ofError();
        }
    }

    @LimitRequest(count = 20, time = 30000)
    @NoRepeatSubmit
    @PostMapping("ai-generate-image")
    public Response generateImages(@RequestBody ChatGPTRO ro) {
        MemberDto memberDto = WebThreadLocal.getMember();

        RLock lock = lockHelper.getLock(LockHelper.LOCK_MEMBER_CHAT, memberDto.getUidStr());

        try {
            lock.lock();
            return Response.of(openAIService.generateImage(ro));
        } catch (Exception e) {
            log.error(e.getMessage());
            return Response.ofError(e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    @LimitRequest(count = 20, time = 30000)
    @NoRepeatSubmit
    @PostMapping("speech-to-text")
    public Response speechToText(@RequestParam("file") MultipartFile multipartFile) {
        File fileSave = null;
        try {
            //获取文件后缀
            String fileExt = Objects.requireNonNull(multipartFile.getOriginalFilename()).substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();

            log.warn("file name is {}:", multipartFile.getOriginalFilename());
            log.warn("fileExt is {}:", fileExt);

            if (!fileExt.toLowerCase().contains("mp3") && !fileExt.toLowerCase().contains("mp4") && !fileExt.toLowerCase().contains("war")) {
                return Response.ofError(500, "该功能暂时只支持MP3 MP4 WAR格式的音频文件");
            }

            File fileFolder = new File(filePath);

            if (!fileFolder.exists()) {
                fileFolder.mkdirs();
            }

            String newFileName = UUID.randomUUID() + "." + fileExt;

            fileSave = new File(filePath, newFileName);
            multipartFile.transferTo(fileSave);

            log.warn("upload success!! file name is {}:", newFileName);

            openAIService.speechToText(fileSave, newFileName, WebThreadLocal.getMember());

            return Response.of(newFileName);
        } catch (Exception e) {
            return Response.ofError(500, e.getMessage());
        }
    }

    @GetMapping("/speech-text/{key}")
    public Response getSpeechText(@PathVariable String key) {
        OpenAISpeechToTextVO speechToTextResult = gptSpeechTextRecodeService.getSpeechToTextResult(key);
        if (speechToTextResult == null) {
            return Response.ofError("Cannot found speech text by key");
        }
        return Response.of(speechToTextResult);
    }

}
