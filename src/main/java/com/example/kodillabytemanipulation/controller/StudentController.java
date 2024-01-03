package com.example.kodillabytemanipulation.controller;

import com.example.kodillabytemanipulation.Student;
import com.example.kodillabytemanipulation.range.Range;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/api/students")
public class StudentController {

    private static Object object;

    @GetMapping("/generate")
    public Map<Integer, String> generateStudents(
          @Valid @RequestParam(defaultValue = "20")  @Range(min = 1, max = 5) int n,
          @Valid @RequestParam(defaultValue = "10")  @Range(min = 1, max = 5) int z) {

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<Map<String, String>> handleMethodArgumentNotValidExceptions(MethodArgumentNotValidException exc) {
        Map<String, String> errorsMap = new HashMap<>();
        List<ObjectError> errorsList = exc.getBindingResult().getAllErrors();
        errorsList.forEach((errorObject) -> {
            FieldError fieldError = (FieldError) errorObject;
            String name = fieldError.getField();
            String message = errorObject.getDefaultMessage();
            errorsMap.put(name, message);
        });
        return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<Map<String, String>> handleException(ConstraintViolationException exc) {
        Map<String, String> resultMap = new HashMap<>();
        String[] errorArray = exc.getMessage().split(":");
        resultMap.put(errorArray[0], errorArray[1]);
        return new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
    }
}
