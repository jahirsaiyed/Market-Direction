package com.jjs.market.core;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class CSVUtils {

    public static List<String[]> readAll(Reader reader) {

        CSVParser parser = new CSVParserBuilder()
                .withSeparator(',')
                .withIgnoreQuotations(true)
                .build();

        CSVReader csvReader = new CSVReaderBuilder(reader)
                .withSkipLines(0)
                .withCSVParser(parser)
                .build();

        List<String[]> list = new ArrayList<>();
        try {
            list = csvReader.readAll();
            reader.close();
            csvReader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }
}
