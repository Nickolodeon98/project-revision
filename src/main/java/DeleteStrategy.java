import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteStrategy implements StatementStrategy{
    @Override
    public PreparedStatement useStrategy(Connection conn) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("DELETE FROM users");
        return ps;
    }

}
