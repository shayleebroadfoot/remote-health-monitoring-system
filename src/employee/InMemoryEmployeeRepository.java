package employee;

import domain.Employee;

import java.util.*;

public class InMemoryEmployeeRepository implements EmployeeRepository
{
    private final Map<String, Employee> employees;

    public InMemoryEmployeeRepository()
    {
        this.employees = new HashMap<>();
    }

    @Override
    public Optional<Employee> findById(String id)
    {
        return Optional.ofNullable(employees.get(id));
    }

    @Override
    public Optional<Employee> findByUsername(String username)
    {
        for (Employee employee : employees.values())
        {
            if (employee.getUsername().equals(username))
                return Optional.of(employee);
        }

        return Optional.empty();
    }

    @Override
    public List<Employee> findAll()
    {
        return new ArrayList<>(employees.values());
    }

    @Override
    public void save(Employee employee)
    {
        if (employee.getId() == null)
            employee.setId(UUID.randomUUID().toString());

        employees.put(employee.getId(), employee);
    }

    @Override
    public void delete(String id)
    {
        employees.remove(id);
    }
}