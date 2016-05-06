package com.liwei.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Liwei on 2016/5/6.
 */
public class BaseDao {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // 获取 Session，注意：没有使用 openSession() ,使用 getCurrentSession()才能被 Spring 管理
    public Session getSession(){
        return sessionFactory.getCurrentSession();
    }

}
