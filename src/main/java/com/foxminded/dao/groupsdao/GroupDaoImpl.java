package com.foxminded.dao.groupsdao;

import com.foxminded.datasource.DataSource;
import com.foxminded.executescript.ExecuteScript;
import com.foxminded.groups.Group;
import org.h2.tools.RunScript;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupDaoImpl implements GroupDao {

    private final DataSource dataSource = new DataSource();

    @Override
    public void create(Group group) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO groups(group_name) VALUES (?)")) {
            preparedStatement.setString(1, group.getGroupName());
            preparedStatement.execute();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Group> findAll() {
        List<Group> allGroup = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT group_id , group_name FROM groups");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Group group = new Group();
                group.setGroupId(resultSet.getInt("group_id"));
                group.setGroupName(resultSet.getString("group_name"));
                allGroup.add(group);
            }
            return allGroup;
        } catch (SQLException | IOException e) {
            System.err.println("Failed to read data from database");
            e.printStackTrace();
        }
        return allGroup;
    }

    @Override
    public Group findId(int groupId) {
        Group group = new Group();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT group_id , group_name FROM groups WHERE group_id=?");) {
            preparedStatement.setInt(1, groupId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                group.setGroupId(resultSet.getInt("group_id"));
                group.setGroupName(resultSet.getString("group_name"));
            }
            return group;
        } catch (SQLException | IOException e) {
            System.err.println("Failed to read data from database");
            e.printStackTrace();
        }
        return group;
    }

    @Override
    public void update(Group group, int groupId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE groups SET group_name=? WHERE group_id=?")) {
            preparedStatement.setString(1, group.getGroupName());
            preparedStatement.setInt(2, groupId);
            preparedStatement.execute();
        } catch (SQLException | IOException e) {
            System.err.println("Changes did not take effect");
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int groupId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM groups WHERE group_id=?")) {
            preparedStatement.setInt(1, groupId);
            preparedStatement.execute();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public List<Group> findGroupsByNumberOfStudents(int numberStudent) {
        List<Group> groupList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT groups.group_id , groups.group_name ,COUNT(students.group_id)\n" +
                     "FROM groups groups\n" +
                     "LEFT JOIN students students ON groups.group_id = students.group_id\n" +
                     "GROUP BY groups.group_id , groups.group_name\n" +
                     "HAVING COUNT(students.group_id)<=?")) {
            preparedStatement.setInt(1, numberStudent);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Group group = new Group();
                group.setGroupId(resultSet.getInt("group_id"));
                group.setGroupName(resultSet.getString("group_name"));
                groupList.add(group);
            }
            return groupList;
        } catch (SQLException | IOException e) {
            System.err.println("Failed to read data from database");
            e.printStackTrace();
        }
        return groupList;
    }

    public void createTableGroup() throws SQLException, IOException {
        new ExecuteScript().runScript("createTableGroups.sql",dataSource.getConnection());
    }

    public void deleteTableGroup() throws SQLException, IOException {
        Statement statement = dataSource.getConnection().createStatement();
        statement.execute("DROP TABLE groups;");
    }
}
