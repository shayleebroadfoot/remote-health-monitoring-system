package monitoring;

import domain.Device;

import java.util.*;

public class InMemoryDeviceRepository implements DeviceRepository
{
    private Map<String, Device> devices;

    public InMemoryDeviceRepository()
    {
        devices = new HashMap<>();
    }

    @Override
    public Optional<Device> findById(String id)
    {
        return Optional.ofNullable(devices.get(id));
    }

    @Override
    public Optional<List<Device>> findByPatientId(String patientId)
    {
        return (Optional.of(new ArrayList<>(devices.values())));
    }

    @Override
    public List<Device> findAll()
    {
        return new ArrayList<>(devices.values());
    }

    // Save device to repository
    // If it's a new device, create a unique ID. Else save with existing ID.
    @Override
    public void save(Device device)
    {
        if (device.getId() == null)
            device.setId(UUID.randomUUID().toString());
        devices.put(device.getId(), device);
    }

    @Override
    public void delete(String id)
    {
        devices.remove(id);
    }
}
