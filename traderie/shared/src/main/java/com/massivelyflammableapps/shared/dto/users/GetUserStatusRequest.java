package com.massivelyflammableapps.shared.dto.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserStatusRequest {
    @NonNull
    String userId;
}
