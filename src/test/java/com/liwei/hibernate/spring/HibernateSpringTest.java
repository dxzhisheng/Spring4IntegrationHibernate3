package com.liwei.hibernate.spring;

import com.liwei.dao.GroupHibernateDao;
import com.liwei.dao.UserHibernateDao;
import com.liwei.model.Group;
import com.liwei.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:beans.xml"})
public class HibernateSpringTest {

    @Autowired
    private GroupHibernateDao groupHibernateDao;

    @Autowired
    private UserHibernateDao userHibernateDao;

    @Test
    public void testAdd() {
        Group group = new Group();
        group.setName("技术研发部");
        groupHibernateDao.groupAdd(group);
        User user =new User("liwei","123456","李威威");
        user.setGroup(group);
        userHibernateDao.add(user);
    }

}
