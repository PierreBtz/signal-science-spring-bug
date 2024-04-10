package eu.pierrebeitz.signalsciencespringbug;

import java.util.Map;

import com.signalsciences.jakartafilter.SigSciFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class SignalScienceSpringBugApplication {

    public static void main(String[] args) {
        SpringApplication.run(SignalScienceSpringBugApplication.class, args);
    }

    @Configuration
    public static class SignalSciencesConfig {

        @Bean
        public FilterRegistrationBean<SigSciFilter> signalSciencesRegistration() {
            var filterRegistration = new FilterRegistrationBean<SigSciFilter>();
            filterRegistration.setFilter(new SigSciFilter());
            filterRegistration.setAsyncSupported(true);
            filterRegistration.setInitParameters(Map.of("rpcServerURI", "unix:/sigsci/tmp/sigsci.sock"));
            filterRegistration.addUrlPatterns("/*");
            filterRegistration.setOrder(1);
            return filterRegistration;
        }
    }

}
