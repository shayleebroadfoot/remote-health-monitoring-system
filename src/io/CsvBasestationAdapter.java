package io;

import domain.VitalSigns;

import java.util.List;

public class CsvBasestationAdapter implements BasestationReader
{
    private final CsvFileReader csvReader;

    public CsvBasestationAdapter(CsvFileReader csvReader)
    {
        this.csvReader = csvReader;
    }

    @Override
    public List<VitalSigns> readAll()
    {
        return csvReader.getVitalSigns();
    }
}
