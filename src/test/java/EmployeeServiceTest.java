import org.atelier1.exception.CannotDeleteAdminException;
import org.atelier1.exception.EmailAlreadyExistsException;
import org.atelier1.exception.EmployeeNotFoundException;
import org.atelier1.model.Employee;
import org.atelier1.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeServiceTest {
    private EmployeeService employeeService;

    @BeforeEach
    public void setUp() {
        employeeService = new EmployeeService();
    }

    @Test
    @DisplayName("Test de l'ajout d'un employé")
    public void testAddEmployee() {
        Employee employee = employeeService.addEmployee("John", "Doe", "john.doe@example.com", "Developer", new Date());
        assertNotNull(employee.getId());
        assertEquals("John", employee.getFirstName());
    }

    @Test
    @DisplayName("Test duplication d'email")
    public void testAddEmployeeDuplicateEmail() {
        employeeService.addEmployee("John", "Doe", "john.doe@example.com", "Developer", new Date());
        assertThrows(EmailAlreadyExistsException.class, () ->
                employeeService.addEmployee("Jane", "Smith", "john.doe@example.com", "Tester", new Date()));
    }

    @Test
    @DisplayName("Test suppression employé")
    public void testDeleteEmployee() {
        Employee employee = employeeService.addEmployee("John", "Doe", "john.doe@example.com", "Developer", new Date());
        employeeService.deleteEmployee(employee.getId());
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.deleteEmployee(employee.getId()));
    }

    @Test
    @DisplayName("Test suppression d'un admin")
    public void testDeleteAdminEmployee() {
        Employee admin = employeeService.addEmployee("Admin", "User", "admin@example.com", "Admin", new Date());
        assertThrows(CannotDeleteAdminException.class, () -> employeeService.deleteEmployee(admin.getId()));
    }

    @Test
    @DisplayName("Test modification d'un employé")
    public void testUpdateEmployee() {
        Employee employee = employeeService.addEmployee("John", "Doe", "john.doe@example.com", "Developer", new Date());
        employeeService.updateEmployee(employee.getId(), "John", "Smith", "john.smith@example.com", "Manager", new Date());
        Employee updatedEmployee = employeeService.updateEmployee(employee.getId(), "John", "Smith", "john.smith@example.com", "Manager", new Date());
        assertEquals("Smith", updatedEmployee.getLastName());
    }

    @Test
    @DisplayName("Test liste de tout les employés")
    public void testListAllEmployees() {
        employeeService.addEmployee("John", "Doe", "john.doe@example.com", "Developer", new Date());
        employeeService.addEmployee("Jane", "Smith", "jane.smith@example.com", "Tester", new Date());
        List<Employee> employees = employeeService.listAllEmployees();
        assertEquals(2, employees.size());
    }

    @Test
    @DisplayName("Test recherche d'un employé")
    public void testSearchEmployees() {
        employeeService.addEmployee("John", "Doe", "john.doe@example.com", "Developer", new Date());
        employeeService.addEmployee("Jane", "Smith", "jane.smith@example.com", "Tester", new Date());
        List<Employee> employees = employeeService.searchEmployees("Jane", null, null, 1, 10);
        assertEquals(1, employees.size());
        assertEquals("Jane", employees.get(0).getFirstName());
    }

    @Test
    @DisplayName("Test assignation d'un projet")
    public void testAssignProjects() {
        Employee employee = employeeService.addEmployee("John", "Doe", "john.doe@example.com", "Developer", new Date());
        employeeService.assignProjects(employee.getId(), Set.of("Project A", "Project B"));
        assertTrue(employee.getProjects().contains("Project A"));
        assertTrue(employee.getProjects().contains("Project B"));
    }
}
