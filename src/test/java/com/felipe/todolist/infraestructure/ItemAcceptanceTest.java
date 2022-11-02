package com.felipe.todolist.infraestructure;

import com.felipe.todolist.domain.items.ItemMediator;
import com.felipe.todolist.domain.items.ItemValidator;
import com.felipe.todolist.domain.lists.ListMediator;
import com.felipe.todolist.domain.model.Item;
import com.felipe.todolist.domain.persistence.ItemRepository;
import com.felipe.todolist.infraestructure.controllers.ItemController;
import com.felipe.todolist.infraestructure.model.items.ItemVO;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static io.restassured.RestAssured.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.webAppContextSetup;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ItemAcceptanceTest {

    @LocalServerPort
    private int port;

    @MockBean
    ItemRepository itemRepository;

    @MockBean
    ItemMediator itemMediator;

    @InjectMocks
    ItemController itemController;

    private Item item;
    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp() {
        openMocks(this);
        item = Item.builder().id(1L)
                .name("item 1").createdAt(LocalDateTime.now())
                .finished(false).build();

        webAppContextSetup(context);
        RestAssuredMockMvc.standaloneSetup(itemController);
    }

    @Test
    void shouldCreateAItemSuccesful() {
        Mockito.when(itemMediator.create(Mockito.any(Item.class), anyLong())).thenReturn(item);
        ItemVO itemVO = ItemVO.builder().id(1L)
                .name("item 1").createdAt(LocalDateTime.now())
                .finished(false).build();
        given().contentType(ContentType.JSON)
                .body(itemVO)
        .when()
                .post(String.format("http://localhost:%s/items?idList=1", port))
        .then()
                .statusCode(201)
                .body(containsString("item 1"));
    }

    @Test
    void shouldCreateAItemAndReturnStatusCode400() {
        doThrow(new IllegalArgumentException("Name is empty")).when(itemMediator).create(Mockito.any(Item.class), anyLong());
        ItemVO itemVO = ItemVO.builder().id(1L)
                .name(null).createdAt(LocalDateTime.now())
                .finished(false).build();
        given().contentType(ContentType.JSON)
                .body(itemVO)
                .when()
                .post(String.format("http://localhost:%s/items?idList=1", port))
                .then()
                .statusCode(400)
                .body(containsString("Name is empty"));
    }

    @Test
    void shouldFindAItemSuccesful() {
        Mockito.when(itemMediator.findById(anyLong())).thenReturn(item);
        given().contentType(ContentType.JSON)
        .when()
                .get(String.format("http://localhost:%s/items/1", port))
        .then()
                .statusCode(200)
                .body(containsString("item 1"));
    }

    @Test
    void shouldThrowNoSuchElementExceptionWhenFindByIdAndItemNotExists() {
        doThrow(new NoSuchElementException("Item not found")).when(itemMediator).findById(anyLong());

        given().contentType(ContentType.JSON)
                .when()
                .get(String.format("http://localhost:%s/items/1", port))
                .then()
                .statusCode(404)
                .body(containsString("Item not found"));
    }

    @Test
    void shouldUpdateItemSuccesful() {
        Mockito.when(itemMediator.update(Mockito.any(Item.class))).thenReturn(item);
        given().contentType(ContentType.JSON)
                .body(item)
        .when()
                .patch(String.format("http://localhost:%s/items/1", port))
        .then()
                .statusCode(200)
                .body(containsString("item 1"))
                .body(containsString("false"));
    }
}


