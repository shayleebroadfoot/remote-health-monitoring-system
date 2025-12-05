package employee;

import domain.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository
{
    public Optional<Employee> findById(String id);

    public Optional<Employee> findByUsername(String username);

    public List<Employee> findAll();

    public void save(Employee employee);

    public void delete(String id);
}
