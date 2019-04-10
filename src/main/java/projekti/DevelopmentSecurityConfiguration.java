package projekti;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableJpaRepositories(basePackages = "repositories")
@EntityScan(basePackages = "entities")
public class DevelopmentSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity sec) throws Exception {
        // Pyyntöjä ei tarkasteta
        sec.ignoring().antMatchers("/**");
    }
}
