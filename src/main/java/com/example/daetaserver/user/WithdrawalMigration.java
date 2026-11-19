package com.example.daetaserver.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

/** Retain DeleteUser and its table until all deployments have migrated successfully. */
@Component("withdrawalMigration")
public class WithdrawalMigration implements InitializingBean {
    @PersistenceContext
    private EntityManager entityManager;

    private final TransactionTemplate transactionTemplate;

    public WithdrawalMigration(PlatformTransactionManager transactionManager) {
        this.transactionTemplate = new TransactionTemplate(transactionManager);
    }

    @Override
    public void afterPropertiesSet() {
        // Idempotent: preserve both the source records and existing withdrawal timestamps.
        // Failure propagates and prevents the API from serving unmigrated users.
        transactionTemplate.executeWithoutResult(status -> entityManager.createQuery("""
                update User u
                set u.deleteAt = coalesce(
                    (select d.deleteAt from DeleteUser d where d.user = u), local datetime)
                where u.deleteAt is null
                  and exists (select d.id from DeleteUser d where d.user = u)
                """).executeUpdate());
    }
}
