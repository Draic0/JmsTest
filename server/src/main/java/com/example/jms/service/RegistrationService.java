package com.example.jms.service;

import jms.dto.ActivateUserDto;
import jms.dto.UserDto;

public interface RegistrationService {

    UserDto register(UserDto userDto);

    boolean activate(ActivateUserDto activateUserDto);

}
