package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteService {
    PageBean<Route> findByPage(int cid, int currentPage, int pageSize, String rname);

    Route findRoute(String rid);

    List<Route> findPopular(String cidStr,String pageSizeStr);

    List<Route> findNewest(String cidStr, String pageSizeStr);

    List<Route> findThemeTour(String cidStr, String pageSizeStr);
}
