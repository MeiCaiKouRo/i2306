package com.next.seatDao;

import com.next.model.TrainSeat;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TrainSeatMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TrainSeat record);

    int insertSelective(TrainSeat record);

    TrainSeat selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TrainSeat record);

    int updateByPrimaryKey(TrainSeat record);

    void batchInsert(@Param("list") List<TrainSeat> list);

}