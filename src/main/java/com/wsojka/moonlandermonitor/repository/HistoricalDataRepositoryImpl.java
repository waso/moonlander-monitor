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

import com.wsojka.moonlandermonitor.model.MoonlanderProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @author Waldemar Sojka
 */
@Repository
public class HistoricalDataRepositoryImpl implements HistoricalDataRepository {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void addPropertyValue(MoonlanderProperty property, long timestamp, double value) {
        redisTemplate.opsForZSet().add(property.name(), String.valueOf(value), timestamp);
    }

    @Override
    public Set<ZSetOperations.TypedTuple<String>> getPropertyValues(MoonlanderProperty property, long timestampStart, long timestampEnd) {
        return redisTemplate.opsForZSet().rangeByScoreWithScores(property.name(), timestampStart, timestampEnd);
    }
}
