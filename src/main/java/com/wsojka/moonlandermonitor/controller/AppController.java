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
package com.wsojka.moonlandermonitor.controller;

import com.wsojka.moonlandermonitor.model.HashRate;
import com.wsojka.moonlandermonitor.service.MoonlanderService;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.DecimalFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AppController {

    private static final Logger log = LoggerFactory.getLogger(AppController.class);

    @Autowired
    private MoonlanderService moonlanderService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(ModelMap model) {
        long endTimestampp = Instant.now().toEpochMilli();
        long lastHourTimestamp = Instant.now().minus(1, ChronoUnit.HOURS).toEpochMilli();
        long last24HoursTimestamp = Instant.now().minus(24, ChronoUnit.HOURS).toEpochMilli();
        long last7daysTimestamp = Instant.now().minus(7, ChronoUnit.DAYS).toEpochMilli();
        List<HashRate> allData = moonlanderService.getHashRates(last7daysTimestamp, endTimestampp);

        List<HashRate> lastHourData = allData.parallelStream().filter(e -> e.getDate().getTimeInMillis() > lastHourTimestamp).collect(Collectors.toList());
        List<HashRate> last24HoursData = allData.parallelStream().filter(e -> e.getDate().getTimeInMillis() > last24HoursTimestamp).collect(Collectors.toList());
        List<HashRate> last30DaysData = allData.parallelStream().filter(e -> e.getDate().getTimeInMillis() > last7daysTimestamp).collect(Collectors.toList());

        lastHourData = lastHourData.parallelStream().sorted(Comparator.comparing(HashRate::getDate)).collect(Collectors.toList());
        last24HoursData = last24HoursData.parallelStream().sorted(Comparator.comparing(HashRate::getDate)).collect(Collectors.toList());
        last30DaysData = last30DaysData.parallelStream().sorted(Comparator.comparing(HashRate::getDate)).collect(Collectors.toList());

        List<String> data1 = Lists.newArrayList();
        List<String> labels1 = Lists.newArrayList();

        List<String> data2 = Lists.newArrayList();
        List<String> labels2 = Lists.newArrayList();

        List<String> data3 = Lists.newArrayList();
        List<String> labels3 = Lists.newArrayList();

        DecimalFormat df = new DecimalFormat();
        df.setMinimumFractionDigits(2);

        lastHourData.forEach(e -> {
            data1.add(df.format(e.getValue()));

            labels1.add(Instant
                    .ofEpochMilli(e.getDate().getTimeInMillis())
                    .atZone(ZoneId.systemDefault())
                    .format(DateTimeFormatter.ofPattern("HH:mm")));

        });

        last24HoursData.forEach(e -> {
            data2.add(df.format(e.getValue()));
            labels2.add(Instant
                    .ofEpochMilli(e.getDate().getTimeInMillis())
                    .atZone(ZoneId.systemDefault())
                    .format(DateTimeFormatter.ofPattern("HH:mm")));
        });

        last30DaysData.forEach(e -> {
            data3.add(df.format(e.getValue()));
            labels3.add(Instant
                    .ofEpochMilli(e.getDate().getTimeInMillis())
                    .atZone(ZoneId.systemDefault())
                    .format(DateTimeFormatter.ofPattern("d MMM HH:mm")));
        });
        model.addAttribute("data_1h", String.join(",", data1));
        model.addAttribute("labels_1h", "'" + String.join("','", labels1) + "'");

        model.addAttribute("data_24h", String.join(",", data2));
        model.addAttribute("labels_24h", "'" + String.join("','", labels2) + "'");

        model.addAttribute("data_7d", String.join(",", data3));
        model.addAttribute("labels_7d", "'" + String.join("','", labels3) + "'");
        return "index";
    }
}
