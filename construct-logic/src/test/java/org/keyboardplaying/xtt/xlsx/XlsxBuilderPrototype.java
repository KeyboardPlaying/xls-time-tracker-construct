package org.keyboardplaying.xtt.xlsx;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.CellCopyPolicy;
import org.apache.poi.ss.usermodel.ConditionalFormatting;
import org.apache.poi.ss.util.CellReference;
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

            /* Save and remove conditional formatting */
            List<ConditionalFormatting> formattingRules = new ArrayList<>();
            XSSFSheetConditionalFormatting formatting = sheet.getSheetConditionalFormatting();
            for (int i = formatting.getNumConditionalFormattings() - 1; i >= 0; i--) {
                formattingRules.add(formatting.getConditionalFormattingAt(i));
                formatting.removeConditionalFormatting(i);
            }

            /* Prepare tracker rows */
            int currentYear = 2016;
            LocalDate dt = new LocalDate(currentYear, DateTimeConstants.JANUARY, 1);
            int r = startRow;
            final CellCopyPolicy policy = new CellCopyPolicy.Builder().build();
            while (dt.year().get() == currentYear) {
                /* Create rows */
                r = createMonth(sheet, dt, dayRow, monthRow, r, policy);
                dt = dt.plusMonths(1);
            }

            /* TODO Remove two first rows */

            /* TODO Apply conditional formatting */

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

    private int createMonth(XSSFSheet sheet, LocalDate dt, final int dayRow, final int monthRow, int row,
            final CellCopyPolicy policy) {
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

    private XSSFRow copyRow(XSSFSheet sheet, int srcRow, int destRow, final CellCopyPolicy policy) {
        sheet.copyRows(srcRow, srcRow + 1, destRow, policy);
        return sheet.getRow(destRow);
    }
}
