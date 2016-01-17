/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.keyboardplaying.xtt.xlsx;

import java.io.File;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * A utility to easily access the reference XLSX file for testing.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
public abstract class AbstractXlsxTest {

    protected File getFile(String name) {
        return new File(getClass().getResource(name).getPath());
    }

    protected File getXlsxFile() {
        return getFile("xlsx_test.xlsx");
    }

    protected XSSFWorkbook getReferenceWorkbook() throws IOException, InvalidFormatException {
        XSSFWorkbook workbook = new XSSFWorkbook(getXlsxFile());
        return workbook;
    }
}
