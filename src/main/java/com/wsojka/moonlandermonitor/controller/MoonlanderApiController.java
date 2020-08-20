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

import com.google.gson.Gson;
import com.wsojka.moonlandermonitor.model.ErrorRateAddForm;
import com.wsojka.moonlandermonitor.model.HashRateAddForm;
import com.wsojka.moonlandermonitor.model.MoonlanderProperty;
import com.wsojka.moonlandermonitor.service.MoonlanderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.util.stream.Collectors;

/**
 * @author Waldemar Sojka
 */
@RestController
@RequestMapping("api")
public class MoonlanderApiController {

    private static final Logger log = LoggerFactory.getLogger(MoonlanderApiController.class);
    
    private Gson gson;

    private MoonlanderService moonlanderService;

    @Autowired
    public MoonlanderApiController(
            MoonlanderService moonlanderService
    ) {
        this.gson = new Gson();
        this.moonlanderService = moonlanderService;
    }

    @PostMapping("/hashrate/add")
    ResponseEntity<?> addHashRateRate(@Valid @RequestBody HashRateAddForm form, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .body(gson.toJson(errors.getAllErrors()
                            .parallelStream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
                            .collect(Collectors.toList())));
        }
        log.info("hash rate received: " + form.getHashRate());
        moonlanderService.addPropertyValue(MoonlanderProperty.ML_HASH_RATE, form.getHashRate(), Instant.now().toEpochMilli());
        return ResponseEntity.ok("{\"success\": true}");
    }

    @PostMapping("/errorrate/add")
    ResponseEntity<?> addErrorRate(@Valid @RequestBody ErrorRateAddForm form, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .body(gson.toJson(errors.getAllErrors()
                            .parallelStream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
                            .collect(Collectors.toList())));
        }
        log.info("error rate received: " + form.getErrorRate());
        moonlanderService.addPropertyValue(MoonlanderProperty.ML_ERROR_RATE, form.getErrorRate(), Instant.now().toEpochMilli());
        return ResponseEntity.ok("{\"success\": true}");
    }
}