package MBPackage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Application {

    public static void main(String[] args)
    {
        JSONInitializer.init();
        SpringApplication.run(Application.class, args);
    }
}
