package com.hti.pos;

import com.hti.pos.domain.Role;
import com.hti.pos.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.TransactionSystemException;

import javax.persistence.EntityManager;
import javax.persistence.TransactionRequiredException;
import javax.validation.*;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by ravuthz
 * Date : 12/4/2020, 1:14 PM
 * Email : ravuthz@gmail.com, yovannaravuth@gmail.com
 */

@SpringBootTest
public class RoleValidationTest {

    @Autowired
    private EntityManager manager;

    @Autowired
    private RoleRepository repository;

    private void validateRole(Role input) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Role>> violations = validator.validate(input);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    @Test
    public void whenRoleIsInvalidThenThrowsException1() {
        Role role = new Role();
        assertThrows(TransactionSystemException.class, () -> {
            repository.save(role);
            manager.flush();
        });
    }

    @Test
    public void whenRoleIsInvalidThenThrowsException2() {
        Role role = new Role();
        assertThrows(ConstraintViolationException.class, () -> {
            validateRole(role);
            repository.save(role);
            manager.flush();
        });
    }

    @Test
    public void whenRoleIsValid() {
        assertDoesNotThrow(() -> {
            Role role = new Role("valid-role");
            repository.save(role);

            assertThat(role).isNotNull()
                    .extracting("name").isEqualTo("valid-role");
        });
    }

    @Test
    public void whenRoleIsValidThenThrowsException() {
        Role role = new Role("valid-role-2");
        assertThrows(TransactionRequiredException.class, () -> {
            repository.save(role);
            manager.flush();
        });
    }

}