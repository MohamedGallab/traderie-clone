package com.massivelyflammableapps.shared.dto.admin;

import lombok.Data;

@Data
public class UpdateCommandRequest {
    String commandClass;
    String commandCode;
}
