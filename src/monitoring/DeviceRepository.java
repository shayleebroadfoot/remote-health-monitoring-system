package monitoring;

import domain.Device;

import java.util.List;
import java.util.Optional;

public interface DeviceRepository
{
    public Optional<Device> findById(String id);

    public Optional<List<Device>> findByPatientId(String patientId);

    public List<Device> findAll();

    public void save(Device device);

    public void delete(String id);
}
