package be.spacedandy.FitFocus;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@EnableEncryptableProperties
@SpringBootApplication
public class FitFocusApplication {

	public static void main(String[] args) {
		SpringApplication.run(FitFocusApplication.class, args);

	}

}
