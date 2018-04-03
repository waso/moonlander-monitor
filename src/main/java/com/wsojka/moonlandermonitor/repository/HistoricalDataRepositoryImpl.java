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
package com.wsojka.moonlandermonitor.repository;

import com.wsojka.moonlandermonitor.model.HashRate;
import com.wsojka.moonlandermonitor.model.MoonlanderProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

/**
 * @author Waldemar Sojka
 */
@Service
public class HistoricalDataRepositoryImpl implements HistoricalDataRepository {

    @Autowired
    private HashRateRepository hashRateRepository;

    @Override
    public void addPropertyValue(MoonlanderProperty property, long timestamp, double value) {
        HashRate hashRate = new HashRate();
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp);
        hashRate.setDate(c);
        hashRate.setValue(new BigDecimal(value));
        hashRateRepository.saveAndFlush(hashRate);
    }

    @Override
    public List<HashRate> getHashRates(long timestampStart, long timestampEnd) {
        Calendar timeStart = Calendar.getInstance();
        Calendar timeEnd = Calendar.getInstance();
        timeStart.setTimeInMillis(timestampStart);
        timeEnd.setTimeInMillis(timestampEnd);
        return hashRateRepository.findByDateBetweenOrderByDateAsc(timeStart, timeEnd);
    }
}
