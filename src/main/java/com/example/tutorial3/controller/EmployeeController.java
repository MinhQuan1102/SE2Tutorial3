package com.example.tutorial3.controller;

import com.example.tutorial3.model.Employee;
import com.example.tutorial3.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class EmployeeController {
    @Autowired
    EmployeeRepository employeeRepository;

    @RequestMapping(value = "/")
    public String getAllEmployees(Model model) {
        List<Employee> employees = employeeRepository.findAll();
        model.addAttribute("employees", employees);
        return "employeeList";
    }

    @RequestMapping(value = "/{id}")
    public String getEmployeeById(@PathVariable(value = "id") Long id, Model model) {
        Employee employee = employeeRepository.getById(id);
        model.addAttribute("employee", employee);
        return "employeeDetail";
    }

    @GetMapping("/addEmployee")
    public String addEmployee(Model model) {
        model.addAttribute("employee", new Employee());
        return "employeeAdd";
    }
    @PostMapping("/")
    public String addEmployee(@ModelAttribute("employee") Employee employee, Model model) {

        model.addAttribute("employee", employee);
        employeeRepository.save(employee);
        return "redirect:/";
    }

    @GetMapping("/updateEmployee/{id}")
    public String updateEmployee(@PathVariable(value = "id") Long id, Model model) {
        Employee employee = employeeRepository.getById(id);
        model.addAttribute("employee", employee);
        return "employeeUpdate";
    }

    @PostMapping("/updateEmployee/{id}")
    public String updateEmployee(@ModelAttribute("employee") Employee employee) {
        Optional<Employee> existingEmployee = employeeRepository.findById(employee.getId());

        if (existingEmployee.isPresent()) {
            // If the employee exists, update its fields with the new data
            Employee updatedEmployee = existingEmployee.get();
            updatedEmployee.setName(employee.getName());
            updatedEmployee.setAge(employee.getAge());
            updatedEmployee.setImage(employee.getImage());
            updatedEmployee.setAddress(employee.getAddress());

            // Save the updated employee to the database
            employeeRepository.save(updatedEmployee);
        }
        return "redirect:/";


    }
    @PostMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable(value = "id") Long id) {
        Optional<Employee> existingEmployee = employeeRepository.findById(id);

        if (existingEmployee.isPresent()) {
            Employee employee = existingEmployee.get();
            employeeRepository.delete(employee);
            return "redirect:/";
        }
        return "redirect:/";
    }
}
