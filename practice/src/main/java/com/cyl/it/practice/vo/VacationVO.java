package com.cyl.it.practice.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author chengyuanliang
 * @desc
 * @since 2019-07-10
 */
@Data
public class VacationVO {

    /**
     * 申请人
     */
    private String applyUser;
    private int days;
    private String reason;
    private Date applyTime;
    private String applyStatus;

    /**
     * 审核人
     */
    private String processInstanceId;
    private String auditor;
    private String result;
    private Date auditTime;

}
