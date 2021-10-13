package com.ltw.common.model.bean;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * BaseQuery
 *
 * @author YeKaiQiang
 * @since 2018/4/22
 */
@Data
public abstract class BaseQuery<E extends BaseDO> {

  private static final int DEFAULT_PAGE_NO = 1;
  private static final int DEFAULT_PAGE_SIZE = 15;
  private static final int MAX_PAGE_SIZE = 1000;

  @ApiModelProperty("当前页码，默认1")
  private int pageNo = DEFAULT_PAGE_NO;

  @ApiModelProperty("每页大小，默认15，最大1000")
  private int pageSize = DEFAULT_PAGE_SIZE;

  /**
   * 获取当前页码
   *
   * @return
   */
  public int getPageNo() {
    return pageNo < DEFAULT_PAGE_NO ? DEFAULT_PAGE_NO : pageNo;
  }

  /**
   * 获取当前页码
   *
   * @return
   */
  public int getPageSize() {
    return pageSize > MAX_PAGE_SIZE ? MAX_PAGE_SIZE : pageSize;
  }


  /**
   * 构造分页查询条件
   *
   * @return Wrapper
   */

  public abstract Wrapper<E> constructQuery();

  public Page<E> createPage() {
    return new Page<>(getPageNo(), getPageSize());
  }

}
