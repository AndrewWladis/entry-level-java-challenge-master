package com.challenge.api.service;

import com.challenge.api.model.Employee;
import com.challenge.api.model.EmployeeModel;
import com.challenge.api.model.EmployeeRequest;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final ConcurrentMap<UUID, Employee> employees = new ConcurrentHashMap<>();

    public EmployeeService() {
        addEmployee("Ada", "Lovelace", "Principal Engineer", "ada@example.com");
        addEmployee("Alan", "Turing", "Software Engineer", "alan@example.com");
    }

    public List<Employee> getAllEmployees() {
        return List.copyOf(employees.values());
    }

    public Optional<Employee> getEmployeeByUuid(UUID uuid) {
        return Optional.ofNullable(employees.get(uuid));
    }

    public Employee createEmployee(EmployeeRequest request) {
        EmployeeModel employee = new EmployeeModel();
        employee.setUuid(UUID.randomUUID());
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setSalary(request.getSalary());
        employee.setAge(request.getAge());
        employee.setJobTitle(request.getJobTitle());
        employee.setEmail(request.getEmail());
        employee.setContractHireDate(
                request.getContractHireDate() == null ? Instant.now() : request.getContractHireDate());
        employee.setContractTerminationDate(request.getContractTerminationDate());
        employees.put(employee.getUuid(), employee);
        return employee;
    }

    private void addEmployee(String firstName, String lastName, String jobTitle, String email) {
        EmployeeModel employee = new EmployeeModel();
        employee.setUuid(UUID.randomUUID());
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setJobTitle(jobTitle);
        employee.setEmail(email);
        employee.setContractHireDate(Instant.now());
        employees.put(employee.getUuid(), employee);
    }
}
