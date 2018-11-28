package com.len.base;

import com.len.util.ReType;

import java.io.Serializable;

/**
 * @author zhuxiaomeng
 * @date 2017/12/13.
 * @email 154040976@qq.com
 * 通用service层
 */
public interface BaseService<T, E extends Serializable> extends BaseMapper<T, E> {
    /**
     * 根据id删除
     * @param id
     * @return
     *//*
  int deleteByPrimaryKey(E id);

  *//**
     * 插入
     * @param record
     * @return
     *//*
  int insert(T record);

  *//**
     *插入非空字段
     * @param record
     * @return
     *//*
  int insertSelective(T record);

  *//**
     * 根据id查询
     * @param id
     * @return
     *//*
  T selectByPrimaryKey(E id);

  *//**
     * 更新非空数据
     * @param record
     * @return
     *//*
  int updateByPrimaryKeySelective(T record);

  */

    /**
     * 更新
     *
     * @param record
     * @return
     *//*
  int updateByPrimaryKey(T record);


  List<T> selectListByPage(T record);*/

    public ReType show(T t, int page, int limit);

    public String showAll(T t);
}
