package com.qcloud.server.spring.management.busnes.object;

import com.qcloud.server.telegram.utils.enums.UpdateType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Request {
    String type;
    String description;
    String[] args;

    public Request(UpdateType type) {
        this.type = type.getTypeName();
        this.description = type.getDescription();
        this.args = type.getArguments();
    }
}
