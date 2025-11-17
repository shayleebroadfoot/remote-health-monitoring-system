package employee;

import domain.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository
{
    public Optional<Employee> findById(String id);

    Optional<Employee> findByUsername(String username);

    List<Employee> findAll();

    void save(Employee employee);

    void delete(String id);
}
