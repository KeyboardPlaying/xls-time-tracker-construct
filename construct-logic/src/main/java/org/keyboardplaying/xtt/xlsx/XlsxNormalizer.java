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
import org.apache.poi.hssf.util.PaneInformation;
import org.apache.poi.openxml4j.util.Nullable;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;

/**
 * A class that normalizes the properties of an XLSX file.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
public class XlsxNormalizer {

    private static final int ZOOM_100 = 100;

    @Value("${xlsx.properties.title}")
    private String title;
    @Value("${xlsx.properties.author}")
    private String author;
    @Value("${xlsx.properties.company}")
    private String company;

    @Value("${xlsx.cell.tracker}")
    private String trackerActiveRange;
    @Value("${xlsx.cell.config}")
    private String configActiveRange;

    /**
     * Sets the title to use for the Excel file's properties.
     *
     * @param title
     *            the title of the Excel file
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the author to use for the Excel file's properties.
     *
     * @param author
     *            the author of the Excel file
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Sets the company to use for the Excel file's properties.
     *
     * @param company
     *            the company of the Excel file
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * Sets the active range for the tracker sheet.
     *
     * @param trackerActiveRange
     *            the tracker sheet
     */
    public void setTrackerActiveRange(String trackerActiveRange) {
        this.trackerActiveRange = trackerActiveRange;
    }

    /**
     * Sets the active range for the configuration sheet.
     *
     * @param configActiveRange
     *            the configuration sheet
     */
    public void setConfigActiveRange(String configActiveRange) {
        this.configActiveRange = configActiveRange;
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

    /**
     * Normalizes the time tracker workbook's selected sheet and cells and applies some styling.
     *
     * @param workbook
     *            the workbook to normalize
     */
    public void normalizeSheets(XSSFWorkbook workbook) {
        normalizeSheet(workbook.getSheetAt(XlsxTracker.TAB_INDEX_TRACKER), trackerActiveRange);
        normalizeSheet(workbook.getSheetAt(XlsxTracker.TAB_INDEX_CONFIG), configActiveRange);
        workbook.setActiveSheet(XlsxTracker.TAB_INDEX_TRACKER);
    }

    private void normalizeSheet(XSSFSheet sheet, String activeRange) {
        updateSheetPosition(sheet);
        sheet.setZoom(ZOOM_100);
        sheet.setDisplayGridlines(false);
        sheet.setSelected(false);

        setSheetActiveRange(sheet, activeRange);
    }

    private void setSheetActiveRange(XSSFSheet sheet, String activeRange) {
        sheet.setActiveCell(new CellAddress(activeRange)); // FIXME doesn't work when cell is merged
    }

    private void updateSheetPosition(XSSFSheet sheet) {
        PaneInformation pane = sheet.getPaneInformation();
        if (pane == null) {
            sheet.getCTWorksheet().getSheetViews().getSheetViewArray(0).setTopLeftCell(CellAddress.A1.formatAsString());
        } else {
            sheet.showInPane(pane.getHorizontalSplitPosition(), pane.getVerticalSplitPosition());
        }
    }
}
