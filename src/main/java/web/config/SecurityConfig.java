package web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import web.service.UserServiceSecurity;

// настройка секьюрности по определенным URL, а также настройка UserDetails и GrantedAuthority
@Configuration
@EnableWebSecurity// Аннотацию @EnableWebSecurity необходимо прописывать при настройке аутентификации,
// а иначе, как сказано в документации, поведение будет непредсказуемым
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserServiceSecurity userServiceSecurity;

//    @Autowired
//    public SecurityConfig(UserServiceSecurity serviceSecurity) {
//        this.userServiceSecurity = serviceSecurity;
//    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("ADMIN").password("ADMIN").authorities("ADMIN")
//                .and().withUser("USER").password("USER").authorities("USER");
        auth.userDetailsService(userServiceSecurity);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                // указываем страницу с формой логина
                .loginPage("/login")
                //указываем логику обработки при логине
                .successHandler(new LoginSuccessHandler())
                // указываем action с формы логина
                .loginProcessingUrl("/login")
                // Указываем параметры логина и пароля с формы логина
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                // даем доступ к форме логина всем
                .permitAll();

        http.logout()
                // разрешаем делать логаут всем
                .permitAll()
                // указываем URL логаута
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                // указываем URL при удачном логауте
                .logoutSuccessUrl("/login?logout")
                //выключаем кроссдоменную секьюрность
                .and().csrf().disable();

        http.authorizeRequests() // предоставить разрешения для следующих url
                .antMatchers("/login").anonymous() //страницы аутентификаци доступна всем
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/user/**").hasAnyAuthority("ADMIN", "USER")
                .anyRequest().authenticated();
    }

    // Необходимо для шифрования паролей
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
