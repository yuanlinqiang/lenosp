package com.len.base;

import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhuxiaomeng
 * @date 2017/12/12.
 * @email 154040976@qq.com
 * mapper封装 crud
 */
public interface BaseMapper<T, E extends Serializable> extends tk.mybatis.mapper.common.BaseMapper<T>, MySqlMapper<T>, IdsMapper<T> {
    /*
     *//**
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

    *//**
     * 更新
     * @param record
     * @return
     *//*
    int updateByPrimaryKey(T record);

    */

    /**
     * 查询
     *
     * @param record
     * @return
     */



    List<T> selectListByPage(T record);
}
