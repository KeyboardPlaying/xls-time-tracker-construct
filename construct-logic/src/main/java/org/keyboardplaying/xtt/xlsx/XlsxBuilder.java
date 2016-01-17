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
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * A class that normalizes the properties of an XLSX file.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
public class XlsxBuilder {

    private static final String TMP_LOCATION = "tracker-tmp.xlsx";
    private static final String TMP_EXTENSION = ".tmp";

    /**
     * Saves the supplied workbook to a temporary file.
     *
     * @param workbook
     *            the workbook to write
     * @return the temporary file
     * @throws IOException
     *             if an I/O error occurs
     */
    public File writeWorkbookToTmpFile(XSSFWorkbook workbook) throws IOException {
        File tmpFile = File.createTempFile(TMP_LOCATION, TMP_EXTENSION);
        writeWorkbookToFile(workbook, tmpFile);
        return tmpFile;
    }

    /**
     * Saves the supplied workbook to the supplied file.
     *
     * @param workbook
     *            the workbook to write
     * @param file
     *            the destination file
     * @throws IOException
     *             if an I/O error occurs
     */
    public void writeWorkbookToFile(XSSFWorkbook workbook, File file) throws IOException {
        try (FileOutputStream out = new FileOutputStream(file)) {
            workbook.write(out);
        }
    }
}
