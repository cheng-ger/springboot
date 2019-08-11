package com.cyl.it.practice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;


import java.io.Serializable;

/**
 * @author chengyuanliang
 * @desc
 * @since 2019-08-10
 */
@Data
@Document(indexName = "practice_user_info" ,type = "userInfoModel")
public class UserInfoModel implements Serializable {


    private static final long serialVersionUID = 2588627948093121361L;
    @Id
    private String id;

    private String userName;

    private Integer age;

    private String sex;

    private String hobby;

    public UserInfoModel() {
    }

    public UserInfoModel(String id, String userName, Integer age, String sex, String hobby) {
        this.id = id;
        this.userName = userName;
        this.age = age;
        this.sex = sex;
        this.hobby = hobby;
    }
}
