package com.liwei.dao;

import com.liwei.model.Group;

/**
 * Created by Liwei on 2016/5/6.
 */
public interface IGroupDao {

    public void add(Group group);

    public Group load(int id);
}
