package com.eternal.service.impl;

import com.eternal.domain.PinTaskEntity;
import com.eternal.mapper.PinTaskMapper;
import com.eternal.service.IPinTaskService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PinTaskServiceImpl implements IPinTaskService {
    @Resource
    private PinTaskMapper pinTaskMapper;
    @Override
    public int insertPinTask(PinTaskEntity pinTaskEntity) {
        return pinTaskMapper.insert(pinTaskEntity);
    }
}
