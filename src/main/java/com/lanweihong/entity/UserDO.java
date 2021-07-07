package com.lanweihong.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * user 实体类
 * @author lanweihong 986310747@qq.com
 */
@Data
@Table(name = "user")
public class UserDO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 6153646859606537850L;

    @Column(name = "user_name")
    private String userName;
}
