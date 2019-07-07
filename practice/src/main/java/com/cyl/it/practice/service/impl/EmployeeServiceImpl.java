package com.cyl.it.practice.service.impl;

import com.cyl.it.practice.co.PageCO;
import com.cyl.it.practice.dao.EmployeeDao;
import com.cyl.it.practice.dto.EmployeeDTO;
import com.cyl.it.practice.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chengyuanliang
 * @desc
 * @since 2019-06-21
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public PageInfo findEmployeeByPage(PageCO page) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());

        PageInfo<EmployeeDTO> employeeDTOPageInfo = PageInfo.of(employeeDao.queryAll());

        return employeeDTOPageInfo;
    }

    @Override
    public PageInfo findEmployeeByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        PageInfo<EmployeeDTO> employeeDTOPageInfo = PageInfo.of(employeeDao.queryAll());

        return employeeDTOPageInfo;
    }


    //====================lambda表达式
}
