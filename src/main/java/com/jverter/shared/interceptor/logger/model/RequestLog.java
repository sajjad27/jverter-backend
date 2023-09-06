package com.jverter.shared.interceptor.logger.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestLog extends RequestResponseLogMetaData{
    private RequestDetails requestDetails;
}

