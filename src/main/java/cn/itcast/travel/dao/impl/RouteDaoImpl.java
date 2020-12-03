package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import com.alibaba.druid.util.JdbcUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
    /**
     * @Author leejunwei
     * @Description //查询总记录数
     * @Date 21:36 2020/11/25/025
     * @Param [cid]
     * @return int
     **/
    @Override
    public int findTotal(int cid,String rname) {
        String sql="select count(*) from tab_route where 1 = 1 ";
        StringBuilder sb=new StringBuilder(sql);
        List params=new ArrayList();
        if(cid!=0){
            sb.append(" and cid= ? ");
            params.add(cid);
        }
        if (rname!=null && rname.length()>0 && !"null".equals(rname)){
            sb.append(" and rname like ?");
            params.add("%"+rname+"%");
        }
        sql=sb.toString();
        return template.queryForObject(sql,Integer.class,params.toArray());
    }
    /**
     * @Author leejunwei
     * @Description //查询当前页记录
     * @Date 21:37 2020/11/25/025
     * @Param
     * @return
     **/
    @Override
    public List<Route> findAll(int cid, int start, int pageSize,String rname) {
        String sql="select * from tab_route where 1=1 ";
        StringBuilder sb=new StringBuilder(sql);
        List params=new ArrayList();
        if(cid!=0){
            sb.append(" and cid= ? ");
            params.add(cid);
        }
        if (rname!=null && rname.length()>0 && !"null".equals(rname)){
            sb.append(" and rname like ? ");
            params.add("%"+rname+"%");
        }
        sb.append(" limit ?,? ");
        sql=sb.toString();
        params.add(start);
        params.add(pageSize);

        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class),params.toArray());
    }
    /**
     * @Author leejunwei
     * @Description //根据rid查询
     * @Date 21:37 2020/11/25/025
     * @Param
     * @return
     **/
    @Override
    public Route findRoute(int parseInt) {
        String sql="select * from tab_route where rid=?";
        return template.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),parseInt);
    }
    /**
     * @Author leejunwei
     * @Description //根据人气最高 收藏数降序排列
     * @Date 21:37 2020/11/25/025
     * @Param
     * @return
     **/
    @Override
    public List<Route> findPopular(int cid, int pageSize) {
        //select * from tab_route where cid=1 order by count desc limit 0,6;
        String sql="select * from tab_route where 1=1";
        StringBuilder sb=new StringBuilder(sql);
        List params=new ArrayList();
        if(cid!=0){
            sb.append(" and cid= ? ");
            params.add(cid);
        }
        sb.append(" order by count desc ");
        sb.append("limit ?,?");
        params.add(0);
        params.add(pageSize);
        sql=sb.toString();
        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class),params.toArray());
    }

    /**
     * @Author leejunwei
     * @Description //根据时间排序 最新的
     * @Date 21:37 2020/11/25/025
     * @Param
     * @return
     **/
    @Override
    public List<Route> findNewest(int cid, int pageSize) {
        //select * from tab_route where cid=1 order by rdate desc limit 0,6;
        String sql="select * from tab_route where 1=1";
        StringBuilder sb=new StringBuilder(sql);
        List params=new ArrayList();
        if(cid!=0){
            sb.append(" and cid= ? ");
            params.add(cid);
        }
        sb.append(" order by rdate desc ");
        sb.append("limit ?,?");
        params.add(0);
        params.add(pageSize);
        sql=sb.toString();
        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class),params.toArray());
    }
    /**
     * @Author leejunwei
     * @Description //是否是主题旅游
     * @Date 21:37 2020/11/25/025
     * @Param
     * @return
     **/
    @Override
    public List<Route> findThemeTour(int cid, int pageSize) {
        //select * from tab_route where isThemeTour=1 and cid=5 order by count desc limit 0,6;
        String sql="select * from tab_route where 1=1";
        StringBuilder sb=new StringBuilder(sql);
        List params=new ArrayList();
        if(cid!=0){
            sb.append(" and cid= ? ");
            params.add(cid);
        }
        sb.append(" and isThemeTour=1 ");
        sb.append(" order by count desc ");
        sb.append("limit ?,?");
        params.add(0);
        params.add(pageSize);
        sql=sb.toString();
        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class),params.toArray());
    }
}
