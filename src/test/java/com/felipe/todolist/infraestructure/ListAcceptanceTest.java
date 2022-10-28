package com.felipe.todolist.infraestructure;

import com.felipe.todolist.domain.model.ToDoList;
import com.felipe.todolist.domain.persistence.ListRepository;
import com.felipe.todolist.infraestructure.controllers.ListController;
import com.felipe.todolist.infraestructure.model.lists.ToDoListWithDateVO;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ListAcceptanceTest {

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
        ToDoListWithDateVO toDoListWithDateVO = ToDoListWithDateVO.builder()
                .name("Cosas por hacer")
                .description("Cosas por hacer antes del 31 de octubre")
                .user("felipe@gmail.com")
                .build();
        given().contentType(ContentType.JSON)
                .body(toDoListWithDateVO)
        .when()
                .post(String.format("http://localhost:%s/lists", port))
        .then()
                .statusCode(201)
                .body(containsString("Cosas por hacer"))
                .body(containsString("date"));
    }

    @Test
    void shouldCreateAListAndReturnStatusCode400() {
        ToDoListWithDateVO toDoListWithDateVO = ToDoListWithDateVO.builder()
                .name(null)
                .description("Cosas por hacer antes del 31 de octubre")
                .user("felipe@gmail.com")
                .build();
        given().contentType(ContentType.JSON)
                .body(toDoListWithDateVO)
        .when()
                .post(String.format("http://localhost:%s/lists", port))
        .then()
                .statusCode(is(400))
                .body(containsString("Name is empty"));
    }

    @Test
    void shouldFindAListSuccesful() {
        when(repository.existsById(100L)).thenReturn(true);
        when(repository.findById(100L)).thenReturn(toDoListOut);
        ToDoListWithDateVO toDoListWithDateVO = ToDoListWithDateVO.builder()
                .name("Cosas por hacer")
                .description("Cosas por hacer antes del 31 de octubre")
                .user("felipe@gmail.com")
                .build();
        given().contentType(ContentType.JSON)
                .body(toDoListWithDateVO)
                .when()
                .get(String.format("http://localhost:%s/lists/100", port))
                .then()
                .statusCode(200)
                .body(containsString("100"))
                .body(containsString("Cosas por hacer"));
    }

    @Test
    void shouldThrowNoSuchElementExceptionWhenFindByIdAndToDoListNotExists() {
        when(repository.existsById(anyLong())).thenReturn(false);
        when(repository.findById(100L)).thenReturn(toDoListOut);
        ToDoListWithDateVO toDoListWithDateVO = ToDoListWithDateVO.builder()
                .name("Cosas por hacer")
                .description("Cosas por hacer antes del 31 de octubre")
                .build();
        given().contentType(ContentType.JSON)
                .body(toDoListWithDateVO)
                .when()
                .patch(String.format("http://localhost:%s/lists/100", port))
                .then()
                .statusCode(404)
                .body(containsString("Solicitud errada"));
    }

    @Test
    void shouldUpdateAListSuccesful() {
        when(repository.existsById(100L)).thenReturn(true);
        when(repository.findById(100L)).thenReturn(toDoListOut);
        when(repository.update(any(ToDoList.class))).thenReturn(toDoListOut);
        ToDoListWithDateVO toDoListWithDateVO = ToDoListWithDateVO.builder()
                .name("Cosas por hacer")
                .description("Cosas por hacer antes del 31 de octubre")
                .build();
        given().contentType(ContentType.JSON)
                .body(toDoListWithDateVO)
                .when()
                .patch(String.format("http://localhost:%s/lists/100", port))
        .then()
                .statusCode(200)
                .body(containsString("100"));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenNameIsNull() {
        when(repository.existsById(100L)).thenReturn(true);
        when(repository.findById(100L)).thenReturn(toDoListOut);
        when(repository.update(any(ToDoList.class))).thenReturn(toDoListOut);
        ToDoListWithDateVO toDoListWithDateVO = ToDoListWithDateVO.builder()
                .name(null)
                .description("Cosas por hacer antes del 31 de octubre")
                .build();
        given().contentType(ContentType.JSON)
                .body(toDoListWithDateVO)
                .when()
                .patch(String.format("http://localhost:%s/lists/100", port))
                .then()
                .statusCode(400)
                .body(containsString("Name is empty"));
    }

    @Test
    void shouldThrowNoSuchElementExceptionWhenUpdateAndToDoListNotExists() {
        when(repository.existsById(anyLong())).thenReturn(false);
        when(repository.findById(100L)).thenReturn(toDoListOut);
        when(repository.update(any(ToDoList.class))).thenReturn(toDoListOut);
        ToDoListWithDateVO toDoListWithDateVO = ToDoListWithDateVO.builder()
                .name("Cosas por hacer")
                .description("Cosas por hacer antes del 31 de octubre")
                .build();
        given().contentType(ContentType.JSON)
                .body(toDoListWithDateVO)
                .when()
                .patch(String.format("http://localhost:%s/lists/100", port))
                .then()
                .statusCode(404);
    }

    @Test
    void shouldDeleteAListSuccesful() {
        when(repository.existsById(100L)).thenReturn(true);
        given().contentType(ContentType.JSON)
                .when()
                .delete(String.format("http://localhost:%s/lists/100", port))
        .then()
                .statusCode(200);
    }

    @Test
    void shouldThrowNoSuchElementExceptionWhenDeleteAndToDoListNotExists() {
        when(repository.existsById(anyLong())).thenReturn(false);

        given().contentType(ContentType.JSON)
                .when()
                .delete(String.format("http://localhost:%s/lists/100", port))
                .then()
                .statusCode(404)
                .body(containsString("List not found"));
    }
}
