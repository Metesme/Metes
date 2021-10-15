package com.eternal.domain;

import lombok.Data;

@Data
public class PinTaskEntity {
    private String taskId;
    private String  fileName;
    private String  hash;
    private Long taskStart;
    private Long taskEnd;
    private Long taskStatus;
}
