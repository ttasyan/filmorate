package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;


@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmorateApplicationTests {
//	@Autowired
//	private JdbcTemplate jdbcTemplate;
//	private  UserRepository userRepository;
//
//	@BeforeEach
//	public void setUp() {
//		RowMapper<User> userRowMapper = new BeanPropertyRowMapper<>(User .class);
//		userRepository = new UserRepository(jdbcTemplate, userRowMapper);
//
//		jdbcTemplate.execute("CREATE TABLE users (id SERIAL PRIMARY KEY, login VARCHAR(255), name VARCHAR(255), email VARCHAR(255), birthday DATE)");
//		jdbcTemplate.execute("INSERT INTO users (login, name, email, birthday) VALUES ('testLogin', 'Test User', 'test@example.com', '2000-01-01')");
//		jdbcTemplate.execute("INSERT INTO users (login, name, email, birthday) VALUES ('friendLogin', 'Friend User', 'friend@example.com', '2000-01-02')");
//	}
//
//	@Test
//	public void testFindUserById() {
//
//		User userOptional = userRepository.findUserById(1);
//
//		ObjectAssert<User> id = assertThat(userOptional);
//
//                             assertThat(userOptional).hasFieldOrPropertyWithValue("id", 1);
//
//				;
//	}
//	@Test
//	public void testAllUsers() {
//		Collection<User> users = userRepository.allUsers();
//
//		assertThat(users).hasSize(2);
//	}
//
//	@Test
//	public void testCreate() {
//		User user =  User.builder()
//		.login("newUser ")
//		.name("New User")
//		.email("new@example.com")
//		.birthday(LocalDate.parse("2000-01-03"))
//				.build();
//
//		User createdUser  = userRepository.create(user);
//
//		assertThat(createdUser ).isNotNull();
//		assertThat(createdUser .getId()).isNotNull();
//
//		User foundUser  = userRepository.findUserById(createdUser .getId());
//
//						assertThat(foundUser).hasFieldOrPropertyWithValue("login", "newUser ")
//								.hasFieldOrPropertyWithValue("name", "New User")
//								.hasFieldOrPropertyWithValue("email", "new@example.com")
//								.hasFieldOrPropertyWithValue("birthday", "2000-01-03")
//				;
//	}
//
//	@Test
//	public void testUpdate() {
//		User user = User.builder()
//		.id(1)
//		.login("updatedLogin")
//		.name("Updated User")
//		.email("updated@example.com")
//		.birthday(LocalDate.parse("2000-01-04"))
//				.build();
//
//		userRepository.update(user);
//
//		User updatedUser  = userRepository.findUserById(1);
//
//						assertThat(updatedUser).hasFieldOrPropertyWithValue("login", "updatedLogin")
//								.hasFieldOrPropertyWithValue("name", "Updated User")
//								.hasFieldOrPropertyWithValue("email", "updated@example.com")
//								.hasFieldOrPropertyWithValue("birthday", "2000-01-04")
//				;
//	}
}

