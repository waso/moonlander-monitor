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
package com.wsojka.moonlandermonitor.validator;

import com.wsojka.moonlandermonitor.model.HashRateAddForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class HashRateAddFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return HashRateAddForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors())
            return;
        HashRateAddForm form = (HashRateAddForm) target;

        if (form.getHashRate() == null) {
            errors.rejectValue("hashRate", "hash rate field missing");
        }

        if (form.getHashRate() < 0) {
            errors.rejectValue("hashRate", "hash rate value invalid");
        }
    }
}
