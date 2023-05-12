package com.chat.messenger.services.impl;

import com.chat.messenger.entities.Config;
import com.chat.messenger.model.dto.ConfigDto;
import com.chat.messenger.repos.ConfigRepository;
import com.chat.messenger.services.ConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ConfigServiceImpl implements ConfigService {

    private final ConfigRepository configRepository;

    public ConfigServiceImpl(ConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    @Override
    public ConfigDto getConfig() {
        Config config = configRepository.findAll().stream().findFirst().get();
        return new ConfigDto(config.getEnableClient());
    }
}
