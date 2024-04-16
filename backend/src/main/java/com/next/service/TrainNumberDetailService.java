package com.next.service;

import com.next.dao.TrainNumberDetailMapper;
import com.next.model.TrainNumberDetail;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainNumberDetailService {

    @Resource
    private TrainNumberDetailMapper trainNumberDetailMapper;


    public List<TrainNumberDetail> getAll(){
        return trainNumberDetailMapper.getAll();
    }
}
