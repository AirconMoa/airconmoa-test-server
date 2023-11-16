package com.airconmoa.airconmoa.util;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SqsConfig {

    @Bean
    public AmazonSQSAsync amazonSQSAsync() {
        // AmazonSQSAsync 빈을 생성하여 반환하는 코드 작성
        return AmazonSQSAsyncClientBuilder.standard()
                .withRegion("ap-northeast-2")
                .build();
    }

}
