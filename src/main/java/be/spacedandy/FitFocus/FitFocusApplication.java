package be.spacedandy.FitFocus;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import it.ozimov.springboot.mail.configuration.EnableEmailTools;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableEncryptableProperties
public class FitFocusApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(FitFocusApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(FitFocusApplication.class, args);
	}

}
