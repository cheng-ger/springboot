package com.cyl.it.practice.dao;

import com.cyl.it.practice.dto.EmployeeDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author chengyuanliang
 * @desc
 * @since 2019-06-21
 */
@Repository
public interface EmployeeDao {

    List<EmployeeDTO> queryAll();

    Long queryNextI();


}
