package com.liwei.dao;

import com.liwei.model.Group;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by Liwei on 2016/5/5.
 */
@Repository(value="groupHibernateDao")
public class GroupHibernateDao extends BaseDao implements IGroupDao{
    @Override
    public void add(Group group) {
        this.getSession().save(group);
    }

    @Override
    public Group load(int id) {
        return (Group) this.getSession().get(Group.class,id);
    }







}
