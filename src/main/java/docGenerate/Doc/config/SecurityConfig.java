package docGenerate.Doc.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
public class SecurityConfig {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf(csrf ->
                        csrf.disable()) // Отключение CSRF защиты
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                // Разрешаем доступ для всех авторизованных пользователей к URL /templates и /documents и их дочерним URL
                                .requestMatchers("/templates/**", "/documents/**","/user/**").authenticated()
                                // Запрещаем доступ к URL /admin и его дочерним URL для всех кроме пользователей с ролью "ADMIN"
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                // Все остальные URL не требуют аутентификации
                                .anyRequest().permitAll()
                )
                .formLogin(withDefaults()
                ); // Настройки формы входа по умолчанию

        return http.build();
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }



}
