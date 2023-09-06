package com.jverter.shared.interceptor.logger.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestResponseLogMetaData {

    private String timestamp;
    private DIRECTION direction;
    private String transactionId;

    public enum DIRECTION {
    	REQUEST,
    	RESPONSE
    }
}

