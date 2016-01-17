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

import org.apache.poi.POIXMLProperties;
import org.apache.poi.POIXMLProperties.CoreProperties;
import org.apache.poi.openxml4j.util.Nullable;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;

/**
 * A class that normalizes the properties of an XLSX file.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
public class XlsxNormalizer {

    private String title;
    private String author;
    private String company;

    /**
     * Sets the title to use for the Excel file's properties.
     *
     * @param title
     *            the title of the Excel file
     */
    @Value("${xlsx.title}")
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the author to use for the Excel file's properties.
     *
     * @param author
     *            the author of the Excel file
     */
    @Value("${xlsx.author}")
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Sets the company to use for the Excel file's properties.
     *
     * @param company
     *            the company of the Excel file
     */
    @Value("${xlsx.company}")
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * Normalizes the time tracker workbook's properties to avoid unrequired changes in the SCM repository.
     * <p/>
     * This sets the author, company and title with the properties of the instance, and resets the last modification
     * date with the creation date.
     *
     * @param workbook
     *            the workbook to normalize
     */
    public void normalizeProperties(XSSFWorkbook workbook) {
        POIXMLProperties properties = workbook.getProperties();
        CoreProperties coreProperties = properties.getCoreProperties();

        // Set author
        coreProperties.setCreator(author);
        coreProperties.getUnderlyingProperties().setLastModifiedByProperty(author);
        properties.getExtendedProperties().getUnderlyingProperties().setCompany(company);

        // Set title
        coreProperties.setTitle(title);

        // Don't save last modification date
        coreProperties.setModified(new Nullable<>(coreProperties.getCreated()));
    }
}
