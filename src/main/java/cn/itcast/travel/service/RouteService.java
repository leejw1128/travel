package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

public interface RouteService {
    PageBean<Route> findByPage(int cid, int currentPage, int pageSize, String rname);

    Route findRoute(String rid);
}
