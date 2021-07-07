package com.lanweihong.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lanweihong 986310747@qq.com
 */
@Data
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1216494278265970514L;

    @Id
    private Integer id;

    @Column(name = "version")
    private Integer version;

    @Column(name = "status")
    private Integer status;

    @Column(name = "add_time")
    private LocalDateTime addTime;
}
