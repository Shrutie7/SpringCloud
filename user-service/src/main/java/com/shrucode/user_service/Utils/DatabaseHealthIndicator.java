package com.shrucode.user_service.Utils;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;


@Component
public class DatabaseHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        boolean isDBUp = checkDBConnection();
        return isDBUp ?
                Health.up().withDetail("DB","Available").build()
                : Health.down().withDetail("DB","Not Available").build();

    }
    private boolean checkDBConnection(){
        //in prod query the DB to chekc if DB up or not
        return true ;
    }
}
