package com.next.service;

import com.next.dao.TrainCityMapper;
import com.next.model.TrainCity;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainCityService {
    @Resource
    private TrainCityMapper trainCityMapper;

    public List<TrainCity> getAll() {
        return trainCityMapper.getAll();
    }
}
