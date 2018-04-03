/*
 * COPYRIGHT (C) 2018 Waldemar Sojka. All Rights Reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.wsojka.moonlandermonitor.service;

import com.wsojka.moonlandermonitor.model.LogFileContent;
import com.wsojka.moonlandermonitor.model.MoonlanderProperty;
import org.apache.log4j.Logger;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class LogFileMonitorService {

    private static Logger log = Logger.getLogger(LogFileMonitorService.class);

    private static int previousLineNumber = -1;

    private static Pattern SEARCH_PATTERN = Pattern.compile("\\[(.*?)\\].*20s: (.*?) avg.*HW:\\d*\\/(.*?)%");

    @Value("${moonlander.logfile}")
    private String logFile;

    @Autowired
    private MoonlanderService moonlanderService;

    @Scheduled(cron = "*/10 * * * * *")
    public void readNewLogfileContent() {
        LogFileContent logFileContent = readNewLines(previousLineNumber);
        log.info("readNewLogfileContent() method executed. Current line count is: " + logFileContent.getTotalLinesCount());
        if (logFileContent.getNewLines().size() > 0) {
            log.debug("========== new log file content start ============");
            logFileContent.getNewLines().forEach(line -> {
                log.debug(line);
                Matcher m = SEARCH_PATTERN.matcher(line);
                if (m.find()) {
                    long timestamp;
                    try {
                        timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(m.group(1)).toInstant().toEpochMilli();
                    } catch (ParseException e ) {
                        log.error("problem with parsing date: " + line);
                        timestamp = Instant.now().getEpochSecond();
                    }
                    log.info("adding hash rate: " + m.group(2) + " measured at " + timestamp);
                    moonlanderService.addPropertyValue(MoonlanderProperty.ML_HASH_RATE, Double.valueOf(m.group(2)), timestamp);
                }
            });
            log.debug("========== new log file content end ============");
        }
        previousLineNumber = logFileContent.getTotalLinesCount();
    }

    private LogFileContent readNewLines(int min) {
        String line;
        List<String> lines = Lists.newArrayList();
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(logFile))) {
            while ((line = br.readLine()) != null) {
                if (min >= 0 && count >= min)
                    lines.add(line);
                count++;
            }
        } catch (IOException e) {
            log.error("problem with reading log file", e);
        }
        LogFileContent logFileContent = new LogFileContent();
        logFileContent.setTotalLinesCount(count);
        logFileContent.setNewLines(lines);
        return logFileContent;
    }
}
