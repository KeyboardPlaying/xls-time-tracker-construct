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
import static org.junit.Assert.assertFalse;

import java.io.IOException;

import org.apache.poi.POIXMLProperties;
import org.apache.poi.POIXMLProperties.CoreProperties;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Before;
import org.junit.Test;

/**
 * A class that normalizes the properties of an XLSX file.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
public class XlsxNormalizerTest extends AbstractXlsxTest {

    private static final String AUTHOR = "Author";
    private static final String COMPANY = "Company";
    private static final String TITLE = "Title";
    private static final String ACTIVE_TRACKER = "A1:A2";
    private static final String ACTIVE_CONFIG = "C2";

    private XlsxNormalizer normalizer;

    /** Prepares the normalizer for tests. */
    @Before
    public void init() {
        normalizer = new XlsxNormalizer();
        normalizer.setAuthor(AUTHOR);
        normalizer.setCompany(COMPANY);
        normalizer.setTitle(TITLE);
        normalizer.setTrackerActiveRange(ACTIVE_TRACKER);
        normalizer.setConfigActiveRange(ACTIVE_CONFIG);
    }

    /** Tests the properties normalization. */
    @Test
    @SuppressWarnings("javadoc")
    public void testNormalizeProperty() throws InvalidFormatException, IOException {
        // Prepare
        try (XSSFWorkbook workbook = getReferenceWorkbook()) {

            // Execute
            normalizer.normalizeProperties(workbook);

            // Assert
            POIXMLProperties properties = workbook.getProperties();
            CoreProperties coreProperties = properties.getCoreProperties();
            assertEquals(AUTHOR, coreProperties.getCreator());
            assertEquals(AUTHOR, coreProperties.getUnderlyingProperties().getLastModifiedByProperty().getValue());
            assertEquals(COMPANY, properties.getExtendedProperties().getCompany());
            assertEquals(coreProperties.getCreated(), coreProperties.getModified());
        }
    }

    /** Tests the properties normalization. */
    @Test
    @SuppressWarnings("javadoc")
    public void testNormalizeSheets() throws InvalidFormatException, IOException {
        // Prepare
        try (XSSFWorkbook workbook = getReferenceWorkbook()) {

            // Execute
            normalizer.normalizeSheets(workbook);

            // Assert
            XSSFSheet sheet;

            sheet = workbook.getSheetAt(XlsxTracker.TAB_INDEX_TRACKER);
            assertEquals(ACTIVE_TRACKER, sheet.getActiveCell());
            assertFalse(sheet.isDisplayGridlines());
            assertFalse(sheet.isSelected());

            sheet = workbook.getSheetAt(XlsxTracker.TAB_INDEX_CONFIG);
            assertEquals(ACTIVE_CONFIG, sheet.getActiveCell());
            assertFalse(sheet.isDisplayGridlines());
            assertFalse(sheet.isSelected());

            assertEquals(XlsxTracker.TAB_INDEX_TRACKER, workbook.getActiveSheetIndex());
        }
    }
}
