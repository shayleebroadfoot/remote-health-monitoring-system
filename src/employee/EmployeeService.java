package employee;

import domain.Employee;
import employee.EmployeeRepository;

import java.util.List;
import java.util.Optional;

public class EmployeeService
{
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository)
    {
        this.employeeRepository = employeeRepository;
    }

    public void createEmployee(Employee employee)
    {
        employeeRepository.save(employee);
    }

    public void updateEmployee(Employee employee)
    {
        employeeRepository.save(employee);
    }

    public void deleteEmployee(String id)
    {
        employeeRepository.delete(id);
    }

    public Optional<Employee> getEmployeeById(String id)
    {
        return employeeRepository.findById(id);
    }

    public List<Employee> getAllEmployees()
    {
        return employeeRepository.findAll();
    }
}
