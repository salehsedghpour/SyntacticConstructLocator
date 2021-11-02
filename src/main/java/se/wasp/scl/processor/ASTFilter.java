package se.wasp.scl.processor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import se.wasp.scl.util.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

import static se.wasp.scl.util.CSVHeadersEnum.*;

public class ASTFilter {
    private static final CSVFormat csvFormat = CSVFormat.Builder
            .create(CSVFormat.DEFAULT)
            .setHeader(CSVHeadersEnum.class)
            .build();
    private static final String[] OUTPUT_HEADERS = { "ELEMENT_TYPE", "LOCATION",
            "PARENT_TYPE", "PARENT_LOCATION", "FILEPATH", "EXTRA_INFO" };
    private static final String ELEMENT_TYPE = "ELEMENT_TYPE";
    private static final String LOCATION = "LOCATION";
    private static final String FILE_PATH = "FILEPATH";
    private static final String EXTRA_INFO = "EXTRA_INFO";
    private static final String PARENT_LOCATION = "PARENT_LOCATION";
    private static final String PARENT_ELEMENT_TYPE = "PARENT_ELEMENT_TYPE";
    private static final String ELEM_STR_TEMPLATE =
            String.format("%s%s%s%s%s%s",
                    ELEMENT_TYPE, LOCATION, PARENT_ELEMENT_TYPE, PARENT_LOCATION, FILE_PATH, EXTRA_INFO);


    public static void filterCLIInput(String content,
                                      SyntacticEnum syntactic, OutputType outputType) throws IOException {
        Reader in = new InputStreamReader(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));
        List<ASTRecord> filteredRecords = new LinkedList<>();
        Iterable<CSVRecord> records = csvFormat.parse(in);
        for (CSVRecord record : records) {
                if (record.get(ELEMENT_TYPE).toLowerCase().contains(syntactic.name().toLowerCase())) {
                filteredRecords.add(new ASTRecord(
                        record.get(ELEMENT_TYPE),
                        record.get(LOCATION),
                        record.get(PARENT_TYPE),
                        record.get(PARENT_LOCATION),
                        record.get(FILEPATH),
                        record.get(EXTRA_INFO)
                ));
            }


        }
        printModel(System.out, outputType, filteredRecords );
        //prepareOutput(filteredRecords);
    }
    private static void printModel(PrintStream ps, OutputType outputType, List<ASTRecord> records) throws IOException {
        switch (outputType){
            case CSV:
                printOutputAsCSV(records, ps);
                break;
            case TABLE:
            default:
                printOutputAsTable(records, ps);
                break;
        }
    }
    private static void printOutputAsCSV(List<ASTRecord> records, PrintStream ps) {
        try (CSVPrinter printer = new CSVPrinter(ps, CSVFormat.DEFAULT
                .withHeader(OUTPUT_HEADERS))){
            for (ASTRecord record : records) {
                printer.printRecord( record.getElementName(),
                        record.getLocation(),
                        record.getParentType(),
                        record.getParentLocation(),
                        record.getFilePath(),
                        record.getExtraInfo());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void printOutputAsTable(List<ASTRecord> records, PrintStream ps) {
        ps.println(populateElemStrTemplate(OUTPUT_HEADERS[0], OUTPUT_HEADERS[1], OUTPUT_HEADERS[5], OUTPUT_HEADERS[2],
                OUTPUT_HEADERS[3], OUTPUT_HEADERS[4]));
        records.forEach(element -> ps.println(getElemStr(element)));
    }
    private static String populateElemStrTemplate
            (
                    String elementType,
                    String location,
                    String extraInfo,
                    String parentType,
                    String parentLocation,
                    String filePath
            ) {
        return ELEM_STR_TEMPLATE
                .replace(ELEMENT_TYPE, String.format("%-30s", elementType))
                .replace(LOCATION, String.format("%-30s", location))
                .replace(EXTRA_INFO, String.format("%-30s", extraInfo))
                .replace(PARENT_ELEMENT_TYPE, String.format("%-30s", parentType))
                .replace(PARENT_LOCATION, String.format("%-30s", parentLocation))
                .replace(FILE_PATH, String.format("%-100s", filePath));
    }
    private static String getElemStr(ASTRecord element) {

        String elemInfo = populateElemStrTemplate
                (
                        element.getElementName(),
                        element.getLocation(),
                        element.getExtraInfo(),
                        element.getParentType(),
                        element.getParentLocation(),
                        element.getFilePath());

        return elemInfo;
    }



}
