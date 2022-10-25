package com.felipe.todolist.domain.lists;

import com.felipe.todolist.domain.model.ToDoList;
import com.felipe.todolist.domain.persistence.ListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class ListMediatorDefaultTest {


    @Nested
    class testForCreateMethod{
        @Mock
        private ListRepository repository;

        private ListMediatorDefault mediator;

        private ToDoList toDoListIn;

        @BeforeEach
        void setUp() {
            openMocks(this);
            toDoListIn = ToDoList.builder()
                    .name("Felipe")
                    .description("Cosas por hacer antes del 31 de octubre")
                    .user("felipe@gmail.com").build();
            mediator = new ListMediatorDefault(repository, new ListValidator());
            ToDoList toDoListOut = ToDoList.builder()
                    .id(100L)
                    .name("Cosas por hacer")
                    .description("Cosas por hacer antes del 31 de octubre")
                    .user("felipe@gmail.com").build();

            when(repository.save(any(ToDoList.class))).thenReturn(toDoListOut);
        }
        @Test
        void shouldCreateAnListSuccesful(){
            //Act
            ToDoList listCreated = mediator.create(toDoListIn);
            //Assert
            assertEquals(100, listCreated.getId());
            verify(repository).save(any(ToDoList.class));
        }

        @Test
        void shouldThrowIllegalArgumentExceptionWhenNameIsNull(){
            toDoListIn.setName(null);
            ListMediator mediator = new ListMediatorDefault(repository, new ListValidator());

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                            mediator.create(toDoListIn),
                    "Se esperaba un IllegalArgumentException cuando el campo name es nulo pero no fue lanzada");
            assertTrue(exception.getMessage().contains("Name is empty"));
            verify(repository, times(0)).save(any(ToDoList.class));
        }

        @Test
        void shouldThrowIllegalArgumentExceptionWhenNameIsEmpty(){
            toDoListIn.setName("");
            ListMediator mediator = new ListMediatorDefault(repository, new ListValidator());

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                            mediator.create(toDoListIn),
                    "Se esperaba un IllegalArgumentException cuando el campo name es nulo pero no fue lanzada");
            assertTrue(exception.getMessage().contains("Name is empty"));
            verify(repository, times(0)).save(any(ToDoList.class));
        }

        @Test
        void shouldThrowIllegalArgumentExceptionWhenUserIsNull(){
            toDoListIn.setUser(null);
            ListMediator mediator = new ListMediatorDefault(repository, new ListValidator());

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                            mediator.create(toDoListIn),
                    "Se esperaba un IllegalArgumentException cuando el campo name es nulo pero no fue lanzada");
            assertTrue(exception.getMessage().contains("User is empty"));
            verify(repository, times(0)).save(any(ToDoList.class));
        }

        @Test
        void shouldThrowIllegalArgumentExceptionWhenUserIsEmpty(){
            toDoListIn.setUser("");
            ListMediator mediator = new ListMediatorDefault(repository, new ListValidator());

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                            mediator.create(toDoListIn),
                    "Se esperaba un IllegalArgumentException cuando el campo name es nulo pero no fue lanzada");
            assertTrue(exception.getMessage().contains("User is empty"));
            verify(repository, times(0)).save(any(ToDoList.class));
        }

        @Test
        void shouldThrowIllegalArgumentExceptionWhenUserFormatIsIncorrect(){
            toDoListIn.setUser("felipecris");
            ListMediator mediator = new ListMediatorDefault(repository, new ListValidator());

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                            mediator.create(toDoListIn),
                    "Se esperaba un IllegalArgumentException cuando el campo name es nulo pero no fue lanzada");
            assertTrue(exception.getMessage().contains("The user does not have the email format"));
            verify(repository, times(0)).save(any(ToDoList.class));
        }
    }

    @Nested
    class TestsForFindMethod{
        @Mock
        private ListRepository repository;

        @Mock
        private ListValidator validator;

        @InjectMocks
        private ListMediatorDefault mediator;

        private ToDoList toDoListOut;

        @BeforeEach
        void setUp() {
            openMocks(this);
            toDoListOut = ToDoList.builder()
                    .id(100L)
                    .name("Cosas por hacer")
                    .description("Cosas por hacer antes del 31 de octubre")
                    .user("felipe@gmail.com").build();
        }

        @Test
        void shouldFindAListSuccesful() {
            when(repository.existsById(100L)).thenReturn(true);
            when(repository.findById(100L)).thenReturn(toDoListOut);
            ToDoList toDoList = mediator.findById(100L);
            assertEquals(100L, toDoList.getId());
            verify(repository).findById(anyLong());
            verify(repository).existsById(anyLong());
        }

        @Test
        void shouldThrowNoSuchElementExceptionWhenToDoListNotExists() {
            when(repository.existsById(anyLong())).thenReturn(false);

            NoSuchElementException exception = assertThrows(NoSuchElementException.class, () ->
                mediator.findById(100L),
                "Se esperaba un NoSuchElementException cuando no existe el ToDoList especificado por su ID pero no fue lanzada");

            assertEquals("Element not found",exception.getMessage());
            assertTrue(exception.getMessage().contains("Element not found"));
            verify(repository, times(0)).findById(anyLong());
            verify(repository).existsById(anyLong());
        }
    }

    @Nested
    class TestForUpdateMethod{
        @Mock
        private ListRepository repository;

        private ListMediatorDefault mediator;

        private ToDoList toDoListIn;

        private ToDoList toDoListOut;

        @BeforeEach
        void setUp() {
            openMocks(this);
            toDoListIn = ToDoList.builder()
                    .id(100L)
                    .name("Cosas por hacer")
                    .description("Cosas por hacer antes del 31 de octubre").build();
            mediator = new ListMediatorDefault(repository, new ListValidator());
            toDoListOut = ToDoList.builder()
                    .id(100L)
                    .name("Cosas por hacer")
                    .description("Cosas por hacer antes del 31 de octubre")
                    .user("felipe@gmail.com").build();

            when(repository.save(any(ToDoList.class))).thenReturn(toDoListOut);
        }

        @Test
        void shouldUpdateAListSuccesful() {
            when(repository.existsById(100L)).thenReturn(true);
            when(repository.findById(100L)).thenReturn(toDoListOut);
            when(repository.update(any(ToDoList.class))).thenReturn(toDoListOut);

            ToDoList listUpdated = mediator.update(toDoListIn);

            assertEquals("Cosas por hacer", listUpdated.getName());
            verify(repository).existsById(anyLong());
            verify(repository).findById(anyLong());
            verify(repository).update(any(ToDoList.class));
        }

        @Test
        void shouldThrowIllegalArgumentExceptionWhenNameIsNull(){
            toDoListIn.setName(null);

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                            mediator.update(toDoListIn),
                    "Se esperaba un IllegalArgumentException cuando el campo name es nulo pero no fue lanzada");
            assertTrue(exception.getMessage().contains("Name is empty"));
            verify(repository, times(0)).save(any(ToDoList.class));
        }

        @Test
        void shouldThrowIllegalArgumentExceptionWhenNameIsEmpty(){
            toDoListIn.setName("");

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                            mediator.update(toDoListIn),
                    "Se esperaba un IllegalArgumentException cuando el campo name es nulo pero no fue lanzada");
            assertTrue(exception.getMessage().contains("Name is empty"));
            verify(repository, times(0)).save(any(ToDoList.class));
        }

        @Test
        void shouldThrowNoSuchElementExceptionWhenToDoListNotExists() {
            when(repository.existsById(anyLong())).thenReturn(false);

            NoSuchElementException exception = assertThrows(NoSuchElementException.class, () ->
                            mediator.update(toDoListIn),
                    "Se esperaba un NoSuchElementException cuando no existe el ToDoList especificado por su ID pero no fue lanzada");

            assertEquals("Element not found",exception.getMessage());
            assertTrue(exception.getMessage().contains("Element not found"));
            verify(repository, times(0)).findById(anyLong());
            verify(repository).existsById(anyLong());
        }
    }
}
