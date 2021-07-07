package com.lanweihong.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * book 实体类
 * @author lanweihong 986310747@qq.com
 */
@Data
@Table(name = "book")
public class BookDO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -8084548900015775128L;

    @Column(name = "book_name")
    private String bookName;
}
