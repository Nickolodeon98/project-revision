import domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes=UserDaoFactory.class)
class UserDaoTest {

    @Autowired
    ApplicationContext context;
    UserDao userDao;
    User user1;
    User user2;
    User user3;

    @BeforeEach
    void setUp() {
        this.userDao = context.getBean("awsUserDao", UserDao.class);
        this.user1 = new User("1", "Seunghwan", "2810");
        this.user2 = new User("2", "Juhwan", "4912");
        this.user3 = new User("3", "Minsoo", "7123");
    }

    @DisplayName("Insert query")
    @Test
    void addAndGet() throws SQLException {
        userDao.deleteAll();
        userDao.add(user1);
        userDao.add(user2);
        userDao.add(user3);

        User user = userDao.select("1");

        assertEquals(user1.getName(), user.getName());

        assertThrows(EmptyResultDataAccessException.class, () -> userDao.select("4"));
    }

    @DisplayName("Delete")
    @Test
    void deleteAll() {
        userDao.deleteAll();
        assertEquals(0, userDao.getCount());
    }

    @DisplayName("Count")
    @Test
    void getCount() throws SQLException {
        userDao.deleteAll();
        userDao.add(user1);
        userDao.add(user2);
        userDao.add(user3);
        assertEquals(3, userDao.getCount());
    }
}