package org.gamerbot.support.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DatabaseSupport {

    default ResultSet executeQuery(String query) {
        try {
            return Database.getInstance().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)
                    .executeQuery(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    default void execute(String query) {
        try {
            Database.getInstance().createStatement()
                    .execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
