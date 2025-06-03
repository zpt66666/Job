package com.baiyun.javaee.controller;

import com.baiyun.javaee.model.LogEntry;
import com.baiyun.javaee.vo.LogResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/app/api")
public class LogController {

    private static final Logger logger = LoggerFactory.getLogger(LogController.class);

    // 从 application.properties 中获取日志文件路径
    @Value("${logging.file.name}")
    private String logFilePath;

    // 定义匹配日志行的正则表达式
    private static final Pattern LOG_PATTERN = Pattern.compile("^(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2})\\[([^\\]]+)\\]\\s+(\\w+)\\s+([\\w.]+)\\s+-\\s+(.*)$");

    @GetMapping("/logs")
    public ResponseEntity<LogResponse> getLogs(
            @RequestParam(value = "level", required = false) String level,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {

        logger.info("Attempting to read log file for filtering, searching, and pagination: {}", logFilePath);
        List<LogEntry> allLogs = new ArrayList<>();

        File logFile = new File(logFilePath);

        if (!logFile.exists() || !logFile.isFile() || !logFile.canRead()) {
            logger.error("Log file not found, is not a file, or is not readable: {}", logFilePath);
            return ResponseEntity.notFound().build();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(logFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Matcher matcher = LOG_PATTERN.matcher(line);
                if (matcher.matches()) {
                    try {
                        LocalDateTime timestamp = LocalDateTime.parse(matcher.group(1));
                        String logLevel = matcher.group(3);
                        String thread = matcher.group(2);
                        String loggerName = matcher.group(4);
                        String message = matcher.group(5);

                        LogEntry logEntry = new LogEntry();
                        logEntry.setTimestamp(timestamp);
                        logEntry.setLevel(logLevel);
                        logEntry.setThread(thread);
                        logEntry.setLogger(loggerName);
                        logEntry.setMessage(message);

                        allLogs.add(logEntry);
                    } catch (DateTimeParseException e) {
                        logger.warn("Could not parse timestamp from log line: {}", line, e);
                        // Optionally, add the unparseable line as a LogEntry with limited info
                    } catch (Exception e) {
                         logger.warn("Could not parse log line: {}", line, e);
                         // Handle other potential parsing errors
                    }
                } else {
                     logger.warn("Log line did not match pattern: {}", line);
                    // Optionally, add the unmatchable line as a LogEntry with message only
                }
            }
        } catch (IOException e) {
            logger.error("Error reading log file: {}", logFilePath, e);
            return ResponseEntity.internalServerError().build();
        }

        // 过滤和搜索
        List<LogEntry> filteredLogs = allLogs.stream()
                .filter(logEntry -> level == null || logEntry.getLevel().equalsIgnoreCase(level))
                .filter(logEntry -> keyword == null || logEntry.getMessage().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());

        // 分页
        int totalLogs = filteredLogs.size();
        int fromIndex = (page - 1) * size;
        if (fromIndex >= totalLogs) {
             // 请求的页码超出范围
            return ResponseEntity.ok(new LogResponse(new ArrayList<>(), totalLogs));
        }
        int toIndex = Math.min(fromIndex + size, totalLogs);
        List<LogEntry> paginatedLogs = filteredLogs.subList(fromIndex, toIndex);

        return ResponseEntity.ok(new LogResponse(paginatedLogs, totalLogs));
    }
} 