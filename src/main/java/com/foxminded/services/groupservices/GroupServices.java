package com.foxminded.services.groupservices;

import com.foxminded.dao.groupsdao.GroupDaoImpl;
import com.foxminded.groups.Group;
import com.foxminded.randomdata.RandomData;

import java.util.List;

public class GroupServices {

    private final RandomData randomData = new RandomData() ;
    private  GroupDaoImpl groupDao = new GroupDaoImpl() ;

    public void fillingGroupsDB() {
        List<Group> groupList = randomData.randomGroups();
        groupList.forEach(groupDao::create);
    }
}
