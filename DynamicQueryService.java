import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DynamicQueryService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DynamicQueryService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Method to execute any SELECT query and return a list of maps
    public List<Map<String, Object>> executeSelectQuery(String sql, Object[] params) {
        return jdbcTemplate.query(sql, params, (ResultSet rs, int rowNum) -> {
            Map<String, Object> row = new HashMap<>();
            int columnCount = rs.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                row.put(rs.getMetaData().getColumnName(i), rs.getObject(i));
            }
            return row;
        });
    }
}
