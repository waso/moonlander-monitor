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

import com.wsojka.moonlandermonitor.model.MoonlanderProperty;
import com.wsojka.moonlandermonitor.service.MoonlanderService;
import org.apache.log4j.Logger;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class AppController {

    private static Logger log = Logger.getLogger(RestApiController.class);

    @Autowired
    private MoonlanderService moonlanderService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(ModelMap model) {
        long endTimestampp = Instant.now().getEpochSecond();
        long lastHourTimestamp = Instant.now().minus(1, ChronoUnit.HOURS).getEpochSecond();
        long last24HoursTimestamp = Instant.now().minus(24, ChronoUnit.HOURS).getEpochSecond();
        long last7daysTimestamp = Instant.now().minus(7, ChronoUnit.DAYS).getEpochSecond();
        Set<ZSetOperations.TypedTuple<String>> allData = moonlanderService.getPropertyValues(MoonlanderProperty.ML_HASH_RATE, last7daysTimestamp, endTimestampp);

        List<ZSetOperations.TypedTuple<String>> lastHourData = allData.parallelStream().filter(e -> e.getScore().longValue() > lastHourTimestamp).collect(Collectors.toList());
        List<ZSetOperations.TypedTuple<String>> last24HoursData = allData.parallelStream().filter(e -> e.getScore().longValue() > last24HoursTimestamp).collect(Collectors.toList());
        List<ZSetOperations.TypedTuple<String>> last30DaysData = allData.parallelStream().filter(e -> e.getScore().longValue() > last7daysTimestamp).collect(Collectors.toList());

        lastHourData = lastHourData.parallelStream().sorted(Comparator.comparing(ZSetOperations.TypedTuple::getScore)).collect(Collectors.toList());
        last24HoursData = last24HoursData.parallelStream().sorted(Comparator.comparing(ZSetOperations.TypedTuple::getScore)).collect(Collectors.toList());
        last30DaysData = last30DaysData.parallelStream().sorted(Comparator.comparing(ZSetOperations.TypedTuple::getScore)).collect(Collectors.toList());

        List<String> data1 = Lists.newArrayList();
        List<String> labels1 = Lists.newArrayList();

        List<String> data2 = Lists.newArrayList();
        List<String> labels2 = Lists.newArrayList();

        List<String> data3 = Lists.newArrayList();
        List<String> labels3 = Lists.newArrayList();

        lastHourData.forEach(e -> {
            data1.add(e.getValue());
            labels1.add(Instant
                    .ofEpochSecond(e.getScore().intValue())
                    .atZone(ZoneId.systemDefault())
                    .format(DateTimeFormatter.ofPattern("HH:mm")));

        });

        last24HoursData.forEach(e -> {
            data2.add(e.getValue());
            labels2.add(Instant
                    .ofEpochSecond(e.getScore().intValue())
                    .atZone(ZoneId.systemDefault())
                    .format(DateTimeFormatter.ofPattern("HH:mm")));
        });

        last30DaysData.forEach(e -> {
            data3.add(e.getValue());
            labels3.add(Instant
                    .ofEpochSecond(e.getScore().intValue())
                    .atZone(ZoneId.systemDefault())
                    .format(DateTimeFormatter.ofPattern("d MMM HH:mm")));
        });

        model.addAttribute("data1", data1.toArray());
        model.addAttribute("labels1", "'" + String.join("','", labels1) + "'");

        model.addAttribute("data2", data2.toArray());
        model.addAttribute("labels2", "'" + String.join("','", labels2) + "'");

        model.addAttribute("data3", data3.toArray());
        model.addAttribute("labels3", "'" + String.join("','", labels3) + "'");
        return "index";
    }
}
