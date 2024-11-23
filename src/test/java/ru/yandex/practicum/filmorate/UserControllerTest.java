package ru.yandex.practicum.filmorate;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserControllerTest {
//    @Autowired
//    UserController userController;
//    @Autowired
//    UserService userService;
//
//    @AfterEach
//    public void cleanUp() {
//        userController.allUsers().clear();
//    }
//
//    @Test
//    void testGetAll() {
//        User user = User.builder()
//                .name("sdf")
//                .birthday(LocalDate.of(1999, 9, 4))
//                .email("gswgg@mail.ru")
//                .login("lgss")
//                .build();
//        userController.create(user);
//        User user1 = User.builder()
//                .name("sgb")
//                .birthday(LocalDate.of(1999, 10, 4))
//                .email("qlfm@mail.ru")
//                .login("ewgoiwoipgj")
//                .build();
//        userController.create(user1);
//        userController.allUsers();
//        assertEquals(userController.allUsers().size(), 2);
//
//    }
//
//    @Test
//    void testPostUser() {
//        User user = User.builder()
//                .name("sdf")
//                .birthday(LocalDate.of(1999, 9, 12))
//                .email("gswgg@mail.ru")
//                .login("lgss")
//                .build();
//        userController.create(user);
//        assertEquals("sdf", user.getName(), "Wrong name");
//        assertEquals(userController.allUsers().size(), 1);
//    }
//
//    @Test
//    void testPostUserFail() {
//        User user = User.builder()
//                .name("saop")
//                .birthday(LocalDate.of(2008, 12, 4))
//                .email("ibnrova")
//                .login("ffpp")
//                .build();
//        Exception exception = assertThrows(ValidationException.class, () -> userController.create(user));
//        assertTrue(exception.getMessage().contains("Некорректный email"));
//    }
//
//    @Test
//    void testPutUser() {
//        User oldUser = User.builder()
//                .name("tyi")
//                .birthday(LocalDate.of(1999, 9, 19))
//                .email("powv@mail.ru")
//                .login("mpwz")
//                .build();
//        userController.create(oldUser);
//        User user = User.builder()
//                .id(oldUser.getId())
//                .name("sghn")
//                .birthday(LocalDate.of(2001, 9, 5))
//                .email("gsPMs@mail.ru")
//                .login("newLogin")
//                .build();
//        userController.update(user);
//        assertEquals(oldUser.getLogin(), user.getLogin(), "user was not updated");
//    }
//
//    @Test
//    void testPutUserFail() {
//        User oldUser = User.builder()
//                .name("sghn")
//                .birthday(LocalDate.of(2001, 9, 5))
//                .email("gsPMs@mail.ru")
//                .login("sgsdg")
//                .build();
//        userController.create(oldUser);
//        User user = User.builder()
//                .name("sghn")
//                .birthday(LocalDate.of(2001, 9, 5))
//                .email("gsPMs@mail.ru")
//                .login("sgsdg")
//                .build();
//        Exception exception = assertThrows(ValidationException.class, () -> userController.update(user));
//        assertTrue(exception.getMessage().contains(" должен быть указан"));
//    }


}
