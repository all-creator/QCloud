package com.qcloud.server.spring.management.busnes.object;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LicenseKey {
    String uuidGlobal;
    String uuidLocal;
    byte[] uuid;
}
