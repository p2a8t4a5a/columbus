/*
 * Copyright 2018 https://github.com/n3xtdata
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.n3xtdata.columbus.connectors.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ResultSetConverter {

  public static List<Map<String, Object>> convertResultSet(ResultSet resultSet) throws SQLException {

    int columnCount = resultSet.getMetaData().getColumnCount();

    List<Map<String, Object>> queryResult = new ArrayList<>();

    do {

      Map<String, Object> row = new HashMap<>();

      for (int i = 1; i <= columnCount; i++) {

        row.put(resultSet.getMetaData().getColumnLabel(i).toLowerCase(), resultSet.getObject(i));

      }

      queryResult.add(row);

    } while (resultSet.next());

    return queryResult;

  }

}