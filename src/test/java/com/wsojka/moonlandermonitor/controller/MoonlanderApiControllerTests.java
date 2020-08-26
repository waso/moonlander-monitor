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
package com.wsojka.moonlandermonitor.controller;

import com.wsojka.moonlandermonitor.AppConfiguration;
import com.wsojka.moonlandermonitor.repository.HistoricalDataRepository;
import com.wsojka.moonlandermonitor.service.MoonlanderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest({MoonlanderApiController.class, AppConfiguration.class})
public class MoonlanderApiControllerTests {

    @MockBean
    private MoonlanderService moonlanderService;

    @MockBean
    private HistoricalDataRepository historicalDataRepository;

    @Autowired
    private MockMvc mvc;

    @Test
    public void addHashRateTest() throws Exception {
        mvc.perform(post("/api/hashrate/add")
                .content("{\"hashRate\": 8}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value("true"));
    }

    @Test
    public void invalidHashRateTypeTest() throws Exception {
        mvc.perform(post("/api/hashrate/add")
                .content("{\"hashRate\": \"abc\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void invalidBooleanTypeTest() throws Exception {
        mvc.perform(post("/api/hashrate/add")
                .content("{\"hashRate\": true}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void invalidRequestTest() throws Exception {
        mvc.perform(post("/api/hashrate/add")
                .content("{\"aaa\": \"bbb\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("[\"hash rate can\\u0027t be empty\"]"));
    }

    @Test
    public void invalidRequestTypeTest() throws Exception {
        mvc.perform(get("/api/hashrate/add")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void negativeHashRateTest() throws Exception {
        mvc.perform(post("/api/hashrate/add")
                .content("{\"hashRate\": -1}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("[\"must be greater than or equal to 0\"]"));
    }
}
