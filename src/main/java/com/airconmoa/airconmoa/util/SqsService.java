package com.airconmoa.airconmoa.util;

import com.airconmoa.airconmoa.response.BaseException;
import com.airconmoa.airconmoa.response.BaseResponseStatus;
import com.airconmoa.airconmoa.user.dto.PostEmailRes;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SqsService {

    private final QueueMessagingTemplate queueMessagingTemplate;
    private final ObjectMapper objectMapper;

    public SqsService(AmazonSQSAsync amazonSqs, ObjectMapper objectMapper) {
        this.queueMessagingTemplate = new QueueMessagingTemplate(amazonSqs);
        this.objectMapper = objectMapper;
    }

    /**
     * 인증번호 이메일 전송
     */
    public String sendMessage(String email) {
        // 6자리 난수 생성
        String sixDigitRandomNumber = generateSixDigitRandomNumber();
        // JSON 형식으로 변환
        String jsonMessage = createJsonMessage(email, sixDigitRandomNumber);
        Message<String> newMessage = MessageBuilder.withPayload(jsonMessage).build();
        queueMessagingTemplate.send("UMCQueue", newMessage);
        return "이메일 인증번호가 발신되었습니다.";
    }

    private String createJsonMessage(String email, String authRandomNumber) {
        try {
            // JSON 오브젝트 생성
            PostEmailRes postEmailRes = new PostEmailRes(email, authRandomNumber);
            // ObjectMapper를 사용하여 JSON 문자열로 변환
            return objectMapper.writeValueAsString(postEmailRes);
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.FAILED_TO_CONVERT);
        }
    }

    private static String generateSixDigitRandomNumber() {
        // Random 객체 생성
        Random random = new Random();

        // 100000부터 999999까지의 범위에서 난수 생성
        int randomNumber = 100000 + random.nextInt(900000);

        // 생성된 난수를 문자열로 변환하여 반환
        return String.valueOf(randomNumber);
    }
}