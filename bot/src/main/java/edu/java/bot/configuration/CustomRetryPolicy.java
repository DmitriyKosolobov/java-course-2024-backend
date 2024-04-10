package edu.java.bot.configuration;

import java.util.List;
import org.springframework.http.HttpStatusCode;
import org.springframework.retry.RetryContext;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

public class CustomRetryPolicy extends SimpleRetryPolicy {

    private final List<Integer> retryableStatuses;

    public CustomRetryPolicy(List<Integer> retryableStatuses) {
        this.retryableStatuses = retryableStatuses;
    }

    @Override
    public boolean canRetry(RetryContext context) {
        Throwable t = context.getLastThrowable();
        return (t == null || this.retryForException(t)) && context.getRetryCount() < this.getMaxAttempts();
    }

    private boolean retryForException(Throwable throwable) {
        if (throwable instanceof HttpClientErrorException clientErrorException) {
            HttpStatusCode statusCode = clientErrorException.getStatusCode();
            return retryableStatuses.contains(statusCode.value());

        } else if (throwable instanceof HttpServerErrorException serverErrorException) {
            HttpStatusCode statusCode = serverErrorException.getStatusCode();
            return retryableStatuses.contains(statusCode.value());
        }

        return false;
    }
}
