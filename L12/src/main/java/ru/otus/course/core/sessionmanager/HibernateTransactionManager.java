package ru.otus.course.core.sessionmanager;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.concurrent.Callable;

@RequiredArgsConstructor
public class HibernateTransactionManager implements TransactionManager {

  private final SessionFactory sessionFactory;

  @Override
  public <T> T doInTransaction(TransactionAction<T> action) {
    return doInTransaction(action, Boolean.FALSE);
  }

  @Override
  public <T> T doInReadOnlyTransaction(TransactionAction<T> action) {
    return doInTransaction(action, Boolean.TRUE);
  }

  private <T> T doInTransaction(TransactionAction<T> action, Boolean readOnly) {
    return wrapException(() -> {
      try (var session = sessionFactory.openSession()) {
        configureSessionForTransaction(session, readOnly);
        var transaction = session.beginTransaction();
        try {
          var result = action.apply(session);
          transaction.commit();
          return result;
        } catch (Exception e) {
          transaction.rollback();
          throw e;
        }
      }
    });
  }

  private void configureSessionForTransaction(Session session, Boolean readOnly) {
    if (readOnly) {
      session.setDefaultReadOnly(Boolean.TRUE);
    }
  }

  private <T> T wrapException(Callable<T> action) {
    try {
      return action.call();
    } catch (Exception e) {
      throw new DataBaseOperationException("Exception in transaction", e);
    }
  }

}
