package com.cyl.it.practice.service;

import com.cyl.it.practice.co.PageCO;
import com.github.pagehelper.PageInfo;

/**
 * @author chengyuanliang
 * @desc
 * @since 2019-06-21
 */
public interface EmployeeService {

    PageInfo findEmployeeByPage(PageCO page);

    PageInfo findEmployeeByPage(int  pageNum  , int  pageSize);




}
