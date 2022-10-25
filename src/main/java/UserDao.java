import domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.*;
import java.util.Map;

public class UserDao {
    private ConnectionMaker connectionMaker;

    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void add(User user) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = connectionMaker.makeConnection();
            ps = conn.prepareStatement("INSERT INTO users VALUES (?, ?, ?)");
            ps.setString(1, user.getId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPassword());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (ps != null) try {
                ps.close();
            } catch (SQLException e) {
            }
            if (conn != null) try {
                conn.close();
            } catch (SQLException e) {
            }
        }
    }

    public User select(String id) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;

        try {
            conn = connectionMaker.makeConnection();
            ps = conn.prepareStatement("SELECT * FROM users WHERE id = ?");
            ps.setString(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (SQLException e) {
            }
            if (ps != null) try {
                ps.close();
            } catch (SQLException e) {
            }
            if (conn != null) try {
                conn.close();
            } catch (SQLException e) {
            }
        }

        if (user == null) throw new EmptyResultDataAccessException(1);
        return user;
    }

    public void deleteAll() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = connectionMaker.makeConnection();
            ps = conn.prepareStatement("DELETE FROM users");
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (ps != null) try {
                ps.close();
            } catch (SQLException e) {
            }
            if (conn != null) try {
                conn.close();
            } catch (SQLException e) {
            }
        }
    }

    public int getCount() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectionMaker.makeConnection();
            ps = conn.prepareStatement("SELECT count(*) FROM users");
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("count(*)");
            } else throw new EmptyResultDataAccessException(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (SQLException e) {
            }
            if (ps != null) try {
                ps.close();
            } catch (SQLException e) {
            }
            if (conn != null) try {
                conn.close();
            } catch (SQLException e) {
            }
        }
    }

    public static void main(String[] args) {

    }
}
