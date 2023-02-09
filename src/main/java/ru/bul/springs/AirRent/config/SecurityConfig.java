package ru.bul.springs.AirRent.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.bul.springs.AirRent.services.PersonDetailsService;


@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PersonDetailsService personDetailsService;


    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    //какая страница отвечает за вход ошибки и тд тп
    //кофигурируем авторизация
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()//откл защиту от межсайтовый подделки запросов
            http.authorizeRequests()
                    .antMatchers("/admin/**").hasRole("ADMIN")//доступ только админу
                    .antMatchers("/pilot/**").hasAnyRole("ADMIN","PILOT")//доступ пилоту и админу
                .antMatchers("/auth/login","/auth/registration" ,"/error","/auth/forget","/AirlineBusiness/main",
                        "/AirlineBusiness/job").permitAll() //пишу что к этой странице могут попасть все
//                .anyRequest().authenticated() //на все страницы не впускаю не авторизованных пользователей





                    .anyRequest().hasAnyRole("USER","ADMIN","PILOT")//кто получает доступ к остальным стр
                .and()



                .formLogin().loginPage("/auth/login") //пишем наш html путь авторизации
                .loginProcessingUrl("/process_login") //наш post запрос
                .defaultSuccessUrl("/check/verif",true) //куда направлять после успешного входа
                .failureUrl("/auth/login?error") //даю параметр error если не успешно
                .and()


                .logout().logoutUrl("/logout").logoutSuccessUrl("/auth/login");



    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers(
                        "/style/**");
    }



    //настройка аутентификации
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(personDetailsService)
              .passwordEncoder(getPasswordEncoder()); //прогяем пароль через шифр
    }


    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
