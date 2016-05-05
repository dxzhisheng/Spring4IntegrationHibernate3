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
public class GroupHibernateDao {


    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getSession(){
        return sessionFactory.getCurrentSession();
    }


    public void groupAdd(Group group){
        getSession().save(group);
    }


    public Group load(Integer id){
        return (Group) getSession().get(Group.class,id);
    }

}
