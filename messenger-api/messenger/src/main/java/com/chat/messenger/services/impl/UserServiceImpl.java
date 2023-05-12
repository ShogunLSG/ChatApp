package com.chat.messenger.services.impl;

import com.chat.messenger.entities.Message;
import com.chat.messenger.entities.User;
import com.chat.messenger.model.dto.UserDto;
import com.chat.messenger.model.request.LoginRequest;
import com.chat.messenger.model.request.RegisterRequest;
import com.chat.messenger.model.response.DeleteAccountResponse;
import com.chat.messenger.model.response.LoginResponse;
import com.chat.messenger.repos.MessageRepository;
import com.chat.messenger.repos.UserRepository;
import com.chat.messenger.services.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    public UserServiceImpl(UserRepository userRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        Optional<User> optionalUser = userRepository.findByUsernameAndPassword(
                request.getUsername().toLowerCase(),
                request.getPassword()
        );
        User user = optionalUser.orElse(null);
        boolean isLoggedIn = user != null;
        UserDto userDto = isLoggedIn ? new UserDto(user.getUsername()) : null;
        return new LoginResponse(isLoggedIn, null, userDto);
    }

    @Override
    public LoginResponse register(RegisterRequest request) {
        String username = request.getUsername().toLowerCase();

        if (userRepository.existsById(username)) {
            return new LoginResponse(false, "Username is already taken", null);
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(request.getPassword());
        userRepository.save(user);

        return new LoginResponse(true, "Registration successful", new UserDto(username));
    }

    @Override
    public DeleteAccountResponse deleteAccount(String username) {
        userRepository.deleteById(username.toLowerCase());

        List<Message> messages =  messageRepository.findAllByUsername(username.toLowerCase());
        if (messages.size() > 0) {
            messages.forEach(message -> {
                message.setUsername("Messenger");
                message.setMessage("This message has been removed");
            });
            messageRepository.saveAll(messages);
        }

        return new DeleteAccountResponse(!userRepository.existsById(username.toLowerCase()));
    }
}