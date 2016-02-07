package org.keyboardplaying.xtt.xlsx;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.CellCopyPolicy;
import org.apache.poi.ss.usermodel.ConditionalFormattingRule;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFConditionalFormatting;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFSheetConditionalFormatting;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.junit.Test;

/**
 * Prototype
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
public class XlsxBuilderPrototype {

    private static final int NB_HEADER_ROWS = 2;

    private static final int COLUMN_MONTH = CellReference.convertColStringToIndex("U");
    private static final int COLUMN_DAY = CellReference.convertColStringToIndex("V");
    private static final int COLUMN_TECHNICAL_FIRST = CellReference.convertColStringToIndex("U");
    private static final int COLUMN_TECHNICAL_LAST = CellReference.convertColStringToIndex("AB");

    @Test
    @SuppressWarnings("javadoc")
    public void generateWorkbook() {
        try (InputStream in = getClass().getResourceAsStream("tracker-dev.xlsx");
                XSSFWorkbook wb = new XSSFWorkbook(in)) {
            final int dayRow = NB_HEADER_ROWS;
            final int monthRow = dayRow + 1;
            final int startRow = monthRow + 1;

            XSSFSheet sheet = wb.getSheet("Timesheet");

            /* Prepare tracker rows */
            int year = 2016;
            createRowsFromTemplate(sheet, year, dayRow, monthRow, startRow);

            /* Save and remove conditional formatting */
            applyConditionalFormattingToCopiedRows(sheet, startRow);

            /* Remove two first rows */
            removeTemplateRows(sheet, startRow);

            /* TODO Hide technical columns */

            /* Write output file */
            File out = new File("tracker-test.xlsx");
            wb.write(new FileOutputStream(out));
            wb.close();

            System.out.println(out.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createRowsFromTemplate(XSSFSheet sheet, int year, int dayRow, int monthRow, int startRow) {
        LocalDate dt = new LocalDate(year, DateTimeConstants.JANUARY, 1);
        int r = startRow;
        final CellCopyPolicy policy = new CellCopyPolicy();
        while (dt.year().get() == year) {
            /* Create rows */
            r = createMonth(sheet, dt, dayRow, monthRow, r, policy);
            dt = dt.plusMonths(1);
        }
    }

    private int createMonth(XSSFSheet sheet, LocalDate dt, int dayRow, int monthRow, int row, CellCopyPolicy policy) {
        int r = row;

        final int nbDays = dt.dayOfMonth().getMaximumValue();
        final int month = dt.monthOfYear().get();

        /* Insert days for the month */
        for (int day = 1; day <= nbDays; day++) {
            XSSFRow inserted = copyRow(sheet, dayRow, r, policy);
            inserted.getCell(COLUMN_MONTH).setCellValue(month);
            inserted.getCell(COLUMN_DAY).setCellValue(day);
            r++;
        }

        /* Insert month summary */
        XSSFRow inserted = copyRow(sheet, monthRow, r, policy);
        inserted.getCell(COLUMN_MONTH).setCellValue(month);
        r++;

        /* Group month rows */
        int groupRow = r - 2;
        sheet.groupRow(row, groupRow);
        sheet.setRowGroupCollapsed(groupRow, true);

        return r;
    }

    private XSSFRow copyRow(XSSFSheet sheet, int srcRow, int destRow, CellCopyPolicy policy) {
        sheet.copyRows(srcRow, srcRow + 1, destRow, policy);
        return sheet.getRow(destRow);
    }

    private void applyConditionalFormattingToCopiedRows(XSSFSheet sheet, int startRow) {
        final int lastRow = sheet.getLastRowNum();
        XSSFSheetConditionalFormatting formatting = sheet.getSheetConditionalFormatting();
        // Go from end to start because we will be removing them
        for (int i = formatting.getNumConditionalFormattings() - 1; i >= 0; i--) {
            // Get conditional formatting
            XSSFConditionalFormatting format = formatting.getConditionalFormattingAt(i);
            // Apply conditional formatting to new range
            CellRangeAddress[] ranges = computeNewFormattingRanges(format.getFormattingRanges(), startRow, lastRow);
            applyRulesToRanges(formatting, format, ranges);
            // remove previous version of the conditional formatting
            formatting.removeConditionalFormatting(i);
        }
    }

    private CellRangeAddress[] computeNewFormattingRanges(CellRangeAddress[] ranges, int startRow, int lastRow) {
        for (CellRangeAddress range : ranges) {
            range.setFirstRow(startRow);
            range.setLastRow(lastRow);
        }
        return ranges;
    }

    private void applyRulesToRanges(XSSFSheetConditionalFormatting formatting, XSSFConditionalFormatting format,
            CellRangeAddress[] ranges) {
        final int nbRules = format.getNumberOfRules();
        for (int j = 0; j < nbRules; j++) {
            ConditionalFormattingRule rule = format.getRule(j);
            formatting.addConditionalFormatting(ranges, rule);
        }
    }

    private void removeTemplateRows(XSSFSheet sheet, int startRow) {
        sheet.shiftRows(startRow, sheet.getLastRowNum(), NB_HEADER_ROWS - startRow);
    }
}
