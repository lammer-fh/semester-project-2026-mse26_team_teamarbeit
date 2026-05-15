package team_teamarbeit.backend.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import team_teamarbeit.backend.service.DatabaseInitService;

@Configuration
public class DatabaseInitConfig {
    
    @Bean
    public CommandLineRunner initData(DatabaseInitService dbInitService) {
        return args -> {
            dbInitService.initRoomExtras();
            dbInitService.initRoomTypes();
            dbInitService.initRooms();
        };
    }
}
