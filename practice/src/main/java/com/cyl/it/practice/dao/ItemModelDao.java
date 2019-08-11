package com.cyl.it.practice.dao;

import com.cyl.it.practice.model.ItemModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author chengyuanliang
 * @desc
 * @since 2019-08-10
 */
@Repository
public interface ItemModelDao extends ElasticsearchRepository<ItemModel ,String> {


    List<ItemModel> findByPriceBetween(double price1, double price2);

    List<ItemModel> findByCategory(String category);

    List<ItemModel> findByTitleLike(String title);

    List<ItemModel> findByTitleLikeAndCategoryAndBrand(String title, String Category, String Brand);
}
