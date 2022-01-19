package web.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.core.authority.AuthorityUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

// хэндлер, содержащий в себе алгоритм действий при успешной аутентификации
// например: пользователя с ролью админа отправляем на админку после логина, а с ролью юзер на главную страницу сайта и т.п.
public class LoginSuccessHandler implements AuthenticationSuccessHandler{

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (roles.contains("ADMIN")) {
            httpServletResponse.sendRedirect("/");
        } else {
            httpServletResponse.sendRedirect("/hello"); //страница конкретного пользователя
        }
    }
}
