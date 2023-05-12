package com.chat.messenger.model.response;

import lombok.Data;

@Data
public class DeleteAccountResponse {

    private boolean successful;

    public DeleteAccountResponse(boolean successful) {
        this.successful = successful;
    }
}
