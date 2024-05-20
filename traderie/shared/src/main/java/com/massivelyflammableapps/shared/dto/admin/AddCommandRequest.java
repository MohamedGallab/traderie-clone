package com.massivelyflammableapps.shared.dto.admin;

import lombok.Data;

@Data
public class AddCommandRequest {
    String commandClass;
    String commandCode;
}
