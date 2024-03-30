package com.example.SpringBootMongoDemo;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class StudentService {

    //@Autowired, right now @AllArgsConstructor have constructor injection,
    // no need @autowired.
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
}