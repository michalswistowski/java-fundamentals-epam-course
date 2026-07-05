package com.epam.training.login.service;

import com.epam.training.login.data.UserStore;
import com.epam.training.login.domain.Address;
import com.epam.training.login.domain.LoginResult;
import com.epam.training.login.domain.User;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.assertArg;
import static org.mockito.Mockito.*;

class DefaultUserServiceTest {

    private UserService userService;
    private UserStore userStore;

    @BeforeEach
    void setup() {
        userStore = mock(UserStore.class);
        userService = new DefaultUserService(userStore);
    }

    @Test
    @DisplayName("should successfully login")
    void shouldSuccessfullyLogin() {

        // given
        User user = new User();
        user.setLoginName("Adam");
        user.setPassword("adam2");
        user.setAddress(new Address());
        user.setLocked(false);

        // when
        when(userStore.getUserByLoginName("Adam")).thenReturn(user);

        LoginResult result = userService.login("Adam", "adam2");
        LoginResult expected = LoginResult.SUCCESS;

        // then
        assertEquals(expected, result);
        verify(userStore, times(1)).getUserByLoginName(anyString());
    }

    @Test
    @DisplayName("Login should throw exception when user is locked")
    void loginShouldThrowExceptionWhenUserIsLocked() {

        // given
        User user = new User();
        user.setLoginName("Adam");
        user.setPassword("adam2");
        user.setAddress(new Address());
        user.setLocked(true);

        // when
        when(userStore.getUserByLoginName("Adam")).thenReturn(user);

        // then
        assertThrows(UserLockedException.class, () -> userService.login("Adam", "adam2"));
        verify(userStore, times(1)).getUserByLoginName(anyString());
    }

    @Test
    @DisplayName("Login should return not successful and update login counter")
    void loginShouldReturnNotSuccessfulAndUpdateLoginCounter() {

        // given
        User user = new User();
        user.setLoginName("Adam");
        user.setPassword("adam2");
        user.setAddress(new Address());
        user.setLocked(false);

        // when
        when(userStore.getUserByLoginName("Adam")).thenReturn(user);

        LoginResult result = userService.login("Adam", "adam3");
        LoginResult expected = LoginResult.UNSUCCESSFUL;

        // then
        assertEquals(expected, result);
        verify(userStore, times(1)).getFailedLoginCounter(anyString());
        verify(userStore, times(1)).updateFailedLoginCounter(anyString(), eq(1));
    }

    @Test
    @DisplayName("Login should throw exception after updating login counter")
    void loginShouldThrowExceptionAfterUpdatingLoginCounter() {

        // given
        User user = new User();
        user.setLoginName("Adam");
        user.setPassword("adam2");
        user.setAddress(new Address());
        user.setLocked(false);

        // when
        when(userStore.getUserByLoginName("Adam")).thenReturn(user);
        when(userStore.getFailedLoginCounter("Adam")).thenReturn(2);

        // then
        assertThrows(UserLockedException.class, () -> {
            userService.login("Adam", "adam3");
        });
        verify(userStore, times(1)).getFailedLoginCounter(anyString());
        verify(userStore, times(1)).updateFailedLoginCounter(anyString(), anyInt());
        verify(userStore, times(1)).updateFailedLoginCounter(anyString(), eq(3));

    }

    @Test
    @DisplayName("Login should return not successful login when user does not exist")
    void loginShouldReturnNotSuccessfulWhenUserDoesNotExist() {

        // given

        // when
        when(userStore.getUserByLoginName("Adam")).thenReturn(null);

        LoginResult result = userService.login("Adam", "adam2");
        LoginResult expected = LoginResult.UNSUCCESSFUL;

        // then
        assertEquals(expected, result);
        verify(userStore, times(1)).getUserByLoginName(anyString());
    }

    @Test
    @DisplayName("Should query address of user after successful login")
    void shouldQueryAddressOfUserAfterSuccessfulLogin() {

        // given
        User user = new User();
        Address address = new Address();

        address.setAddressLine("some street");
        address.setCity("Chicago");
        address.setCountry("USA");
        address.setName("smth");
        address.setZipCode("80-500");

        user.setLoginName("Adam");
        user.setPassword("adam2");
        user.setAddress(address);
        user.setLocked(false);

        // when
        when(userStore.getUserByLoginName("Adam")).thenReturn(user);

        LoginResult loginResult = userService.login("Adam", "adam2");
        LoginResult expectedLoginResult = LoginResult.SUCCESS;
        Address addressResult = userService.getLoggedInUserAddress();

        // then
        assertEquals(expectedLoginResult, loginResult);
        assertEquals(address, addressResult);

        verify(userStore, times(1)).updateFailedLoginCounter(anyString(), eq(0));
    }
}
