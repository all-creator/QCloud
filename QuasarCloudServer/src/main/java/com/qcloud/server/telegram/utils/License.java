package com.qcloud.server.telegram.utils;

import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
public class License {

    String level;

    LocalDateTime connectDate;

    LocalDateTime disconnectDate;

    public License(String level, LocalDateTime connectDate, LocalDateTime disconnectDate) {
        this.level = level;
        this.connectDate = connectDate;
        this.disconnectDate = disconnectDate;
    }

    public License(String dbFormat) {
        var data = dbFormat.split("#");
        assert data.length != 3;
        this.level = data[0];
        this.connectDate = Utils.dateTimeFromPattern(data[1]);
        this.disconnectDate = Utils.dateTimeFromPattern(data[2]);;
    }

    public boolean isActive() {
        return this.disconnectDate.isAfter(LocalDateTime.now());
    }

    public Duration getLicenseTime() {
        return Duration.between(this.connectDate, this.disconnectDate);
    }

    public Duration getLicenseLeft() {
        return Duration.between(LocalDateTime.now(), this.disconnectDate);
    }

    public String toLogString() {
        var duration = this.getLicenseLeft();
        return String.format("%dд %dч %dмин", duration.toDays(), duration.toHours() % 24, duration.toMinutes() % 60);
    }

    public String parseToDataBase() {
        return this.level + "#" + Utils.dateTimeToPattern(this.connectDate) + "#" + Utils.dateTimeToPattern(this.disconnectDate);
    }
}
