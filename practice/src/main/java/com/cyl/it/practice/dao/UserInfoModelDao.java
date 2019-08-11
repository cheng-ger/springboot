package com.cyl.it.practice.dao;

import com.cyl.it.practice.model.UserInfoModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author chengyuanliang
 * @desc
 * @since 2019-08-10
 */
@Repository
public interface UserInfoModelDao extends ElasticsearchRepository<UserInfoModel , String > {
}
