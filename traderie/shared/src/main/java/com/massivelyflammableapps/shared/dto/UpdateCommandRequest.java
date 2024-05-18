package com.massivelyflammableapps.shared.dto;

import lombok.Data;

@Data
public class UpdateCommandRequest {
    String commandClass;
    String commandCode;
}
