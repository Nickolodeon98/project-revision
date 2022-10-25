import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserDaoFactory {
    @Bean
    public UserDao awsUserDao() {
        return new UserDao(new AwsConnectionMaker());
    }
    @Bean
    public UserDao localUserDao() {
        return new UserDao(new LocalConnectionMaker());
    }

    public static void main(String[] args) {

    }
}
