package io;

import domain.VitalSigns;

import java.util.List;

public interface BasestationReader
{
    List<VitalSigns> readAll();
}
