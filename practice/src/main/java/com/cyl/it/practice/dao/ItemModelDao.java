package com.cyl.it.practice.dao;

import com.cyl.it.practice.model.ItemModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author chengyuanliang
 * @desc
 * @since 2019-08-10
 */
@Repository
public interface ItemModelDao extends ElasticsearchRepository<ItemModel ,String> {
}
