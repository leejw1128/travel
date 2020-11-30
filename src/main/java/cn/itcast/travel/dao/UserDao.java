package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    //根据用户名字获取用户
    public User findByUsername(String username);
    //保存用户
    public void save(User user);

    void updateStatus(User user);

    User findByCode(String code);

    User findByNameAndPassword(String username, String password);
}
