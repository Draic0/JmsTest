package jms.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserDto {

    private Long id;

    private String phoneNumber;

    private String firstName;

    private String secondName;

    private LocalDate birthDate;

}
