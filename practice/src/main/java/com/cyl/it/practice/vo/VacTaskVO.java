package com.cyl.it.practice.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author chengyuanliang
 * @desc
 * @since 2019-07-10
 */
@Data
public class VacTaskVO {

    private String id;
    private String name;
    private VacationVO vac;
    private Date createTime;
}
