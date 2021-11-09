package jms.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User extends BaseEntity {

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "active")
    private Boolean active = false;

}
