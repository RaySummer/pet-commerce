//package com.eco.commerce.core.module.test;
//
//import com.eco.commerce.core.utils.StringUtil;
//import com.theokanning.openai.completion.CompletionChoice;
//import com.theokanning.openai.completion.CompletionRequest;
//import com.theokanning.openai.completion.CompletionResult;
//import com.theokanning.openai.image.CreateImageRequest;
//import com.theokanning.openai.service.OpenAiService;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
///**
// * @author Ray
// * @since 2023/2/15
// */
//public class TestOpenAI {
//
//    static final String apiKey = "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";
//
//    static final String organizationId = "org-B2fgWNzZ4RdnzOv0Sc45eF0g";
//
//    public static void main(String[] args) {
//
//        StringBuffer input = new StringBuffer();
//        StringBuffer sb = new StringBuffer();
//
//        boolean isChat = true;
//        Scanner sc = new Scanner(System.in);
//
//        OpenAiService service = new OpenAiService(apiKey);
//
//        System.out.println("\n请输入······ 回复esc退出聊天");
//
//        List<String> list = new ArrayList<>();
//
//        list.add("AI");
//        list.add("Ray");
//
//        while (isChat) {
//
//            String next = sc.next();
//            if (next.equalsIgnoreCase("exit")) {
//                break;
//            }
//            input.append(next);
//
//            CompletionRequest completionRequest = CompletionRequest.builder()
//                    .model("text-davinci-003")
//                    .prompt(sb.toString())
//                    .temperature(0.9)
//                    .echo(true)
//                    .maxTokens(100)
//                    .topP(1.0)
//                    .frequencyPenalty(0.0)
//                    .presencePenalty(0.6)
//                    .stop(list)
//                    .user("Ray")
//                    .n(1)
//                    .build();
//            CompletionResult completion = service.createCompletion(completionRequest);
//            for (CompletionChoice choices : completion.getChoices()) {
//                sb.append(choices.getText());
//            }
//            input.append(sb.toString().substring(input.toString().length()));
//            System.out.println(sb);
//            System.out.println("--------!!");
//            System.out.println(input);
//        }
//
//
////        CompletionRequest completionRequest = CompletionRequest.builder()
////                .model("text-davinci-003")
////                .prompt("")
////                .temperature(0.9)
////                .echo(true)
////                .maxTokens(10)
////                .topP(1.0)
////                .frequencyPenalty(0.0)
////                .presencePenalty(0.6)
////                .stop(list)
////                .n(1)
////                .build();
////        CompletionResult completion = service.createCompletion(completionRequest);
////        completion.getChoices().forEach(System.out::println);
////
////        System.out.println("\nCreating Image...");
////        CreateImageRequest request = CreateImageRequest.builder()
////                .prompt("二次元图片")
////                .build();
////
////        System.out.println("\nImage is located at:");
////        System.out.println(service.createImage(request).getData().get(0).getUrl());
//
//    }
//}
