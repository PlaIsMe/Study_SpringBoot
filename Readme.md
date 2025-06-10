# Implement Unit Test

## Dependency

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

## 🧱 Key Annotations
```
|-------------------------|-------------------------------------------------------------------------|
| Annotation              | Description                                                             |
|-------------------------|-------------------------------------------------------------------------|
| `@SpringBootTest`       | Boots up the full Spring context for integration testing.               |
| `@AutoConfigureMockMvc` | Enables `MockMvc` to simulate HTTP requests without running a server.   |
|-------------------------|-------------------------------------------------------------------------|
```

## 🧪 Mocking with `@MockBean`

```java
@MockBean
private UserService userService;
```

- `@MockBean` tells Spring to replace the real `UserService` bean with a mock.
- You can then use `Mockito.when(...)` to define what the mock should return.

---

## 🛠 Test Data Initialization

```java
@BeforeEach
void initData() {
    // Set up request and response objects
}
```

- Runs **before each test**.
- Prepares the input (`UserCreationRequest`) and expected output (`UserResponse`).

---

## ✅ Test 1: Valid User Creation

```java
@Test
void createUser_validRequest_success() throws Exception {
    Mockito.when(userService.createUser(ArgumentMatchers.any()))
           .thenReturn(userResponse);

    mockMvc.perform(post("/users")...)
           .andExpect(status().isOk())
           .andExpect(jsonPath("code").value(1000))
           .andExpect(jsonPath("result.id").value("cf0600f538b3"));
}
```

- Uses `MockMvc` to simulate a POST request to `/users`.
- Mocks the service layer using `Mockito.when(...)`.
- Asserts that the API responds with status `200 OK` and expected JSON.

---

## ❌ Test 2: Invalid Username

```java
@Test
void createUser_usernameInvalid_fail() throws Exception {
    request.setUsername("pla"); // Invalid username: too short

    mockMvc.perform(post("/users")...)
           .andExpect(status().isBadRequest())
           .andExpect(jsonPath("code").value(1003))
           .andExpect(jsonPath("message").value("Username must be at least 4 characters"));
}
```

- Sends a request with invalid input (`username` too short).
- Expects an HTTP `400 Bad Request` with a custom error message.

---

## 🤖 What is Mocking in this Context?

> Mocking means simulating the behavior of `UserService` so that the controller can be tested independently of its logic.

```java
Mockito.when(userService.createUser(...)).thenReturn(userResponse);
```

- This tells the test:  
  When the controller calls `userService.createUser(...)`, just return the fake `userResponse` object.

### ✅ Test 3: `createUser_validRequest_success`

```java
@Test
void createUser_validRequest_success() {
    when(userRepository.existsByUsername(anyString())).thenReturn(false);
    when(userRepository.save(any())).thenReturn(user);

    var response = userService.createUser(request);

    Assertions.assertThat(response.getId()).isEqualTo("cf0600f538b3");
    Assertions.assertThat(response.getUsername()).isEqualTo("thuien");
}
```

- **Mocks repository**:
  - `existsByUsername(...)` returns `false` → username does not exist.
  - `save(...)` returns the predefined `user`.
- **Calls service** method and validates:
  - `response.getId()` must match `"cf0600f538b3"`.
  - `response.getUsername()` must match `"thuien"`.

---

### ❌ Test 4: `createUser_userExisted_fail`

```java
@Test
void createUser_userExisted_fail() {
    when(userRepository.existsByUsername(anyString())).thenReturn(true);

    var exception = assertThrows(AppException.class,
        () -> userService.createUser(request));

    Assertions.assertThat(exception.getErrorCode().getCode())
        .isEqualTo(1002);
}
```

- Simulates case where username already exists.
- Expects `AppException` to be thrown.
- Asserts that the returned error code is `1002`.

---

### 🤖 Key Concepts

- **Mocking**: Use `@MockBean` and `when(...).thenReturn(...)` to control the behavior of dependencies.
- **Service Testing**: Isolate service logic by mocking database interactions.
- **Validation**: Use AssertJ for fluent assertions.

---

### 🔍 Common Issues

- ❗ Forgot to set `request` object in `initData()`.
- ❗ Expected username `"john"` in test, but `user` is mocked with `"thuien"` → inconsistency!

Make sure the test data (`request`, `user`) is consistent with expected results.


## 🧪 Isolation in Unit Test

To ensure your unit tests are isolated from production systems, use an **in-memory H2 database** instead of connecting to a real production database.

---

### 1. Add H2 Dependency

```xml
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <version>2.2.224</version>
    <scope>test</scope>
</dependency>
```

---

### 2. Create `test.properties` (inside `src/test/resources`)

```properties
spring.datasource.url=jdbc:h2:mem:testdb;MODE=MYSQL
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=sa
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=none
```

---

### 3. Use the Test Properties File

Add the following annotation in your test class:

```java
@TestPropertySource("/test.properties")
```

---

### 4. Modify `ApplicationInitConfig` (for conditional bean loading)

If your config class uses a MySQL-specific bean, you can guard it with a condition like this:

```java
@Bean
@ConditionalOnProperty(
    prefix = "spring",
    value = "datasource.driverClassName",
    havingValue = "com.mysql.cj.jdbc.Driver"
)
public YourBean yourBean() {
    // Bean initialization here
}
```

This ensures the bean is only loaded when you're running with the MySQL driver, not during tests using H2.

---
