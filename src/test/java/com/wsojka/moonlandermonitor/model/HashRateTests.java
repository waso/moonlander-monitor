/*
 * COPYRIGHT (C) 2020 Waldemar Sojka. All Rights Reserved.
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
package com.wsojka.moonlandermonitor.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Set;

import static org.junit.Assert.assertTrue;

public class HashRateTests {
    private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void valueTest() {
        HashRate hashRate = new HashRate();
        hashRate.setId(10L);
        hashRate.setValue(new BigDecimal(11));
        Calendar calendar = Calendar.getInstance();
        hashRate.setDate(calendar);
        Assert.assertEquals((long)10, (long)hashRate.getId());
        Assert.assertEquals(11, hashRate.getValue().intValue());
        Assert.assertEquals(calendar, hashRate.getDate());
        Set<ConstraintViolation<HashRate>> violations = validator.validate(hashRate);
        assertTrue(violations.isEmpty());
    }
}
