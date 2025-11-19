package employee;

import domain.Employee;

import java.util.Optional;

public class AuthenticationService
{
    private static AuthenticationService instance;
    private final EmployeeRepository employeeRepository;
    private Employee currentUser;

    private AuthenticationService(EmployeeRepository employeeRepository)
    {
        this.employeeRepository = employeeRepository;
    }

    public static synchronized AuthenticationService getInstance(EmployeeRepository employeeRepository)
    {
        if (instance == null)
            instance = new AuthenticationService(employeeRepository);

        return instance;
    }

    public boolean login(String username, String password)
    {
        if (currentUser != null)
            return false;

        Optional<Employee> found = employeeRepository.findByUsername(username);

        if (found.isPresent() && found.get().getUsername() != null && password.equals(found.get().getPassword()))
        {
            currentUser = found.get();
            return true;
        }

        currentUser = null;
        return false;
    }

    public void logout()
    {
        currentUser = null;
    }

    public Optional<Employee> getCurrentUser()
    {
        return Optional.ofNullable(currentUser);
    }

    public boolean isLoggedIn()
    {
        return currentUser != null;
    }
}