package com.ust.flight_management.service;

import com.ust.flight_management.entity.Flight;
import org.apache.commons.csv.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.*;

@Service
public class CsvService {

    public List<Flight> parseCsv(MultipartFile file) {
        List<Flight> employees = new ArrayList<>();

        try (Reader reader = new InputStreamReader(file.getInputStream());
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord record : csvParser) {
                Flight emp = new Flight();
                emp.setId((long)Integer.parseInt(record.get("id")));
                emp.setAirline(record.get("name"));
                employees.add(emp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return employees;
    }
}

