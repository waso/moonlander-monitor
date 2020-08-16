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

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ErrorRateAddFormTests {


    private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void minValueTest() {
        ErrorRateAddForm form = new ErrorRateAddForm();
        form.setErrorRate(0.0);
        Set<ConstraintViolation<ErrorRateAddForm>> violations = validator.validate(form);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void maxValueTest() {
        ErrorRateAddForm form = new ErrorRateAddForm();
        form.setErrorRate(Double.MAX_VALUE);
        Set<ConstraintViolation<ErrorRateAddForm>> violations = validator.validate(form);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void negativeValueTest() {
        ErrorRateAddForm form = new ErrorRateAddForm();
        form.setErrorRate(-1.0);
        List<ConstraintViolation<ErrorRateAddForm>> violations = Lists.newArrayList(validator.validate(form));
        assertEquals(1, violations.size());
        assertEquals("must be greater than or equal to 0", violations.get(0).getMessage());
    }

    @Test
    public void nullValueTest() {
        ErrorRateAddForm form = new ErrorRateAddForm();
        form.setErrorRate(null);
        List<ConstraintViolation<ErrorRateAddForm>> violations = Lists.newArrayList(validator.validate(form));
        assertEquals(1, violations.size());
        assertEquals("error rate can't be empty", violations.get(0).getMessage());
    }
}
