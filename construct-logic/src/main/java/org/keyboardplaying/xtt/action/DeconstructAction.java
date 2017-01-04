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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.keyboardplaying.file.Unzipper;
import org.keyboardplaying.xtt.configuration.ProjectLocationHelper;
import org.keyboardplaying.xtt.xlsx.XlsxBuilder;
import org.keyboardplaying.xtt.xlsx.XlsxNormalizer;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Updates the exploded version of the tracker based on the XLSX file.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
public class DeconstructAction implements ProjectAction {

    private ProjectLocationHelper location;
    private XlsxNormalizer normalizer;
    private XlsxBuilder builder;

    /**
     * Sets the project location helper for this instance.
     *
     * @param location the new project location helper
     */
    @Autowired
    public void setLocationHelper(ProjectLocationHelper location) {
        this.location = location;
    }

    /**
     * Sets the XLSX builder for file assembling.
     *
     * @param normalizer the XLSX normalizer
     */
    @Autowired
    public void setXlsxNormalizer(XlsxNormalizer normalizer) {
        this.normalizer = normalizer;
    }

    /**
     * Sets the XLSX normalizer to use when deconstructing the tracker.
     *
     * @param builder the XLSX builder
     */
    @Autowired
    public void setXlsxBuilder(XlsxBuilder builder) {
        this.builder = builder;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.xtt.action.ProjectAction#perform()
     */
    @Override
    public void perform() throws ActionException {
        try (InputStream in = new FileInputStream(location.getConstructedFile());
             XSSFWorkbook workbook = new XSSFWorkbook(in)) {

            // Normalize file
            normalizer.normalizeProperties(workbook);
            normalizer.normalizeSheets(workbook);

            // Create temporary file with normalized XLS
            File tmpFile = builder.writeWorkbookToTmpFile(workbook);

            // Deconstruct temporary file
            Unzipper.unzip(tmpFile, location.getDeconstructedDirectory(), true);

            // Do some cleaning
            tmpFile.delete();
        } catch (IOException e) {
            throw new ActionException("action.error.deconstruct", e);
        }
    }
}
