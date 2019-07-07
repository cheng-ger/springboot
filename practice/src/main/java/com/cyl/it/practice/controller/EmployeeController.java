package com.cyl.it.practice.controller;

import com.cyl.it.practice.co.PageCO;
import com.cyl.it.practice.service.EmployeeService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author chengyuanliang
 * @desc
 * @since 2019-06-21
 */
@Slf4j
@RestController
@RequestMapping("empService")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("findEmpAll")
    public PageInfo findEmpPageAll(@RequestBody PageCO page){
        log.info("page:{}", page);
        return employeeService.findEmployeeByPage( page);
    }

    @GetMapping("findEmpAlla")
    public PageInfo findEmpPageAlla(@RequestBody PageCO page){
        log.info("page:{}", page);
        return employeeService.findEmployeeByPage( page);
    }

    @GetMapping("findEmp/{pageNum}/{pageSize}")
    public PageInfo findEmpPage( @PathVariable  int  pageNum , @PathVariable  int pageSize){
        Page page = new Page();
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);
        log.info("page:{}", page);
        return employeeService.findEmployeeByPage(pageNum,pageSize );
    }
}
