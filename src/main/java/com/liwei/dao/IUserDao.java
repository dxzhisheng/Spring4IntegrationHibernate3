package com.liwei.dao;

import com.liwei.model.User;

import java.util.List;

/**
 * Created by Liwei on 2016/5/6.
 */
public interface IUserDao {

    public void add(User user);
    public void update(User user);
    public void delete(User user);
    public User load(int id);
    public List<User> list(String hql, Object[] params);

}
