/**
 * Yobi, Project Hosting SW
 *
 * Copyright 2015 NAVER Corp.
 * http://yobi.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package data.exchangers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import data.DefaultExchanger;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Keeun Baik
 */
public class UserProjectNotificationDataExchanger extends DefaultExchanger {

    private static final String ID = "id"; // BIGINT(19) NOT NULL
    private static final String USER_ID = "user_id"; // BIGINT(19)
    private static final String PROJECT_ID = "project_id"; // BIGINT(19)
    private static final String NOTIFICATION_TYPE = "notification_type"; // VARCHAR(255)
    private static final String ALLOWED = "allowed"; // BOOLEAN(1)

    @Override
    protected void setPreparedStatement(PreparedStatement ps, JsonNode node) throws SQLException {
        short index = 1;
        ps.setLong(index++, node.get(ID).longValue());
        ps.setLong(index++, node.get(USER_ID).longValue());
        ps.setLong(index++, node.get(PROJECT_ID).longValue());
        ps.setString(index++, node.get(NOTIFICATION_TYPE).textValue());
        ps.setBoolean(index++, node.get(ALLOWED).booleanValue());
    }

    @Override
    protected void setNode(JsonGenerator generator, ResultSet rs) throws IOException, SQLException {
        short index = 1;
        putLong(generator, ID, rs, index++);
        putLong(generator, USER_ID, rs, index++);
        putLong(generator, PROJECT_ID, rs, index++);
        putString(generator, NOTIFICATION_TYPE, rs, index++);
        putBoolean(generator, ALLOWED, rs, index++);
    }

    @Override
    public String getTable() {
        return "USER_PROJECT_NOTIFICATION";
    }

    @Override
    protected String getInsertSql() {
        return "INSERT INTO USER_PROJECT_NOTIFICATION (ID, USER_ID, PROJECT_ID, " +
                "NOTIFICATION_TYPE, ALLOWED)" + values(5);
    }

    @Override
    protected String getSelectSql() {
        return "SELECT ID, USER_ID, PROJECT_ID, NOTIFICATION_TYPE, ALLOWED " +
                "FROM USER_PROJECT_NOTIFICATION";
    }
}
