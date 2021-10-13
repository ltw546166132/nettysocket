package com.ltw.common.model.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * BaseDO
 *
 * @author YeKaiQiang
 * @since 2018/4/22
 */
@Data
public class BaseDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId
    private Long id;

}
