package service;

import com.syf.project.dto.EmployeeDto;
import com.syf.project.entity.Employee;
import com.syf.project.service.EmployeeCacheService;
import com.syf.project.service.EmployeeEntityService;
import com.syf.project.service.EmployeeService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Spy
    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    EmployeeEntityService employeeEntityService;
    @Mock
    EmployeeCacheService employeeCacheService;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(employeeService, "timeout", 500);
    }

    @Test
    public void testSelectEmployee() {
        EmployeeDto employeeDto= EmployeeDto.builder().firstname("John").lastname("Doe")
                .lastname("Trump")
                .emp_id("1")
                .dept("CmpSc")
                .gender("Male")
                .salary(5000)
                .build();
        Employee employee = Employee.builder()
                .firstname("John")
                .lastname("Trump")
                .emp_id("1")
                .dept("CmpSc")
                .gender("Male")
                .salary(5000)
                .build();
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        employeeDtos.add(employeeDto);
        Mockito.doReturn(null).when(employeeCacheService).readFromLocalRedis(Mockito.any());
        Mockito.doReturn(employee).when(employeeEntityService).findByIdAndDept(Mockito.any(),Mockito.any());
        Assert.assertEquals(employeeDtos, employeeService.selectEmployee(employeeDtos));
    }

    @Test
    public void testCatchSelectEmployee() {
        EmployeeDto employeeDto= EmployeeDto.builder().firstname("John").lastname("Doe")
                .lastname("Trump")
                .emp_id("1")
                .dept("CmpSc")
                .gender("Male")
                .salary(5000)
                .build();
        Employee employee = Employee.builder()
                .firstname("John")
                .lastname("Trump")
                .emp_id("1")
                .dept("CmpSc")
                .gender("Male")
                .salary(5000)
                .build();
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        employeeDtos.add(employeeDto);
        Mockito.doThrow(RuntimeException.class).when(employeeCacheService).readFromLocalRedis(Mockito.any());
        //Mockito.doThrow(RuntimeException.class).when(employeeEntityService).findByIdAndDept(Mockito.any(),Mockito.any());
        Exception exception= assertThrows(Exception.class, () -> {
            employeeService.selectEmployee(employeeDtos);
        });
        assertNotNull(exception);
    }

    @Test
    public void testInsertEmployee() {
        EmployeeDto employeeDto= EmployeeDto.builder().firstname("John").lastname("Doe")
                .lastname("Trump")
                .emp_id("1")
                .dept("CmpSc")
                .gender("Male")
                .salary(5000)
                .build();
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        employeeDtos.add(employeeDto);
        Mockito.doNothing().when(employeeEntityService).insertEmployee(Mockito.any());
        Assert.assertEquals(true, employeeService.insertEmployees(employeeDtos));
    }

    @Test
    public void testCatchInsertEmployee() {
        EmployeeDto employeeDto= EmployeeDto.builder().firstname("John").lastname("Doe")
                .lastname("Trump")
                .emp_id("1")
                .dept("CmpSc")
                .gender("Male")
                .salary(5000)
                .build();
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        employeeDtos.add(employeeDto);
        Mockito.doThrow(RuntimeException.class).when(employeeEntityService).insertEmployee(Mockito.any());
        Exception exception= assertThrows(Exception.class, () -> {
            employeeService.insertEmployees(employeeDtos);
        });
        assertNotNull(exception);
    }

    @Test
    public void testUpdateEmployee() {
        List<EmployeeDto> employeeDto = new ArrayList<>();
        Mockito.doNothing().when(employeeEntityService).updateEmployee(Mockito.any());
        Assert.assertEquals(true, employeeService.updateEmployee(employeeDto));
    }

    @Test
    public void testCatchUpdateEmployee() {
        List<EmployeeDto> employeeDto = new ArrayList<>();
        Mockito.doThrow(RuntimeException.class).when(employeeEntityService).updateEmployee(Mockito.any());
        Exception exception= assertThrows(Exception.class, () -> {
            employeeService.updateEmployee(employeeDto);
        });
        assertNotNull(exception);
    }

    @Test
    public void testdeleteEmployee() {
        Mockito.doNothing().when(employeeEntityService).deleteEmployee(Mockito.any());
        Assert.assertEquals(true, employeeService.deleteEmployee("ABC"));
    }

    @Test
    public void testCatchdeleteEmployee() {
        Mockito.doThrow(RuntimeException.class).when(employeeEntityService).deleteEmployee(Mockito.any());
        Exception exception= assertThrows(Exception.class, () -> {
            employeeService.deleteEmployee("ABC");
        });
        assertNotNull(exception);
    }
}
