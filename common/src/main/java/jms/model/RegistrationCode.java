package jms.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "registration_code")
@Getter
@Setter
public class RegistrationCode extends BaseEntity {

    @Column(name = "code_hash")
    private String codeHash;

    @Column(name = "date_generated")
    private LocalDateTime dateGenerated = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
