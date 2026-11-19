package com.example.daetaserver.user;

import com.example.daetaserver.deleteUser.DeleteUser;
import com.example.daetaserver.global.error.CustomException;
import com.example.daetaserver.global.error.ErrorCode;
import com.example.daetaserver.global.security.JWT.TokenManager;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DataJpaTest
@Import({WithdrawalMigration.class, UserService.class})
class WithdrawalTests {
    @Autowired EntityManager entityManager;
    @Autowired WithdrawalMigration migration;
    @Autowired UserService service;
    @MockitoBean TokenManager tokenManager;

    private User user(String id, LocalDateTime deletedAt) {
        User user = User.builder().id(id).name(id).email(id + "@example.test")
                .createdAt(LocalDateTime.of(2025, 1, 1, 0, 0)).deleteAt(deletedAt).build();
        entityManager.persist(user);
        return user;
    }

    private void assertRejected(String id) {
        when(tokenManager.getSubject("token")).thenReturn(id);
        CustomException exception = assertThrows(CustomException.class, () -> service.getMe("token"));
        assertEquals(ErrorCode.DELETE_USER, exception.getErrorCode());
    }

    @Test
    void migratesLegacyWithdrawalsAndRetainsSourceWithoutChangingActiveUsers() {
        LocalDateTime original = LocalDateTime.of(2025, 2, 3, 4, 5);
        User withdrawn = user("withdrawn", null);
        User undated = user("undated", null);
        User alreadyMigrated = user("existing", original.plusDays(1));
        user("active", null);
        entityManager.persist(DeleteUser.builder().user(withdrawn).deleteAt(original).build());
        entityManager.persist(DeleteUser.builder().user(undated).build());
        entityManager.persist(DeleteUser.builder().user(alreadyMigrated).deleteAt(original).build());
        entityManager.flush();

        migration.afterPropertiesSet();
        entityManager.clear();
        assertEquals(original, entityManager.find(User.class, "withdrawn").getDeleteAt());
        LocalDateTime fallback = entityManager.find(User.class, "undated").getDeleteAt();
        assertNotNull(fallback);
        assertEquals(original.plusDays(1), entityManager.find(User.class, "existing").getDeleteAt());
        assertNull(entityManager.find(User.class, "active").getDeleteAt());
        assertEquals(3L, entityManager.createQuery("select count(d) from DeleteUser d", Long.class).getSingleResult());
        assertRejected("withdrawn");
        assertRejected("undated");

        migration.afterPropertiesSet();
        entityManager.clear();
        assertEquals(fallback, entityManager.find(User.class, "undated").getDeleteAt());
        when(tokenManager.getSubject("token")).thenReturn("active");
        assertEquals("active", service.getMe("token").name());
    }

    @Test
    void withdrawalPersistsAndMakesGetMeRejectTheUser() {
        user("new", null);
        user("other", null);
        when(tokenManager.getSubject("token")).thenReturn("new");
        service.withdraw("token");
        entityManager.flush();
        entityManager.clear();
        LocalDateTime timestamp = entityManager.find(User.class, "new").getDeleteAt();
        assertNotNull(timestamp);
        assertRejected("new");
        service.withdraw("token");
        entityManager.flush();
        entityManager.clear();
        assertEquals(timestamp, entityManager.find(User.class, "new").getDeleteAt());
        assertNull(entityManager.find(User.class, "other").getDeleteAt());
    }

    @Test
    void withdrawalOfUnknownUserReturnsNotFound() {
        when(tokenManager.getSubject("token")).thenReturn("missing");
        CustomException exception = assertThrows(CustomException.class, () -> service.withdraw("token"));
        assertEquals(ErrorCode.NOT_FOUND_USER, exception.getErrorCode());
    }
}
