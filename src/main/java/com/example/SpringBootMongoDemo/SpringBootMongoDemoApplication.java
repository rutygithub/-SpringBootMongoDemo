package com.example.SpringBootMongoDemo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class SpringBootMongoDemoApplication {
    //This class also implements Spring’s CommandLineRunner interface.
    // CommandLineRunner is a simple Spring Boot interface with a run method.
    // Spring Boot will automatically call the run method of all beans
    // implementing this interface after the application context has been
    // loaded.
    //https://www.youtube.com/watch?v=ssj0CGxv60k&ab_channel=Amigoscode
    //Spring Boot Tutorial - Build a Rest Api with MongoDB

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMongoDemoApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(StudentRepository studentRepository,
                             MongoTemplate mongoTemplate) {
        Address address = new Address("U.S.", "123456", "San Francisco");
        String email = "lucksamyu@gmail.com";

        return args -> {

            Student student = new Student("James", "Bond", email, Gender.MALE
                    , address, List.of("Computer Science", "Math"),
                    BigDecimal.TEN, LocalDateTime.now());

//            usingMongoTemplateAndQuery(studentRepository, mongoTemplate,
//            email, student);
            studentRepository.findStudentByEmail(email).ifPresentOrElse((s) -> {
                System.out.println(s + " already exists");
            }, () -> {
                studentRepository.insert(student);
                System.out.println("Insert the student " + student);
            });
        };

    }

    private static void usingMongoTemplateAndQuery(StudentRepository studentRepository, MongoTemplate mongoTemplate, String email, Student student) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));
        List<Student> students = mongoTemplate.find(query, Student.class);
        if (students.size() > 1) {
            throw new IllegalStateException("found many students with " +
                    "email" + email);
        }

        if (students.isEmpty()) {
            studentRepository.insert(student);
            System.out.println("Insert the student" + student);
        } else {
            System.out.println(student + "already exists");
        }
    }
}
