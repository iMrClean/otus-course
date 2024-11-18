package ru.otus.course.service;

import lombok.RequiredArgsConstructor;
import org.eclipse.jetty.security.AbstractLoginService;
import org.eclipse.jetty.security.RolePrincipal;
import org.eclipse.jetty.security.UserPrincipal;
import org.eclipse.jetty.util.security.Password;
import ru.otus.course.core.repository.UserDataTemplate;
import ru.otus.course.core.sessionmanager.TransactionManager;

import java.util.List;

@RequiredArgsConstructor
public class LoginServiceImpl extends AbstractLoginService {

  public static final String ROLE_NAME_USER = "user";

  public static final String ROLE_NAME_ADMIN = "admin";

  private final TransactionManager transactionManager;

  private final UserDataTemplate userDataTemplate;

  @Override
  protected List<RolePrincipal> loadRoleInfo(UserPrincipal user) {
    return List.of(new RolePrincipal(ROLE_NAME_ADMIN));
  }

  @Override
  protected UserPrincipal loadUserInfo(String username) {
    return transactionManager.doInReadOnlyTransaction(session ->
      userDataTemplate.findByLogin(session, username)
        .map(u -> new UserPrincipal(u.getLogin(), new Password(u.getPassword())))
        .orElse(null)
    );
  }

}
