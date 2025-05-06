# 🚀 Integrating MapStruct in Spring Boot  

## 📌 1️⃣ Install MapStruct  
Add the following dependency in `pom.xml`:  
```xml
<dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct</artifactId>
    <version>1.6.3</version>
</dependency>

<dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct-processor</artifactId>
    <version>1.6.3</version>
    <scope>provided</scope>
</dependency>
```

For Gradle (`build.gradle.kts`):  
```kotlin
dependencies {
    implementation("org.mapstruct:mapstruct:1.6.3")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")
}
```

---

## 📌 2️⃣ Create `mapper` Package  
Inside the **`mapper`** package, define a mapper interface using `@Mapper`:  

```java
@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
}
```

Now, instead of manually setting fields:  
```java
user.setUsername(request.getUsername());
user.setPassword(request.getPassword());
user.setFirstName(request.getFirstName());
user.setLastName(request.getLastName());
user.setDob(request.getDob());
```
You can simply use MapStruct:  
```java
User user = userMapper.toUser(request);
```

---

## 📌 3️⃣ Create `response` Package for Standardized Responses  
Define a `UserResponse` DTO to ensure consistent API responses:  

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String username;
    String firstName;
    String lastName;
    LocalDate dob;
}
```

---

## 📌 4️⃣ Update `UserMapper`  
Modify the `UserMapper` to map from `User` to `UserResponse`:  

```java
@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
    UserResponse toUserResponse(User user);
}
```

---

## 📌 5️⃣ Use `UserMapper` in `UserService`  
Instead of manually mapping, use `UserMapper` in the service layer:  

```java
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponse createUser(UserCreationRequest request) {
        User user = userMapper.toUser(request);
        return userMapper.toUserResponse(userRepository.save(user));
    }
}
```

---

## 📌 6️⃣ Use `UserResponse` in `UserController`

---

## 📌 7️⃣ Additional Best Practices

### ✅ Use `@Mapping` for Custom Mappings
When specific fields need transformation, use `@Mapping`:
```java
@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "sourceField", target = "targetField")
    UserResponse toUserResponse(User user);
}
```

### ✅ Avoid `@Autowired` for Dependency Injection
Instead of `@Autowired`, use constructor-based injection with `@RequiredArgsConstructor`:
```java
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
}
```

