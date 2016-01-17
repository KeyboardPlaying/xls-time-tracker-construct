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
package org.keyboardplaying.xtt.action;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Before;
import org.junit.Test;
import org.keyboardplaying.xtt.AbstractXlsxTest;
import org.keyboardplaying.xtt.configuration.ProjectLocationHelper;
import org.keyboardplaying.xtt.xlsx.XlsxBuilder;
import org.keyboardplaying.xtt.xlsx.XlsxNormalizer;

/**
 * Test class for {@link DeconstructAction}.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
public class DeconstructActionTest extends AbstractXlsxTest {

    private DeconstructAction action;

    private ProjectLocationHelper location;
    private XlsxNormalizer normalizer;
    private XlsxBuilder builder;

    /** Prepares the action for tests. */
    @Before
    @SuppressWarnings("javadoc")
    public void init() throws IOException {
        location = mock(ProjectLocationHelper.class);
        when(location.getConstructedFile()).thenReturn(getXlsxFile());
        when(location.getDeconstructedDirectory()).thenReturn(new File("xlsx_deconstructed"));

        normalizer = mock(XlsxNormalizer.class);

        builder = mock(XlsxBuilder.class);
        when(builder.writeWorkbookToTmpFile(any(XSSFWorkbook.class))).thenCallRealMethod();

        action = new DeconstructAction();
        action.setLocationHelper(location);
        action.setXlsxNormalizer(normalizer);
        action.setXlsxBuilder(builder);
    }

    /** Tests the deconstruction of an XLSX. */
    @Test
    @SuppressWarnings("javadoc")
    public void testDeconstruct() throws ActionException, IOException {
        // Execute
        action.perform();

        // Assert
        verify(normalizer, times(1)).normalizeProperties(any(XSSFWorkbook.class));
        verify(normalizer, times(1)).normalizeSheets(any(XSSFWorkbook.class));
        verify(builder, times(1)).writeWorkbookToTmpFile(any(XSSFWorkbook.class));
    }
}
