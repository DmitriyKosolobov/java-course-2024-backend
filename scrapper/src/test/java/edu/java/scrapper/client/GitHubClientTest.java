package edu.java.scrapper.client;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.client.GitHubClientImpl;
import edu.java.client.dto.GitHubRepositoryResponse;
import edu.java.configuration.ApplicationConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@WireMockTest(httpPort = 8080)
public class GitHubClientTest {

    @Test
    @DisplayName("Проверка клиента GitHub")
    public void gitHubClientTest() {

        stubFor(get(urlPathMatching("/repos/owner/repo"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(
                    "{\"id\": 1, \"name\": \"repo\", \"pushed_at\": \"2024-01-26T19:16:51Z\", \"updated_at\": \"2024-01-26T19:18:00Z\"}")));

        ApplicationConfig applicationConfigMock = Mockito.mock(ApplicationConfig.class);
        ApplicationConfig.BaseUrls baseUrlsMock = Mockito.mock(ApplicationConfig.BaseUrls.class);
        when(applicationConfigMock.urls()).thenReturn(baseUrlsMock);
        when(baseUrlsMock.gitHubBaseUrl()).thenReturn("http://localhost:8080/");

        GitHubClientImpl gitHubClient = new GitHubClientImpl(applicationConfigMock);

        GitHubRepositoryResponse response = gitHubClient.fetchRepository("owner", "repo");

        verify(getRequestedFor(urlEqualTo("/repos/owner/repo")));
        assert response != null;
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("repo");
        assertThat(response.getPushedAt()).isEqualTo("2024-01-26T19:16:51Z");
        assertThat(response.getUpdatedAt()).isEqualTo("2024-01-26T19:18:00Z");

    }

}
