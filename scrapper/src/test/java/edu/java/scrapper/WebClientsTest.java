package edu.java.scrapper;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.client.GitHubClientImpl;
import edu.java.client.StackOverflowClientImpl;
import edu.java.client.dto.GitHubRepositoryResponse;
import edu.java.client.dto.StackOverflowQuestionItem;
import edu.java.client.dto.StackOverflowQuestionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.web.reactive.function.client.WebClient;
import java.time.Instant;
import java.time.ZoneOffset;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@WireMockTest(httpPort = 8080)
public class WebClientsTest {
    private WebClient webClient;

    @BeforeEach
    public void setup() {
        webClient = WebClient.builder().baseUrl("http://localhost:8080/").build();
    }

    @Test
    @DisplayName("Проверка клиента GitHub")
    public void GitHubClientTest() {

        stubFor(get(urlPathMatching("/repos/owner/repo"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(
                    "{\"id\": 1, \"name\": \"repo\", \"pushed_at\": \"2024-01-26T19:16:51Z\", \"updated_at\": \"2024-01-26T19:18:00Z\"}")));

        GitHubClientImpl gitHubClient = new GitHubClientImpl(webClient);

        GitHubRepositoryResponse response = gitHubClient.fetchRepository("owner", "repo").block();

        verify(getRequestedFor(urlEqualTo("/repos/owner/repo")));
        assert response != null;
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("repo");
        assertThat(response.getPushedAt()).isEqualTo("2024-01-26T19:16:51Z");
        assertThat(response.getUpdatedAt()).isEqualTo("2024-01-26T19:18:00Z");

    }

    @Test
    @DisplayName("Проверка клиента StackOverflow")
    public void StackOverflowClientTest(){

        stubFor(get(urlPathMatching("/2.3/questions/1"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(
                    "{\"items\": [{ \"question_id\": 1, \"is_answered\": true,\"view_count\": 2,\"answer_count\": 2,\"score\": 2, \"creation_date\": 1351578086 ,\"last_activity_date\": 1352102450 }]}")));

        StackOverflowClientImpl stackOverflowClient = new StackOverflowClientImpl(webClient);

        StackOverflowQuestionResponse response = stackOverflowClient.fetchQuestion(1L).block();

        verify(getRequestedFor(urlEqualTo("/2.3/questions/1?order=desc&sort=activity&site=stackoverflow")));
        assert response != null;
        StackOverflowQuestionItem responseItem = response.getQuestions().getFirst();
        assertThat(responseItem.getQuestionId()).isEqualTo(1L);
        assertThat(responseItem.isAnswered()).isTrue();
        assertThat(responseItem.getViewCount()).isEqualTo(2);
        assertThat(responseItem.getAnswerCount()).isEqualTo(2);
        assertThat(responseItem.getScore()).isEqualTo(2);
        assertThat(responseItem.getCreationDate()).isEqualTo(Instant.ofEpochSecond(1351578086).atOffset(ZoneOffset.UTC));
        assertThat(responseItem.getLastActivityDate()).isEqualTo(Instant.ofEpochSecond(1352102450).atOffset(ZoneOffset.UTC));
    }

}
