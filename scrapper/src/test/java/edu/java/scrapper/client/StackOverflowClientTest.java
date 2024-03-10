package edu.java.scrapper.client;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.client.StackOverflowClientImpl;
import edu.java.client.dto.StackOverflowQuestionItem;
import edu.java.client.dto.StackOverflowQuestionResponse;
import edu.java.configuration.ApplicationConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.Instant;
import java.time.ZoneOffset;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@WireMockTest(httpPort = 8080)
public class StackOverflowClientTest {

    @Test
    @DisplayName("Проверка клиента StackOverflow")
    public void stackOverflowClientTest(){

        stubFor(get(urlPathMatching("/2.3/questions/1"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(
                    "{\"items\": [{ \"question_id\": 1, \"is_answered\": true,\"view_count\": 2,\"answer_count\": 2,\"score\": 2, \"creation_date\": 1351578086 ,\"last_activity_date\": 1352102450 }]}")));

        ApplicationConfig applicationConfigMock = Mockito.mock(ApplicationConfig.class);
        ApplicationConfig.BaseUrls baseUrlsMock = Mockito.mock(ApplicationConfig.BaseUrls.class);
        when(applicationConfigMock.urls()).thenReturn(baseUrlsMock);
        when(baseUrlsMock.stackOverflowBaseUrl()).thenReturn("http://localhost:8080/");

        StackOverflowClientImpl stackOverflowClient = new StackOverflowClientImpl(applicationConfigMock);

        StackOverflowQuestionResponse response = stackOverflowClient.fetchQuestion(1L);

        verify(getRequestedFor(urlEqualTo("/2.3/questions/1?order=desc&sort=activity&site=stackoverflow")));
        assertNotNull(response);
        StackOverflowQuestionItem responseItem = response.getItems().getFirst();
        assertEquals(1L,responseItem.getQuestionId());
        assertTrue(responseItem.isAnswered());
        assertEquals(2,responseItem.getViewCount());
        assertEquals(2,responseItem.getAnswerCount());
        assertEquals(2,responseItem.getScore());
        assertEquals(Instant.ofEpochSecond(1351578086).atOffset(ZoneOffset.UTC),responseItem.getCreationDate());
        assertEquals(Instant.ofEpochSecond(1352102450).atOffset(ZoneOffset.UTC),responseItem.getLastActivityDate());
    }
}
