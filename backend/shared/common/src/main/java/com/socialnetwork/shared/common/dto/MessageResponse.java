package com.socialnetwork.shared.common.dto;

import lombok.Data;

@Data
public final class MessageResponse {
    private String message;

    private MessageResponse() {
    }

    public static MessageResponse create(String message) {
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage(message);
        return messageResponse;
    }
}
