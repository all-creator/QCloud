package com.qcloud.server.spring.management.busnes.object;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterClient {
    String uuid;
    byte[] hash;
}
