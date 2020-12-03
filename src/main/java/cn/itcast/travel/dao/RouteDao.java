package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteDao {
    int findTotal(int cid,String rname);

    List<Route> findAll(int cid, int currentPage, int pageSize, String rname);

    Route findRoute(int parseInt);

    List<Route> findPopular(int cid,int pageSize);

    List<Route> findNewest(int parseInt, int parseInt1);

    List<Route> findThemeTour(int parseInt, int parseInt1);
}
