package com.next.dao;

import com.next.model.TrainNumberDetail;

import java.util.List;

public interface TrainNumberDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TrainNumberDetail record);

    int insertSelective(TrainNumberDetail record);

    TrainNumberDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TrainNumberDetail record);

    int updateByPrimaryKey(TrainNumberDetail record);

    List<TrainNumberDetail> getAll();
}