package web.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

// обязателен для не boot-приложения. Кода в нем нет, но требуется для регистрации секьюрити в Спринг-контейнере
public class SpringSecurityInitializer extends AbstractSecurityWebApplicationInitializer {
    //пустой класс, использующийся для регистрации модуля в спринг-контейнере
}
