package com.ExamNinja.demo.Controller;

import com.ExamNinja.demo.model.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
public class EmployeeController {

    private List<Employee> employees = createList();

    @RequestMapping(value = "/employees", method = RequestMethod.GET, produces = "application/json")
    public List<Employee> getEmployees() {
        return employees;
    }

    @RequestMapping(value = "/employees", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public Employee addEmployee(@RequestBody Employee newEmployee) {
        employees.add(newEmployee);
        return newEmployee;
    }

    private static List<Employee> createList() {
        List<Employee> tempEmployees = new ArrayList<>();

        Employee emp1 = new Employee();
        emp1.setName("ebc");
        emp1.setId(1);
        emp1.setDepartment("HR");

        Employee emp2 = new Employee();
        emp2.setName("emp2");
        emp2.setId(2);
        emp2.setDepartment("Finance");

        Employee emp3 = new Employee();
        emp3.setName("abc");
        emp3.setId(3);
        emp3.setDepartment("HR3");

        tempEmployees.add(emp1);
        tempEmployees.add(emp2);
        tempEmployees.add(emp3);

        return tempEmployees;
    }
}
