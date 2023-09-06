package com.jverter.shared.interceptor.logger.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseLog extends RequestResponseLogMetaData {
    private ResponseDetails responseDetails;
}