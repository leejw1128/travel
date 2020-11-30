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

    @Override
    public Route findRoute(int parseInt) {
        String sql="select * from tab_route where rid=?";
        return template.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),parseInt);
    }

}
