package com.felipe.todolist.domain.items;

import com.felipe.todolist.domain.lists.ListMediatorDefault;
import com.felipe.todolist.domain.model.Item;
import com.felipe.todolist.domain.persistence.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class ItemMediatorTest {

    @Nested
    class testsForCreateMethod{

        @Mock
        private ItemRepository itemRepository;
        @Mock
        private ListMediatorDefault listMediatorDefault;

        private ItemMediatorDefault itemMediator;

        private Item item;

        @BeforeEach
        void setUp() {
            openMocks(this);
            itemMediator = new ItemMediatorDefault(itemRepository, new ItemValidator(), listMediatorDefault);
            item = Item.builder().id(1L).createdAt(LocalDateTime.now())
                    .finished(false).name("item 1").build();
        }

        @Test
        void shouldCreateAnItemSuccesfull() {
            doNothing().when(listMediatorDefault).validateExistsListById(anyLong());
            when(itemRepository.save(any(Item.class), anyLong())).thenReturn(item);

            Item result = itemMediator.create(item, 1L);

            assertEquals(1L, result.getId());
            verify(itemRepository).save(any(Item.class), anyLong());
        }

        @ParameterizedTest
        @ValueSource(strings = {""})
        @NullSource
        void shouldThrowIllegalArgumentExceptionWhenNameIsEmptyOrNull(String arg) {
            item.setName(arg);

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                            itemMediator.create(item, 1L),
                    "Se esperaba un IllegalArgumentException cuando el campo name es nulo pero no fue lanzada");

            assertTrue(exception.getMessage().contains("Name is empty"));
            verify(itemRepository, times(0)).save(item, 1L);
        }

        @Test
        void shouldThrowNoSuchElementExceptionWhenToDoListNotExists() {
            doThrow(new NoSuchElementException("List not found")).when(listMediatorDefault).validateExistsListById(1L);

            NoSuchElementException exception = assertThrows(NoSuchElementException.class, () ->
                            itemMediator.create(item, 1L),
                    "Se esperaba un NoSuchElementException cuando la lista a la que pertenece el item no existe");

            assertTrue(exception.getMessage().contains("List not found"));
            verify(itemRepository, times(0)).save(item, 1L);
            verify(listMediatorDefault).validateExistsListById(1L);
        }
    }

    @Nested
    class testsForFindMethod{
        @Mock
        private ItemRepository itemRepository;
        @Mock
        private ListMediatorDefault listMediatorDefault;

        private ItemMediatorDefault itemMediator;

        private Item item;

        @BeforeEach
        void setUp() {
            openMocks(this);
            itemMediator = new ItemMediatorDefault(itemRepository, new ItemValidator(), listMediatorDefault);
            item = Item.builder().id(1L).createdAt(LocalDateTime.now())
                    .finished(false).name("item 1").build();
        }

        @Test
        void shouldFindItemSuccesfull() {
            when(itemRepository.existsById(anyLong())).thenReturn(true);
            when(itemRepository.findById(anyLong())).thenReturn(item);
            Item result = itemMediator.findById(anyLong());

            assertEquals(1L, result.getId());
            verify(itemRepository).findById(anyLong());
            verify(itemRepository).existsById(anyLong());
        }

        @Test
        void shouldThrowNoSuchElementExceptionWhenToDoListNotExists() {
            when(itemRepository.existsById(anyLong())).thenReturn(false);

            NoSuchElementException exception = assertThrows(NoSuchElementException.class, () ->
                            itemMediator.findById(anyLong()),
                    "Se esperaba un NoSuchElementException cuando no existe el ToDoList especificado por su ID pero no fue lanzada");

            assertEquals("Item not found",exception.getMessage());
            assertTrue(exception.getMessage().contains("Item not found"));
            verify(itemRepository, times(0)).findById(anyLong());
            verify(itemRepository).existsById(anyLong());
        }
    }
}
