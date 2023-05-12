package com.chat.messenger.services;

import com.chat.messenger.model.request.LoginRequest;
import com.chat.messenger.model.request.RegisterRequest;
import com.chat.messenger.model.response.DeleteAccountResponse;
import com.chat.messenger.model.response.LoginResponse;

public interface UserService {

    LoginResponse login(LoginRequest request);

    LoginResponse register(RegisterRequest request);

    DeleteAccountResponse deleteAccount(String username);
}
