/*
 * Copyright 2018 https://github.com/n3xtdata
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.n3xtdata.columbus.loader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.n3xtdata.columbus.ColumbusApplicationTests;
import com.n3xtdata.columbus.core.Check;
import com.n3xtdata.columbus.core.Component;
import com.n3xtdata.columbus.core.JdbcConnection;
import com.n3xtdata.columbus.core.SshConnection;
import java.io.FileNotFoundException;
import java.util.HashMap;
import org.junit.Test;

public class GenericYamlLoaderTests extends ColumbusApplicationTests {

  @Test
  public void shouldLoadCheckFile() throws FileNotFoundException {

    GenericYamlLoader<Check> checkFileLoader = new GenericYamlLoader<>(new Check());

    HashMap<String, Check> checkHashMap = checkFileLoader
        .load("src/test/resources/generic-yaml-loader-tests/checks/test1.yml");

    Check check = checkHashMap.get("test-1");

    Component component = new Component("componentLabel", "jdbc", "jdbc-sqllite", "SELECT * FROM dummy");

    assertEquals(check.getDescription(), "a short description ...");
    assertEquals(1, check.getComponents().size());
    assertTrue(check.getComponents().contains(component));
    assertEquals("src/test/resources/generic-yaml-loader-tests/checks/test1.yml", check.getPath());

  }

  @Test
  public void shouldOnlyLoadValidatedCheckFile() throws FileNotFoundException {

    GenericYamlLoader<Check> checkFileLoader = new GenericYamlLoader<>(new Check());

    HashMap<String, Check> checkHashMap = checkFileLoader
        .load("src/test/resources/generic-yaml-loader-tests/checks/test2.yml");

    assertEquals(checkHashMap.size(), 2);
  }

  @Test
  public void shouldLoadJdbcConnectionFile() throws FileNotFoundException {

    GenericYamlLoader<JdbcConnection> jdbcLoader = new GenericYamlLoader<>(new JdbcConnection());

    HashMap<String, JdbcConnection> jdbcConnectionsHashMap = jdbcLoader
        .load("src/test/resources/generic-yaml-loader-tests/connections/jdbc/test1.yml");

    JdbcConnection jdbcConnection = jdbcConnectionsHashMap.get("jdbc-sqllite");

    assertEquals(jdbcConnection.getUrl(), "jdbc:sqlite:src/test/resources/jdbc-tests/test.db");
    assertNotNull(jdbcConnection.getDriverJar());
    assertEquals("src/test/resources/generic-yaml-loader-tests/connections/jdbc/test1.yml", jdbcConnection.getPath());


  }


  @Test
  public void shouldLoadSshConnectionFile() throws FileNotFoundException {

    GenericYamlLoader<SshConnection> sshLoader = new GenericYamlLoader<>(new SshConnection());

    HashMap<String, SshConnection> sshConnectionsHashMap = sshLoader
        .load("src/test/resources/generic-yaml-loader-tests/connections/ssh/test1.yml");

    SshConnection sshConnection = sshConnectionsHashMap.get("ssh-test");

    assertEquals(sshConnection.getUsername(), "user");
    assertEquals("src/test/resources/generic-yaml-loader-tests/connections/ssh/test1.yml", sshConnection.getPath());
  }

}