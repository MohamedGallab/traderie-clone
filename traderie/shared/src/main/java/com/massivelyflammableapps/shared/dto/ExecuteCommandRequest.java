package com.massivelyflammableapps.shared.dto;

import lombok.Data;

@Data
public class ExecuteCommandRequest {
    String commandClass;
    Object paramsObj[];
}
