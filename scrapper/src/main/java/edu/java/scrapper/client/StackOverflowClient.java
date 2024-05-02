package edu.java.scrapper.client;

import edu.java.scrapper.client.dto.StackOverflowAnswerResponse;
import edu.java.scrapper.client.dto.StackOverflowQuestionResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

public interface StackOverflowClient {

    @GetExchange("2.3/questions/{questionId}?order=desc&sort=activity&site=stackoverflow")
    StackOverflowQuestionResponse fetchQuestion(@PathVariable Long questionId);

    @GetExchange("2.3/questions/{questionId}/answers?order=desc&sort=activity&site=stackoverflow")
    StackOverflowAnswerResponse fetchAnswer(@PathVariable Long questionId);
}
