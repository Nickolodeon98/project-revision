import domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {
    UserDao userDao;
    User user1;
    User user2;
    User user3;

    @BeforeEach
    void setUp() {
        this.userDao = new UserDao(new AwsConnectionMaker());
        this.user1 = new User("273", "Seunghwan", "2810");
        this.user2 = new User("243", "Juhwan", "4912");
        this.user3 = new User("353", "Minsoo", "7123");
    }

    @DisplayName("Insert query")
    @Test
    void addAndGet() throws SQLException {
        userDao.add(user1);
        userDao.add(user2);
        userDao.add(user3);

        User user = userDao.select("273");

        assertEquals(user1.getName(), user.getName());
    }
}