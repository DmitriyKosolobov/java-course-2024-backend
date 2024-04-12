package edu.java.scrapper.service.sender;

import edu.java.scrapper.controller.dto.UpdateRequest;

public interface SenderNotification {
    void send(UpdateRequest updateRequest);
}
