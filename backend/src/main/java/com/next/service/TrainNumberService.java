package com.next.service;

import com.next.dao.TrainNumberMapper;
import com.next.model.TrainNumber;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainNumberService {

    @Resource
    private TrainNumberMapper trainNumberMapper;

    public List<TrainNumber> getAll() {
        return trainNumberMapper.getAll();
    }
}
