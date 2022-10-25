import domain.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Date;
import java.util.Map;

public class UserDao {
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    public UserDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.dataSource = dataSource;

    }


    public void add(User user) throws SQLException {
        this.jdbcTemplate.update("INSERT INTO users (id, name, password) VALUES (?, ?, ?);",
                user.getId(), user.getName(), user.getPassword());
    }

    public void executeQuery() {

    }

    public User findById(String id) throws SQLException {
        String sql = "SELECT id, name, password FROM users WHERE id = ?";
        return this.jdbcTemplate.queryForObject(sql,
                (rs, count) -> new User(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("password")),
                id);
    }

    public void deleteAll() {
        this.jdbcTemplate.update("DELETE FROM users");
    }

    public int getCount() {
        return this.jdbcTemplate.queryForObject("SELECT count(*) FROM users;", Integer.class);
    }


}
