package com.codegym.repositories;

import com.codegym.models.Department;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DepartmentRepository extends PagingAndSortingRepository<Department,Integer> {
}
