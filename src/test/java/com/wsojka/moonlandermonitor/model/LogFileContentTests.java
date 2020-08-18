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
package com.wsojka.moonlandermonitor.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class LogFileContentTests {

    @Test
    public void valueTest() {
        LogFileContent logFileContent = new LogFileContent();
        logFileContent.setTotalLinesCount(120);
        logFileContent.setNewLines(Arrays.asList("111", "222"));

        Assert.assertEquals(120, logFileContent.getTotalLinesCount());
        Assert.assertNotNull(logFileContent.getNewLines());
        Assert.assertEquals(2, logFileContent.getNewLines().size());
        Assert.assertEquals("111", logFileContent.getNewLines().get(0));
        Assert.assertEquals("222", logFileContent.getNewLines().get(1));
    }
}