package service;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import domain.Homework;
import domain.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;

public class ServiceMockTest {

    private Service service;

    @Mock
    StudentXMLRepository studentXMLRepositoryMock;

    @Mock
    HomeworkXMLRepository homeworkXMLRepositoryMock;

    @Mock
    GradeXMLRepository gradeXMLRepositoryMock;


    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        service = new Service(studentXMLRepositoryMock, homeworkXMLRepositoryMock, gradeXMLRepositoryMock);
    }

    @AfterEach
    void delete() {

    }

    @Test
    void saveStudentWithValidAttributes() {
        Student student = new Student("5", "Pop Kornel", 533);
        when(studentXMLRepositoryMock.save(student))
                .thenReturn(null);
        Assertions.assertEquals(
                1,
                service.saveStudent("5", "Pop Kornel", 533)
        );
    }

    @Test
    void saveHomeworkWithValidAttributes() {
        Homework homework = new Homework("10", "valami", 9, 7);
        when(homeworkXMLRepositoryMock.save(homework))
                .thenReturn(null);
        Assertions.assertNotEquals(
                0,
                service.saveHomework("10", "valami", 9, 7)
        );
        Mockito.verify(homeworkXMLRepositoryMock).save(homework);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "aaaaaaa"})
    void deleteHomeworkWithInvalidId(String id) {
        when(homeworkXMLRepositoryMock.delete(anyString())).thenReturn(null);
        boolean success = service.deleteHomework(id) == 1;
        Assertions.assertFalse(success);
        Mockito.verify(homeworkXMLRepositoryMock).delete(anyString());
    }

}
