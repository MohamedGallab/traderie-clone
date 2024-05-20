package com.massivelyflammableapps.shared.dto.admin;

import lombok.Data;

@Data
public class ExecuteCommandRequest {
    String commandClass;
    Object paramsObj[];
}
