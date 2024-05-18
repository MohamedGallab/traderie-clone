package com.massivelyflammableapps.shared.dto;

import lombok.Data;

@Data
public class AddCommandRequest {
    String commandClass;
    String commandCode;
}
