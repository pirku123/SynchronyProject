package controller;

import com.syf.project.controller.EmployeeController;
import com.syf.project.dto.EmployeeDto;
import com.syf.project.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.junit.Assert;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

    @Spy
    @InjectMocks
    private EmployeeController employeeController;
    @Mock
    private EmployeeService employeeService;

    @Test
    public void testSelectEmployee() {
        List<EmployeeDto> employeeDto = new ArrayList<>();
        Mockito.doReturn(employeeDto).when(employeeService).selectEmployee(Mockito.any());
        Assert.assertEquals(new ArrayList<EmployeeDto>(), employeeController.selectEmployee(employeeDto));
    }

    @Test
    public void testCatchSelectEmployee() {
        List<EmployeeDto> employeeDto = new ArrayList<>();
        Mockito.doThrow(RuntimeException.class).when(employeeService).selectEmployee(Mockito.any());
        Exception exception= assertThrows(Exception.class, () -> {
            employeeController.selectEmployee(employeeDto);
        });
        assertNotNull(exception);
    }

    @Test
    public void testInsertEmployee() {
        List<EmployeeDto> employeeDto = new ArrayList<>();
        Mockito.doReturn(true).when(employeeService).insertEmployees(Mockito.any());
        Assert.assertEquals(true, employeeController.insertEmployee(employeeDto));
    }

    @Test
    public void testCatchInsertEmployee() {
        List<EmployeeDto> employeeDto = new ArrayList<>();
        Mockito.doThrow(RuntimeException.class).when(employeeService).insertEmployees(Mockito.any());
        Exception exception= assertThrows(Exception.class, () -> {
            employeeController.insertEmployee(employeeDto);
        });
        assertNotNull(exception);
    }

    @Test
    public void testUpdateEmployee() {
        List<EmployeeDto> employeeDto = new ArrayList<>();
        Mockito.doReturn(true).when(employeeService).updateEmployee(Mockito.any());
        Assert.assertEquals(true, employeeController.updateEmployee(employeeDto));
    }

    @Test
    public void testCatchUpdateEmployee() {
        List<EmployeeDto> employeeDto = new ArrayList<>();
        Mockito.doThrow(RuntimeException.class).when(employeeService).updateEmployee(Mockito.any());
        Exception exception= assertThrows(Exception.class, () -> {
            employeeController.updateEmployee(employeeDto);
        });
        assertNotNull(exception);
    }

    @Test
    public void testdeleteEmployee() {
        Mockito.doReturn(true).when(employeeService).deleteEmployee(Mockito.any());
        Assert.assertEquals(true, employeeController.deleteEmployee("ABC"));
    }

    @Test
    public void testCatchdeleteEmployee() {
        Mockito.doThrow(RuntimeException.class).when(employeeService).deleteEmployee(Mockito.any());
        Exception exception= assertThrows(Exception.class, () -> {
            employeeController.deleteEmployee("ABC");
        });
        assertNotNull(exception);
    }
}
