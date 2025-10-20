package eci.edu.dosw.taller.TallerEvaluativo_C2_DOSW_1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "eci.edu.dosw.taller")
public class TallerEvaluativoC2Dosw1Application {

	public static void main(String[] args) {
		SpringApplication.run(TallerEvaluativoC2Dosw1Application.class, args);
	}

}
