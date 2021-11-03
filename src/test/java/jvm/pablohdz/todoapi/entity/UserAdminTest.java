package jvm.pablohdz.todoapi.entity;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserAdminTest
{
    @Test
    void givenValueEntity_whenTryInstance_thenReturnFullEntity()
    {
        UserAdmin userAdmin = new UserAdmin("envy", "shine", "c8PSWRwR", "test@email.com");

        UUID actualApiKey = userAdmin.getApiKey();
        Timestamp actualCreatedAt = userAdmin.getCreatedAt();

        assertThat(actualApiKey)
                .withFailMessage("The Api Key cannot be null")
                .isNotNull();
        assertThat(actualCreatedAt)
                .withFailMessage("The created_at cannot be null")
                .isNotNull();
    }
}