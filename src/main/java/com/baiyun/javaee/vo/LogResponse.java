package com.baiyun.javaee.vo;

import com.baiyun.javaee.model.LogEntry;

import java.util.List;

public class LogResponse {
    private List<LogEntry> logs;
    private long total;

    public LogResponse(List<LogEntry> logs, long total) {
        this.logs = logs;
        this.total = total;
    }

    // Getters

    public List<LogEntry> getLogs() {
        return logs;
    }

    public long getTotal() {
        return total;
    }
} 