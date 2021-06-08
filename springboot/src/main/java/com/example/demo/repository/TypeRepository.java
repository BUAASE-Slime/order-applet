package com.example.demo.repository;

import com.example.demo.bean.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypeRepository extends JpaRepository<Type, Integer> {

    List<Type> findByLeimuTypeIn(List<Integer> categoryTypeList);

    List<Type> findByLeimuType(Integer categoryType);

}
