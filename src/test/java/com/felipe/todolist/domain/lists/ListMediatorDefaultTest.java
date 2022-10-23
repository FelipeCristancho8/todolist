package com.felipe.todolist.domain.lists;

import com.felipe.todolist.domain.model.ToDoList;
import com.felipe.todolist.domain.persistence.ListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class ListMediatorDefaultTest {

    @Mock
    private ListRepository repository;

    private ListMediatorDefault mediator;

    private ToDoList toDoListIn;

    @BeforeEach
    void setUp() {
        openMocks(this);
        toDoListIn = ToDoList.builder()
                .name(null)
                .description("Cosas por hacer antes del 31 de octubre")
                .user("felipe@gmail.com").build();
        mediator = new ListMediatorDefault(repository);
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
        ListMediator mediator = new ListMediatorDefault(repository);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            mediator.create(toDoListIn),
            "Se esperaba un IllegalArgumentException cuando el campo name es nulo pero no fue lanzada");
        assertTrue(exception.getMessage().contains("Name is empty"));
        verify(repository, times(0)).save(any(ToDoList.class));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenNameIsEmpty(){
        toDoListIn.setName("");
        ListMediator mediator = new ListMediatorDefault(repository);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                        mediator.create(toDoListIn),
                "Se esperaba un IllegalArgumentException cuando el campo name es nulo pero no fue lanzada");
        assertTrue(exception.getMessage().contains("Name is empty"));
        verify(repository, times(0)).save(any(ToDoList.class));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenUserIsNull(){
        toDoListIn.setUser(null);
        ListMediator mediator = new ListMediatorDefault(repository);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                        mediator.create(toDoListIn),
                "Se esperaba un IllegalArgumentException cuando el campo name es nulo pero no fue lanzada");
        assertTrue(exception.getMessage().contains("User is empty"));
        verify(repository, times(0)).save(any(ToDoList.class));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenUserIsEmpty(){
        toDoListIn.setUser("");
        ListMediator mediator = new ListMediatorDefault(repository);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                        mediator.create(toDoListIn),
                "Se esperaba un IllegalArgumentException cuando el campo name es nulo pero no fue lanzada");
        assertTrue(exception.getMessage().contains("User is empty"));
        verify(repository, times(0)).save(any(ToDoList.class));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenUserFormatIsIncorrect(){
        toDoListIn.setUser("felipecris");
        ListMediator mediator = new ListMediatorDefault(repository);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                        mediator.create(toDoListIn),
                "Se esperaba un IllegalArgumentException cuando el campo name es nulo pero no fue lanzada");
        assertTrue(exception.getMessage().contains("The user does not have the email format"));
        verify(repository, times(0)).save(any(ToDoList.class));
    }
}
