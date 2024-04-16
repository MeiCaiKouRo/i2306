package com.next.service;

import com.next.dao.TrainStationMapper;
import com.next.model.TrainStation;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainStationService {

    @Resource
    private TrainStationMapper trainStationMapper;

    public List<TrainStation> getAll() {
        return trainStationMapper.getAll();
    }
}
