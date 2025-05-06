# 🔐 Bcrypt Password Hashing in Spring Boot

## Why Use Bcrypt Instead of MD5?

In older systems, algorithms like **MD5** were commonly used for password hashing. However, MD5 has several security flaws:

- **Hash functions are one-way** — once hashed, the original input cannot be retrieved.
- MD5 always produces the same output for the same input, making it vulnerable to rainbow table attacks.
- Adding a **salt** (a random string) to the password before hashing helps, but MD5 is still weak even with salt if both are exposed.

### ✅ Why Bcrypt Is Better

- Bcrypt produces **different outputs for the same input** using random salting.
- It is **computationally expensive**, which slows down brute-force attacks.
- Bcrypt is currently the standard for secure password hashing.
- MD5 is now mainly used for **file integrity checking (checksum)**, not password storage.

---

## ⚙️ Using Bcrypt in Spring Boot

### Step 1: Add Dependency

Add this to your `pom.xml`:

```xml
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-crypto</artifactId>
</dependency>
```

### Step 2: Hash the Password (During User Creation)

```java
PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10); // 10 = strength
user.setPassword(passwordEncoder.encode(request.getPassword()));
```

### Step 3: Authenticate Password (During Login)

```java
boolean authenticate(AuthenticationRequest request) {
    var user = userRepository.findByUsername(request.getUsername())
        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
    return passwordEncoder.matches(request.getPassword(), user.getPassword());
}
```

