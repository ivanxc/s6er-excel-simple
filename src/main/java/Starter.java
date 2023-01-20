import exception.ParameterException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

public class Starter {
    private static final int VALUES_AMOUNT = 3;

    public static void main(String[] args) {
        try (InputStream file = Starter.class.getResourceAsStream("name_java.xlsx");
             Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheetAt(0);
            List<String> values = new ArrayList<>();
            values.add("С405ММ799");
            values.add("С528ЕК777");
            values.add("К920МО197");

            Starter starter = new Starter();

            boolean found = starter.findNames(sheet, values);
            System.out.println("Values " + (found ? "found" : "not found"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean findNames(Sheet sheet, List<String> values) {
        if (values.size() != VALUES_AMOUNT) {
            throw new ParameterException("Given " + values.size() + " parameters but expected " + VALUES_AMOUNT);
        }
        // Можно еще проверить элементы values на соответствие шаблону номера

        Set<String> result = new HashSet<>();

        for (Row r : sheet) {
            Cell c = r.getCell(0);
            if (c != null) {
                String value;
                if (c.getCellType() == CellType.STRING) {
                    value = c.getStringCellValue();
                    if (values.contains(value)) {
                        result.add(value);
                    }
                }
            }
        }

//        result.forEach(v -> System.out.println(v + " was found"));
        return result.size() > 0;
    }
}