package jms.service;

import jms.dto.ActivateUserDto;

public interface RegistrationCodeService {

    Integer HOURS_VALID = 2;

    String generateCode(Long userId);

    boolean activate(ActivateUserDto message);

}
