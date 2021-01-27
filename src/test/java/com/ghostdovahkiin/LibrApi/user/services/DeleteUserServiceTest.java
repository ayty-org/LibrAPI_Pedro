package com.ghostdovahkiin.LibrApi.user.services;

import com.ghostdovahkiin.LibrApi.exceptions.UserNotFoundException;
import com.ghostdovahkiin.LibrApi.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.ghostdovahkiin.LibrApi.user.services.builders.UserBuilder.createUser;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests execution for Delete User Service")
public class DeleteUserServiceTest {
    @Mock
    private UserRepository userRepository;
    private DeleteUserServiceImpl deleteUserService;

    @BeforeEach
    void setUp() {
        this.deleteUserService = new DeleteUserServiceImpl(userRepository);
    }

    @Test
    @DisplayName("Should delete a User")
    void shouldDeleteUser() {
        when(userRepository.existsById(anyLong())).thenReturn(true);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(createUser().build()));
        deleteUserService.delete(1L);
        verify(userRepository).existsById(anyLong());
    }

    @Test
    @DisplayName("Shold return a UserNotFoundException if not encountered a user with specified ID")
    void shouldThrowUserNotFoundException() {
        when(userRepository.existsById(anyLong())).thenReturn(false);
        assertThrows(UserNotFoundException.class, () -> deleteUserService.delete(1L));
        verify(userRepository, times(0)).deleteById(anyLong());
    }


}