package com.massivelyflammableapps.shared.dto.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserInfoRequest {
    @NonNull
    String username;
}
