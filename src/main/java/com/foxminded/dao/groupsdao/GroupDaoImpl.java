package com.foxminded.dao.groupsdao;

import com.foxminded.datasource.DataSource;
import com.foxminded.groups.Group;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupDaoImpl implements GroupDao {

    private final DataSource dataSource = new DataSource();

    @Override
    public void create(Group group) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO groups SET groupName=?")) {
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
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT groupId , groupName FROM groups");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Group group = new Group();
                group.setGroupId(resultSet.getInt("groupId"));
                group.setGroupName(resultSet.getString("groupName"));
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
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT groupId , groupName FROM groups WHERE groupId=?");) {
            preparedStatement.setInt(1, groupId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            group.setGroupId(resultSet.getInt("groupId"));
            group.setGroupName(resultSet.getString("groupName"));
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
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE groups SET groupName=? WHERE groupId=?")) {
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
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM groups WHERE groupId=?")) {
            preparedStatement.setInt(1, groupId);
            preparedStatement.execute();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
