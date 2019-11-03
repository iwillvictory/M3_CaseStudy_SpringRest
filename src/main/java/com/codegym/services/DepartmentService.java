package com.codegym.services;

import com.codegym.models.Department;

import java.util.List;

public interface DepartmentService {
    List<Department> findAll();
    Department findById(int id);
    void save(Department department);
    void remove(int id);
}
