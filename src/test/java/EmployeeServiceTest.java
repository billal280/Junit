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
    private Employee employee ;
    @BeforeEach
    public void setUp() {
        employeeService = new EmployeeService();
        employee = employeeService.addEmployee("John", "Doe", "john.doe@example.com", "Developer", new Date());

    }

    @Test
    @DisplayName("Test de l'ajout d'un employé")
    public void testAddEmployee() {
        assertNotNull(employee.getId());
        assertEquals("John", employee.getFirstName());
    }

    @Test
    @DisplayName("Test duplication d'email")
    public void testDuplicateEmail(){
        assertThrows(EmailAlreadyExistsException.class,()-> employeeService.addEmployee("Jane", "Dose", "john.doe@example.com", "Manager", new Date()));
    }

    @Test
    @DisplayName("Test suppresion d'employer")
    public void testSuppEmployee(){
        employeeService.deleteEmployee(employee.getId());
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.deleteEmployee(employee.getId()));
    }
    @Test
    @DisplayName("Test suppresion d'admin")
    public void testSuppAdmin(){
        Employee admin = employeeService.addEmployee("John", "Doe", "admin@kaiman.fr", "Admin", new Date());
        assertThrows(CannotDeleteAdminException.class, () -> employeeService.deleteEmployee(admin.getId()));
    }
}
