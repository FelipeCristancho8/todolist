package com.felipe.todolist.infraestructure;

import com.felipe.todolist.domain.model.ToDoList;
import com.felipe.todolist.domain.persistence.ListRepository;
import com.felipe.todolist.infraestructure.controllers.ListController;
import com.felipe.todolist.infraestructure.model.ToDoListVO;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.context.WebApplicationContext;

import static io.restassured.RestAssured.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.webAppContextSetup;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ListAcceptanceTest {

    @LocalServerPort
    private int port;

    @MockBean
    ListRepository repository;

    private ToDoList toDoListOut;

    @InjectMocks
    private ListController controller;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp() {
        toDoListOut = ToDoList.builder()
                .id(100L)
                .name("Cosas por hacer")
                .description("Cosas por hacer antes del 31 de octubre")
                .user("felipe@gmail.com").build();

        openMocks(this);
        when(repository.save(any(ToDoList.class))).thenReturn(toDoListOut);
        webAppContextSetup(context);
        RestAssuredMockMvc.standaloneSetup(controller);
    }

    @Test
    void shouldCreateAListSuccesful() {
        ToDoListVO toDoListVO = ToDoListVO.builder()
                .name("Cosas por hacer")
                .description("Cosas por hacer antes del 31 de octubre")
                .user("felipe@gmail.com")
                .build();
        given().contentType(ContentType.JSON)
                .body(toDoListVO)
        .when()
                .post(String.format("http://localhost:%s/lists", port))
        .then()
                .statusCode(201)
                .body(containsString("100"))
                .body(containsString("date"));
    }

    @Test
    void shouldCreateAListAndReturnStatusCode400() {
        ToDoListVO toDoListVO = ToDoListVO.builder()
                .name(null)
                .description("Cosas por hacer antes del 31 de octubre")
                .user("felipe@gmail.com")
                .build();
        given().contentType(ContentType.JSON)
                .body(toDoListVO)
        .when()
                .post(String.format("http://localhost:%s/lists", port))
        .then()
                .statusCode(is(400))
                .body(containsString("Name is empty"));
    }
}
