package QrScannerAPI.QrScannerAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

//główna klasa uruchamiajaca API
@SpringBootApplication
public class QrScannerApiApplication  extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(QrScannerApiApplication .class);
	}

	public static void main(String[] args) {
	    SpringApplication.run(QrScannerApiApplication.class, args);
	}
}
