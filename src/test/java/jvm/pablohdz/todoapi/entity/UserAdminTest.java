package jvm.pablohdz.todoapi.entity;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserAdminTest
{
    @Test
    void givenValueEntity_whenTryInstance_thenReturnFullEntity()
    {
        UserAdmin userAdmin = new UserAdmin("envy", "admin123", "shine", "c8PSWRwR", "test@email" +
                ".com");

        String actualApiKey = userAdmin.getApiKey();
        Timestamp actualCreatedAt = userAdmin.getCreatedAt();

        assertThat(actualApiKey)
                .withFailMessage("The Api Key cannot be null")
                .isNotNull();
        assertThat(actualCreatedAt)
                .withFailMessage("The created_at cannot be null")
                .isNotNull();
    }
}