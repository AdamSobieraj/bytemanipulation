package com.example.kodillabytemanipulation.controller;

import com.example.kodillabytemanipulation.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private static Object object;

    @GetMapping("/generate")
    public Map<Integer, String> generateStudents(
            @RequestParam(defaultValue = "20") int n,
            @RequestParam(defaultValue = "10") int z) {

        Map<Integer, String> result = new HashMap<>();

        for (int i = 0; i < n; i++) {
            Student student = new Student(z);
            result.put(student.hashCode(), getObjectIndexNumber(student));
        }

        return result;
    }

    private static String getObjectIndexNumber(Object obj) {
        try {
            Field field = Student.class.getDeclaredField("indexNumber");
            field.setAccessible(true);
            object = field.get(obj);
            return (String) object;
        } catch (Exception e) {
            throw new RuntimeException("Error getting object ID", e);
        }
    }
}
