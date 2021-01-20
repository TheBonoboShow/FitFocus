package be.spacedandy.FitFocus;

import be.spacedandy.FitFocus.security.BeforeAuthenticationFilter;
import be.spacedandy.FitFocus.services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@EnableWebSecurity
@Configuration
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/login", "/resources/**", "/css/**", "/fonts/**", "/img/***").permitAll()
                .antMatchers("/register", "/resources/**", "/css/**", "/fonts/**", "/img/***", "/js/**").permitAll()
                .antMatchers("/verify", "/resources/**", "/css/**", "/fonts/**", "/img/***", "/js/**").permitAll()
                .antMatchers("/index").permitAll()
                .antMatchers("/bookSession").permitAll()
                .antMatchers("/buySubscription").permitAll()
                .antMatchers("/calendar").permitAll()
                .antMatchers("/calendarYoga").permitAll()
                .antMatchers("/calendarAerobic").permitAll()
                .antMatchers("/calendarCycling").permitAll()
                .antMatchers("/calendarCardio").permitAll()
                .antMatchers("/calendarBoxing").permitAll()
                .antMatchers("/secret", "/resources/**", "/css/**", "/fonts/**", "/img/***", "/js/**").permitAll()
                .antMatchers("/verifysecret", "/resources/**", "/css/**", "/fonts/**", "/img/***", "/js/**").permitAll()
                .antMatchers("/prices").permitAll()
                .antMatchers("/contact").permitAll()
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/sessions").hasAnyAuthority("ADMIN", "COACH")
                .antMatchers("/sessions/findById/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .usernameParameter("username")
                .defaultSuccessUrl("/index")
                .failureHandler(new SimpleUrlAuthenticationFailureHandler() {

                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                                        AuthenticationException exception) throws IOException, ServletException {
                        super.setDefaultFailureUrl("/login?error");
                        super.onAuthenticationFailure(request, response, exception);
                        request.setAttribute("param", "error");
                    }
                })
                .and()
                .logout().invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout").permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/403");
    }

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(bCryptPasswordEncoder());

        return provider;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
