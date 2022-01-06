package com.foxminded.dao.groupsdao;

import com.foxminded.groups.Group;

import java.util.List;

public interface GroupDao {

    void create (Group group);
    List<Group> findAll();
    Group findId(int groupId);
    void update (Group group , int groupId);
    void delete (int groupId);
}
