package com.pet.commerce.portal.module.openai.dto.vo;

import com.pet.commerce.core.module.openai.model.ChatGPTRecode;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ray
 * @since 2023/3/1
 */
@Getter
@Setter
public class ChatGPTVO implements Serializable {
    private static final long serialVersionUID = -4678868328578417558L;

    private List<ChatContentVO> contents = new ArrayList<>();

    public static ChatGPTVO of(List<ChatContentVO> chatContentVOList) {
        ChatGPTVO chatGPTVO = new ChatGPTVO();
        chatGPTVO.setContents(chatContentVOList);
        return chatGPTVO;
    }

    public static ChatContentVO of(ChatGPTRecode chatGPTRecode) {
        Assert.notNull(chatGPTRecode, "ChatGPTRecode is null");
        return ChatContentVO
                .builder()
                .type(chatGPTRecode.getType())
                .content(chatGPTRecode.getContent())
                .chatTime(chatGPTRecode.getCreatedTime())
                .build();
    }

    public static List<ChatContentVO> ofList(List<ChatGPTRecode> chatGPTRecodeList) {
        if (chatGPTRecodeList.isEmpty()) {
            return null;
        }
        return chatGPTRecodeList.stream().map(ChatGPTVO::of).collect(Collectors.toList());
    }

    @Getter
    @Setter
    @Builder
    public static class ChatContentVO implements Serializable {
        private static final long serialVersionUID = 1661975580088641243L;

        private String type;

        private String content;

        private Date chatTime;


    }
}
