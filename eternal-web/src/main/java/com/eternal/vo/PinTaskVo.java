package com.eternal.vo;

import lombok.Data;

@Data
public class PinTaskVo {
    private String taskId;
    private String  hash;
    private String fileName;
    private long    date;
    private long    userId;

}
