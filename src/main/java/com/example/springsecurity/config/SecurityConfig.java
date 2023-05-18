package com.example.springsecurity.config;

import com.example.springsecurity.services.PersonDetailsService;
import org.springframework.cglib.proxy.NoOp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig{
    private final PersonDetailsService personDetailsService;

    @Bean
    public PasswordEncoder getPasswordEncode(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        конфигурируем работу Спринг Секьюрити
        httpSecurity//csrf().disable() //откл. защиту от межсайтовой подделки запросов
                .authorizeHttpRequests() //указываем какие строины должны быть защищены ауторизацией
                .requestMatchers("/authentication", "/error", "/registration","/recouces/**",
                        "/static/**","/css/**","/js/**", "/img/**").permitAll()
                //указываем что не
        // аутентифицированные пользователи могут зайти на страницу аутентификации и на объект
        // ошибки с помощью permitAll указываем что не ауктентифицированные пользователи могут
        // заходить на перечисленные странцы
                .requestMatchers("/admin").hasRole("ADMIN") // указываем на то, что страница
                // /admin доступна пользователям с ролью ADMIN
                .anyRequest().hasAnyRole("USER","ADMIN")
//                .anyRequest().authenticated()
        // указываем что для всех остальных страниц необходимо вызывать метод authentivates(),
        // который открывает форму аутентификации
                .and()// указываем что дальше настривается аутентификация и соединяем её с
        // настройкой доступа
                .formLogin().loginPage("/authentication") //указываем какой url запрос будет
        // отправляться при заходе на защищённые страницы
                .loginProcessingUrl("/process_login")
                // на какой url отправляются данные формы. Нам уже не нужно будет создавать метод
        // в контроллере и обрабатывать данные с формы. Мы задали url, который используется по
        // умолчанию для обработки формы аутентификации по средствам Spring Security. Spring
        // Security будет ждать объект с формы аутентификации и затем сверять логин и пароль с
        // данными в БД
                .defaultSuccessUrl("/index", true) // указываем на какой url необходимо направить
        // пользователя после успешной аутентификации. Вторым аргументом указывется true, чтобы
        // перенаправление шло в любом случае после успешной конфигурации
                .failureUrl("/authentication?error")
//        указываем куда необходимо перенаправить пользователя при проваленной ауктентификации. В
//        запрос будет передан объект error, который будет проверяться на форме и при наличии
//        данного объект в запросе выводится сообщение "Неправильный логин или пароль"
        .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/authentication");
        return httpSecurity.build();
    }
    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    //    private final AuthenticationProvider authenticationProvider;
//
//    public SecurityConfig(AuthenticationProvider authenticationProvider) {
//        this.authenticationProvider = authenticationProvider;
//    }

    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//        authenticationManagerBuilder.authenticationProvider(authenticationProvider);
    authenticationManagerBuilder.userDetailsService(personDetailsService)
            .passwordEncoder(getPasswordEncode());
    }
}
