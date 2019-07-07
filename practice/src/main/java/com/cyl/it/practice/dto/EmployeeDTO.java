package com.cyl.it.practice.dto;

import lombok.Data;

/**
 * @author chengyuanliang
 * @desc  wode
 * @since 2019-06-21
 */
@Data
public class EmployeeDTO {

    /*EmployeeID  int(11)
    Name        varchar(255)
    Position    varchar(255)
    Salary      double
    Remarks     varchar(255)*/


    private Long employeeId;

    private String name;

    private String position;

    private Double salary;

    private String remarks;


}
