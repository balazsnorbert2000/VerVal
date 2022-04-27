package service;

import domain.Grade;
import domain.Homework;
import domain.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;
import validation.*;

class ServiceTest {

    private Service service;

    @BeforeEach
    void setup() {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Homework> homeworkValidator = new HomeworkValidator();
        Validator<Grade> gradeValidator = new GradeValidator();

        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "students.xml");
        HomeworkXMLRepository fileRepository2 = new HomeworkXMLRepository(homeworkValidator, "homework.xml");
        GradeXMLRepository fileRepository3 = new GradeXMLRepository(gradeValidator, "grades.xml");

        service = new Service(fileRepository1, fileRepository2, fileRepository3);
        service.saveStudent("10", "Student1", 531);
        service.saveStudent("11", "Student2", 532);
    }

    @AfterEach
    void delete() {
        service.deleteStudent("10");
        service.deleteStudent("11");
    }

    @Test
    void saveStudentWithValidAttributes() {
        int returnValue = service.saveStudent("3", "Pop Kornel", 533);
        Assertions.assertEquals(1, returnValue);
        if (returnValue == 1) {
            service.deleteStudent("3");
        }
    }

    @Test
    void saveStudentWithInvalidGroup() {
        int returnValue = service.saveStudent("5", "Pop Kornel", 1533);
        Assertions.assertNotEquals(1, returnValue);
    }

    @ParameterizedTest
    @ValueSource(strings = {"10", "11"})
    void deleteStudentWithValidId(String id) {
        boolean success = service.deleteStudent(id) == 1;
        Assertions.assertTrue(success);
    }

    @Test
    void saveHomeworkWithValidAttributes() {
        int returnValue = service.saveHomework("5", "Test", 8, 7);
        Assertions.assertEquals(1, returnValue);
        if (returnValue == 1) {
            service.deleteHomework("5");
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "aaaaaaa"})
    void deleteHomeworkWithInvalidId(String id) {
        boolean success = service.deleteHomework(id) == 1;
        Assertions.assertFalse(success);
    }
}