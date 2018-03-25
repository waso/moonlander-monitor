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

import com.wsojka.moonlandermonitor.model.MoonlanderProperty;
import com.wsojka.moonlandermonitor.repository.HistoricalDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * @author Waldemar Sojka
 */
@Service
public class MoonlanderServiceImpl implements MoonlanderService {

    @Autowired
    private HistoricalDataRepository historicalDataRepository;

    @Override
    @Transactional
    public void addPropertyValue(MoonlanderProperty property, double rate, long timestamp) {
        historicalDataRepository.addPropertyValue(MoonlanderProperty.ML_HASH_RATE, timestamp, rate);
    }

    @Override
    @Transactional
    public Set<ZSetOperations.TypedTuple<String>> getPropertyValues(MoonlanderProperty property, long timestampStart, long timestampEnd) {
        return historicalDataRepository.getPropertyValues(property, timestampStart, timestampEnd);
    }
}