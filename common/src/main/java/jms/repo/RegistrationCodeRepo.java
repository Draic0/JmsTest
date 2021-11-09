package jms.repo;

import jms.model.RegistrationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistrationCodeRepo extends JpaRepository<RegistrationCode, Long> {

    List<RegistrationCode> getByUserId(Long userId);

}
