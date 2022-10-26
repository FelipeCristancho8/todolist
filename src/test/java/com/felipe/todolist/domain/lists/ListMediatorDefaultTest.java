package com.felipe.todolist.domain.lists;

import com.felipe.todolist.domain.model.Item;
import com.felipe.todolist.domain.model.ToDoList;
import com.felipe.todolist.domain.persistence.ItemRepository;
import com.felipe.todolist.domain.persistence.ListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class ListMediatorDefaultTest {


    @Nested
    class testForCreateMethod{
        @Mock
        private ListRepository listRepository;

        @Mock
        private ItemRepository itemRepository;

        private ListMediatorDefault mediator;

        private ToDoList toDoListIn;

        @BeforeEach
        void setUp() {
            openMocks(this);
            toDoListIn = ToDoList.builder()
                    .name("Felipe")
                    .description("Cosas por hacer antes del 31 de octubre")
                    .user("felipe@gmail.com").build();
            mediator = new ListMediatorDefault(listRepository, itemRepository, new ListValidator());
            ToDoList toDoListOut = ToDoList.builder()
                    .id(100L)
                    .name("Cosas por hacer")
                    .description("Cosas por hacer antes del 31 de octubre")
                    .user("felipe@gmail.com").build();

            when(listRepository.save(any(ToDoList.class))).thenReturn(toDoListOut);
        }
        @Test
        void shouldCreateAnListSuccesful(){
            //Act
            ToDoList listCreated = mediator.create(toDoListIn);
            //Assert
            assertEquals(100, listCreated.getId());
            verify(listRepository).save(any(ToDoList.class));
        }

        @ParameterizedTest
        @NullSource
        @ValueSource(strings = {""})
        void shouldThrowIllegalArgumentExceptionWhenNameIsNull(String arg){
            toDoListIn.setName(arg);
            ListMediator mediator = new ListMediatorDefault(listRepository, itemRepository, new ListValidator());

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                            mediator.create(toDoListIn),
                    "Se esperaba un IllegalArgumentException cuando el campo name es nulo pero no fue lanzada");
            assertTrue(exception.getMessage().contains("Name is empty"));
            verify(listRepository, times(0)).save(any(ToDoList.class));
        }

        @ParameterizedTest
        @NullSource
        @ValueSource(strings = {""})
        void shouldThrowIllegalArgumentExceptionWhenUserIsNull(String arg){
            toDoListIn.setUser(arg);
            ListMediator mediator = new ListMediatorDefault(listRepository, itemRepository, new ListValidator());

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                            mediator.create(toDoListIn),
                    "Se esperaba un IllegalArgumentException cuando el campo name es nulo pero no fue lanzada");
            assertTrue(exception.getMessage().contains("User is empty"));
            verify(listRepository, times(0)).save(any(ToDoList.class));
        }

        @Test
        void shouldThrowIllegalArgumentExceptionWhenUserFormatIsIncorrect(){
            toDoListIn.setUser("felipecris");
            ListMediator mediator = new ListMediatorDefault(listRepository, itemRepository, new ListValidator());

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                            mediator.create(toDoListIn),
                    "Se esperaba un IllegalArgumentException cuando el campo name es nulo pero no fue lanzada");
            assertTrue(exception.getMessage().contains("The user does not have the email format"));
            verify(listRepository, times(0)).save(any(ToDoList.class));
        }
    }

    @Nested
    class TestsForFindMethod{
        @Mock
        private ListRepository listRepository;

        @Mock
        private ItemRepository itemRepository;

        @Mock
        private ListValidator validator;

        @InjectMocks
        private ListMediatorDefault mediator;

        private ToDoList toDoListOut;

        private List<Item> items;

        @BeforeEach
        void setUp() {
            openMocks(this);
            toDoListOut = ToDoList.builder()
                    .id(100L)
                    .name("Cosas por hacer")
                    .description("Cosas por hacer antes del 31 de octubre")
                    .user("felipe@gmail.com").build();
            Item item = Item.builder().id(100L).description("descripcion")
                    .createdAt(LocalDateTime.now()).finished(false).build();
            items = Arrays.asList(item);
        }

        @Test
        void shouldFindAListSuccesful() {
            when(listRepository.existsById(100L)).thenReturn(true);
            when(listRepository.findById(100L)).thenReturn(toDoListOut);
            when(itemRepository.findItemsByToDoListId(anyLong())).thenReturn(items);
            ToDoList toDoList = mediator.findById(100L);
            assertEquals(100L, toDoList.getId());
            verify(listRepository).findById(anyLong());
            verify(listRepository).existsById(anyLong());
        }

        @Test
        void shouldThrowNoSuchElementExceptionWhenToDoListNotExists() {
            when(listRepository.existsById(anyLong())).thenReturn(false);

            NoSuchElementException exception = assertThrows(NoSuchElementException.class, () ->
                mediator.findById(100L),
                "Se esperaba un NoSuchElementException cuando no existe el ToDoList especificado por su ID pero no fue lanzada");

            assertEquals("Element not found",exception.getMessage());
            assertTrue(exception.getMessage().contains("Element not found"));
            verify(listRepository, times(0)).findById(anyLong());
            verify(listRepository).existsById(anyLong());
        }
    }

    @Nested
    class TestForUpdateMethod{
        @Mock
        private ListRepository repository;

        @Mock
        private ItemRepository itemRepository;

        private ListMediatorDefault mediator;

        private ToDoList toDoListIn;

        private ToDoList toDoListOut;

        private List<Item> items;

        @BeforeEach
        void setUp() {
            openMocks(this);
            toDoListIn = ToDoList.builder()
                    .id(100L)
                    .name("Cosas por hacer")
                    .description("Cosas por hacer antes del 31 de octubre").build();
            mediator = new ListMediatorDefault(repository, itemRepository, new ListValidator());
            toDoListOut = ToDoList.builder()
                    .id(100L)
                    .name("Cosas por hacer")
                    .description("Cosas por hacer antes del 31 de octubre")
                    .user("felipe@gmail.com").build();

            Item item = Item.builder().id(100L).description("descripcion")
                    .createdAt(LocalDateTime.now()).finished(false).build();
            items = Arrays.asList(item);
            when(repository.save(any(ToDoList.class))).thenReturn(toDoListOut);
        }

        @Test
        void shouldUpdateAListSuccesful() {
            when(repository.existsById(100L)).thenReturn(true);
            when(repository.findById(100L)).thenReturn(toDoListOut);
            when(repository.update(any(ToDoList.class))).thenReturn(toDoListOut);
            when(itemRepository.findItemsByToDoListId(100L)).thenReturn(items);

            ToDoList listUpdated = mediator.update(toDoListIn);

            assertEquals("Cosas por hacer", listUpdated.getName());
            verify(repository).existsById(anyLong());
            verify(repository).findById(anyLong());
            verify(repository).update(any(ToDoList.class));
        }

        @ParameterizedTest
        @NullSource
        @ValueSource(strings = {""})
        void shouldThrowIllegalArgumentExceptionWhenNameIsNullOrEmpty(String arg){
            toDoListIn.setName(arg);

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

    @Nested
    class TestForDeleteMethod{

        @Mock
        private ListRepository listRepository;

        @InjectMocks
        private ListMediatorDefault mediator;

        @BeforeEach
        void setUp() {
            openMocks(this);
        }

        @Test
        void shouldUpdateAListSuccesful() {
            when(listRepository.existsById(anyLong())).thenReturn(true);
            doNothing().when(listRepository).delete(anyLong());
            mediator.delete(anyLong());

            verify(listRepository).delete(anyLong());
        }

        @Test
        void shouldThrowNoSuchElementExceptionWhenToDoListNotExists() {
            when(listRepository.existsById(anyLong())).thenReturn(false);

            NoSuchElementException exception = assertThrows(NoSuchElementException.class, () ->
                            mediator.delete(anyLong()),
                    "Se esperaba un NoSuchElementException cuando no existe el ToDoList especificado por su ID pero no fue lanzada");

            assertEquals("Element not found",exception.getMessage());
            assertTrue(exception.getMessage().contains("Element not found"));
            verify(listRepository, times(0)).delete(anyLong());
        }
    }
}
