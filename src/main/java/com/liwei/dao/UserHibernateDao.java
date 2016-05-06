package com.liwei.dao;

import com.liwei.model.User;
import com.sun.xml.internal.rngom.parse.host.Base;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Liwei on 2016/5/5.
 */
@Repository
public class UserHibernateDao extends BaseDao implements IUserDao{

    @Override
    public void add(User user){
        this.getSession().save(user);
    }

    @Override
    public void update(User user) {
        this.getSession().update(user);
    }

    @Override
    public void delete(User user) {
        this.getSession().delete(user);
    }

    @Override
    public User load(int id) {
        return (User)this.getSession().load(User.class,id);
    }

    @Override
    public List<User> list(String hql, Object[] params) {
        Query query = this.getSession().createQuery(hql);
        if(params!=null){
            for(int i=0;i<params.length;i++){
                query.setParameter(i,params[i]);
            }
        }
        return query.list();
    }


}
