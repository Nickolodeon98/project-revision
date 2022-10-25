import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;

@Configuration
public class UserDaoFactory {
    @Bean
    public UserDao awsUserDao() {
        return new UserDao(awsDataSource());
    }
    @Bean
    public UserDao localUserDao() {
        return new UserDao(localDataSource());
    }

    @Bean
    public DataSource awsDataSource() {
        SimpleDriverDataSource simpleDriverDataSource = new SimpleDriverDataSource();
        Map<String, String> env = System.getenv();
        simpleDriverDataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
        simpleDriverDataSource.setUrl(env.get("DB_HOST"));
        simpleDriverDataSource.setUsername(env.get("DB_NAME"));
        simpleDriverDataSource.setPassword(env.get("DB_PASSWORD"));
        return simpleDriverDataSource;
    }

    @Bean
    public DataSource localDataSource() {
        SimpleDriverDataSource simpleDriverDataSource = new SimpleDriverDataSource();
        Map<String, String> env = System.getenv();
        simpleDriverDataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
        simpleDriverDataSource.setUrl(env.get("DB_HOST"));
        simpleDriverDataSource.setUsername(env.get("DB_NAME"));
        simpleDriverDataSource.setPassword(env.get("DB_PASSWORD"));
        return simpleDriverDataSource;
    }
}
