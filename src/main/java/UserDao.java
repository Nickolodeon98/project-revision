import domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

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

    RowMapper<User> rowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User(rs.getString("id"), rs.getString("name"),
                    rs.getString("password"));
            return user;
        }
    };

    public User findById(String id) throws SQLException {
        String sql = "SELECT id, name, password FROM users WHERE id = ?";
        return this.jdbcTemplate.queryForObject(sql,rowMapper,id);
    }

    public void deleteAll() {
        this.jdbcTemplate.update("DELETE FROM users");
    }

    public int getCount() {
        return this.jdbcTemplate.queryForObject("SELECT count(*) FROM users;", Integer.class);
    }

    public List<User> getAll() {
        String sql = "SELECT * FROM users";
        return this.jdbcTemplate.query(sql, rowMapper);
    }
}
