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


    public static void filterCLIInput(String content,
                                      SyntacticEnum syntactic) throws IOException {
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
        prepareOutput(filteredRecords);
    }
    private static void prepareOutput(List<ASTRecord> records) throws IOException {
        PrintStream printStream = System.out;
        CSVPrinter csvPrinter = new CSVPrinter(printStream, csvFormat);
        for (ASTRecord record : records) {
            csvPrinter.printRecord(
                    record.getElementName(),
                    record.getLocation(),
                    record.getParentType(),
                    record.getParentLocation(),
                    record.getFilePath(),
                    record.getExtraInfo());
        }
    }


}
