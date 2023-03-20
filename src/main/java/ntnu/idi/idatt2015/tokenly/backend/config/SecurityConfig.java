package ntnu.idi.idatt2015.tokenly.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .securityMatchers((matchers) -> matchers
                        .requestMatchers("/h2-console/**", "/"))
                .csrf((csrf) -> csrf.ignoringRequestMatchers("/h2-console/**"))
                .headers((headers) -> headers.frameOptions().sameOrigin())
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().authenticated())
                /*.formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll())*/
                .httpBasic();
         return http.build();
    }

    @Bean
    public PasswordEncoder encoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .generateUniqueName(false)
                .setName("tokenly-db")
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .setType(EmbeddedDatabaseType.H2)
                .addScript("db/sql/dbschema.sql")
                .addScript("db/sql/testdata.sql")
                .build();
    }

    @Bean
    public JdbcUserDetailsManager users(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        UserDetails user = User.builder()
                .username("admin")
                .password(encoder().encode("pw"))
                .roles("USER", "ADMIN")
                .build();
        jdbcUserDetailsManager.createUser(user);
        return jdbcUserDetailsManager;
    }

    /*@Bean
    public UserDetailsManager users(DataSource dataSource) {
        String userPw =  encoder().encode("userpw");
        System.out.println(userPw);
        UserDetails user = User.builder()
                .username("user")
                .password(userPw)
                .roles("USER")
                .build();
        String adminPw = encoder().encode("adminpw");
        System.out.println(adminPw);
        UserDetails admin = User.builder()
                .username("admin")
                .password(adminPw)
                .roles("USER","ADMIN")
                .build();

        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        users.createUser(user);
        users.createUser(admin);
        return users;
    }*/
}