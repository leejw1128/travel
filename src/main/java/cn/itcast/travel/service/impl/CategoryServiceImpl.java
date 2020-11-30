package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {

    private CategoryDao dao=new CategoryDaoImpl();
    /**
     * @Author leejunwei
     * @Description //查询类别信息，第一次查数据库，第二次查Redis缓存
     * @Date 20:51 2020/11/25/025
     * @Param []
     * @return java.util.List<cn.itcast.travel.domain.Category>
     **/
    @Override
    public List<Category> findAll() {
        Jedis jedis= JedisUtil.getJedis();
//        Set<String> categorys=jedis.zrange("category",0,-1);
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, - 1);
        List<Category>categoryList=null;
        if(categorys.size()==0||categorys==null){
//            System.out.println("去数据库查^");
            categoryList=dao.findAll();
            for (int i=0;i<categoryList.size();i++){
                jedis.zadd("category",categoryList.get(i).getCid(),categoryList.get(i).getCname());
            }
        }else {
//            System.out.println("去Redis查^");
            categoryList=new ArrayList<Category>();
            for (Tuple tuple :categorys
                 ) {
                Category category = new Category();
                category.setCname(tuple.getElement());
                category.setCid((int) tuple.getScore());
                categoryList.add(category);
            }
        }
        return categoryList;
    }
}
