package com.ziad.task.model.response;

import java.util.Date;

public record AuthenticationResponse(
        Date currentTime,
        String tokenType,
        String token,
        long expirationTime
) {
}
