package jms.service.impl;

import jms.consts.MyConstants;
import jms.dto.ActivateUserDto;
import jms.exception.NotFoundException;
import jms.model.RegistrationCode;
import jms.model.User;
import jms.repo.RegistrationCodeRepo;
import jms.repo.UserRepo;
import jms.service.RegistrationCodeService;
import jms.util.MessageUtil;
import jms.util.UuidUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationCodeServiceImpl implements RegistrationCodeService, MyConstants {

    private final UserRepo userRepo;
    private final RegistrationCodeRepo registrationCodeRepo;

    private final PasswordEncoder passwordEncoder;

    private final MessageUtil messageUtil;

    @Override
    @RabbitListener(queues = GENERATE_CODE_QUEUE, returnExceptions = "true")
    public String generateCode(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(
                () -> new NotFoundException(messageUtil.getMessage("user.not.found", userId)));

        String code = generate(user);

        RegistrationCode registrationCode = new RegistrationCode();
        registrationCode.setUser(user);
        registrationCode.setCodeHash(passwordEncoder.encode(code));
        registrationCodeRepo.save(registrationCode);

        return code;
    }

    @Override
    @RabbitListener(queues = ACTIVATE_USER_QUEUE, returnExceptions = "true")
    public boolean activate(ActivateUserDto message) {
        User user = userRepo.findById(message.getUserId()).orElseThrow(
                () -> new NotFoundException(messageUtil.getMessage("user.not.found", message.getUserId())));

        List<RegistrationCode> codes = registrationCodeRepo.getByUserId(message.getUserId());
        for (RegistrationCode code: codes) {
            if (isCodeValid(code) && passwordEncoder.matches(message.getCode(), code.getCodeHash())) {
                user.setActive(true);
                userRepo.save(user);
                return true;
            }
        }

        return false;
    }

    private String generate(User user) {
        return UuidUtil.generateFromPhoneNumber(user.getPhoneNumber());
    }

    private boolean isCodeValid(RegistrationCode registrationCode) {
        return registrationCode.getDateGenerated().plusHours(HOURS_VALID).isAfter(LocalDateTime.now());
    }

}
