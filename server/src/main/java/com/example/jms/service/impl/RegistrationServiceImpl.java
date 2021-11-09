package com.example.jms.service.impl;

import com.example.jms.mapper.UserMapper;
import com.example.jms.service.RegistrationService;
import jms.consts.MyConstants;
import jms.dto.ActivateUserDto;
import jms.dto.UserDto;
import jms.model.User;
import jms.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationServiceImpl implements RegistrationService, MyConstants {

    private final RabbitTemplate rabbitTemplate;

    private final UserRepo userRepo;

    private final UserMapper userMapper;


    @Override
    public UserDto register(UserDto userDto) {
        User user = userMapper.toModel(userDto, new User());
        user = userRepo.save(user);

        String code = (String) rabbitTemplate.convertSendAndReceive(TOPIC_EXCHANGE_NAME, ROUTING_KEY_GENERATE, user.getId());

        log.info("CODE GENERATED: " + code);
        return userMapper.toDto(user);
    }

    @Override
    public boolean activate(ActivateUserDto activateUserDto) {
        boolean success = (boolean) rabbitTemplate.convertSendAndReceive(TOPIC_EXCHANGE_NAME, ROUTING_KEY_ACTIVATE, activateUserDto);
        if (success) {
            log.info("ACTIVATION SUCCESS!");
        } else {
            log.info("ACTIVATION FAILURE!");
        }
        return success;
    }

}
