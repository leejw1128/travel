package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserServcie;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

import java.util.UUID;

public class UserServiceImpl implements UserServcie {
    private UserDao userDao=new UserDaoImpl();
    /*
    * 注册用户
    * */
    @Override
    public boolean regist(User user) {
        User u=userDao.findByUsername(user.getUsername());
        if (u!=null){
            return false;
        }
        user.setCode(UuidUtil.getUuid());
        user.setStatus("N");
        userDao.save(user);
        String content="<a href='http://localhost:8080/travel/user/active?code="+user.getCode()+"'>点击激活旅游网</a>";
        MailUtils.sendMail(user.getEmail(),content,"激活邮件");
        return true;
    }
    //邮箱激活
    @Override
    public boolean active(String code) {
        User user=userDao.findByCode(code);
        if (user!=null){
            userDao.updateStatus(user);
            return true;
        }else {
            return false;
        }
    }
    //实现登录功能
    @Override
    public User login(User user) {
        String username=user.getUsername();
        String password=user.getPassword();
        User u=userDao.findByNameAndPassword(username,password);
        return u;
    }
}
