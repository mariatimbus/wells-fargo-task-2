package com.wellsfargo.counselor.config;

import com.wellsfargo.counselor.entity.Advisor;
import com.wellsfargo.counselor.repository.AdvisorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedAdvisor(AdvisorRepository advisorRepository) {
        return args -> {
            if (advisorRepository.count() == 0) {
                advisorRepository.save(new Advisor(
                        "Default",
                        "Advisor",
                        "1 Market Street, San Francisco, CA",
                        "415-555-0100",
                        "advisor@wellsfargo.example"
                ));
            }
        };
    }
}
