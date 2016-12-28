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

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Before;
import org.junit.Test;
import org.keyboardplaying.xtt.AbstractXlsxTest;

/**
 * A class that normalizes the properties of an XLSX file.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
public class XlsxBuilderTest extends AbstractXlsxTest {

    private XlsxBuilder builder;

    /**
     * Prepares the normalizer for tests.
     */
    @Before
    public void init() {
        builder = new XlsxBuilder();
    }

    /**
     * Tests {@link XlsxBuilder#writeWorkbookToTmpFile(XSSFWorkbook)}.
     */
    @Test
    @SuppressWarnings("javadoc")
    public void testWriteWorkbookToTmpFile() throws InvalidFormatException, IOException {
        // Prepare
        try (XSSFWorkbook original = getReferenceWorkbook()) {

            // Execute
            File file = builder.writeWorkbookToTmpFile(original);

            // Assert
            // Check file is an unaltered copy
            try (XSSFWorkbook copy = new XSSFWorkbook(file)) {
                assertEquals(original.getCTWorkbook().xmlText(), copy.getCTWorkbook().xmlText());
            }
        }
    }
}
