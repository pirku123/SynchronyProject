package service;

import com.syf.project.entity.Employee;
import com.syf.project.repository.EmployeeRepository;
import com.syf.project.service.EmployeeEntityService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class EmployeeEntityServiceTest {

    @Mock
    EmployeeRepository repository;

    @Mock
    EntityManager entityManager;

    @Spy
    @InjectMocks
    EmployeeEntityService service;

    @Test
    public void testFindByIdAndDept() {
        Mockito.doReturn(new Employee()).when(repository).findByIdAndDept(Mockito.any(),Mockito.any());
        Assert.assertEquals(new Employee(), service.findByIdAndDept("",""));
    }

    @Test
    public void testCatchFindByIdAndDept() {
        Mockito.doThrow(RuntimeException.class).when(repository).findByIdAndDept(Mockito.any(),Mockito.any());
        Exception exception= assertThrows(Exception.class, () -> {
            service.findByIdAndDept("","");
        });
        assertNotNull(exception);
    }

    @Test
    public void testUpdateEmployee() {
        List<Employee> employee = new ArrayList<>();
        service.updateEmployee(employee);
    }

    @Test
    public void testCatchUpdateEmployee() {
        List<Employee> employee = new ArrayList<>();
        Mockito.doThrow(RuntimeException.class).when(repository).saveAll(Mockito.any());
        Exception exception= assertThrows(Exception.class, () -> {
            service.updateEmployee(employee);
        });
        assertNotNull(exception);
    }

    @Test
    public void testInsertEmployee() {
        Employee employee = new Employee();
        Mockito.doNothing().when(entityManager).persist(Mockito.any());
        service.insertEmployee(employee);
    }

    @Test
    public void testCatchInsertEmployee() {
        Employee employee = new Employee();
        Mockito.doThrow(RuntimeException.class).when(entityManager).persist(Mockito.any());
        Exception exception= assertThrows(Exception.class, () -> {
            service.insertEmployee(employee);
        });
        assertNotNull(exception);
    }

    @Test
    public void testdeleteEmployee() {
        Mockito.doNothing().when(repository).deleteById(Mockito.any());
        service.deleteEmployee("");
    }

    @Test
    public void testCatchdeleteEmployee() {
        Mockito.doThrow(RuntimeException.class).when(repository).deleteById(Mockito.any());
        Exception exception= assertThrows(Exception.class, () -> {
            service.deleteEmployee("ABC");
        });
        assertNotNull(exception);
    }
}
