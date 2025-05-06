# Authentication with JWT in Spring Boot

## Spring Security Request Flow Overview
```
+-------------------+
|      Client       |
+-------------------+
         ↑
         |                           Spring Security Filters
         ↓                         --------------------------
+-------------------+             |     ---------------      |
|      Filter       |             |    |               |     |
+-------------------+    -------------→|     Filter    |     |
         ↑              |         |    |               |     |
         |              |         |     ---------------      |
         |              |         |            ↑             |
         ↓              |         |            ↓             |
+-------------------+   |         |     ---------------      |
|  FilterChainProxy |←--|         |    |               |     |
+-------------------+   |         |    |     Filter    |     |
         ↑              |         |    |               |     |
         |              |         |     ---------------      |
         |              |         |            ↑             |
         ↓              |         |            ↓             |
+-------------------+   |         |     ---------------      |
|      Filter       |   |         |    |               |     |
+-------------------+    -------------→|     Filter    |     |
         ↑                        |    |               |     |
         |                        |     ---------------      |
         ↓                         ---------------------------
+-------------------+
|      Servlet      |
+-------------------+
         ↑
         |
         ↓
+-------------------+
|    Controler      |
|     Service       |
|    Repository     |
+-------------------+
```

### 🌐 Request Lifecycle in Spring Security

1. The **client sends a request** to a protected API endpoint.

2. The request **passes through a series of filters**, collectively known as the **Spring Security Filter Chain**.

3. These filters are responsible for various tasks such as:
   - Authentication (e.g., checking JWT, session ID, username/password).
   - Authorization (checking user roles/permissions).
   - CORS, CSRF, and other security checks.

4. Each filter in the chain has the ability to either:
   - **Allow the request to proceed** to the next filter or the application, or
   - **Deny the request**, immediately returning a response to the client (e.g., 401 Unauthorized or 403 Forbidden).

5. If the request passes all security filters, it proceeds to:
   - **Controller → Service → Repository** layers of the application.

### 🔍 Filter Abstraction

- While the Spring Security filter chain contains **many internal filters** (like `AuthenticationFilter`, `AuthorizationFilter`, etc.), 
- The **Servlet container** (e.g., Tomcat) only sees **one unified `Filter`** — the **Spring Security Filter Chain**.
- Internally, this filter chain delegates to its composed filters in order.

---

✅ This architecture aligns with Spring Security’s core design:
- A single `FilterChainProxy` acts as the servlet-level filter.
- It routes the request through multiple security filters.
- These filters handle login, token parsing, session management, role-based access control, etc.


## Spring Security Filters Flow
![Spring Security Filters Flow](<images/Spring Security Filters Flow.png>)

## 🔐 Spring Security Filter Flow (Token-Based Authentication)

### 1️⃣ User Sends Request
The client (Actor) sends an HTTP request with a token (usually in the header like `Authorization: Bearer xyz...` or a session cookie).

### 2️⃣ Security Filter Chain
- The request enters the **Security Filter Chain**.
- This chain consists of various filters (like `BasicAuthenticationFilter`, `JwtAuthenticationFilter`, `UsernamePasswordAuthenticationFilter`, etc.).
- The appropriate **Authentication Filter** is chosen based on the type of token.

### 3️⃣ Filter Creates Authentication Token
- The filter extracts the token and builds an `Authentication` object, e.g., `UsernamePasswordAuthenticationToken` or `BearerTokenAuthenticationToken`.
- This object is passed to the `AuthenticationManager`.

### 4️⃣ Authentication Manager Delegates
- `AuthenticationManager` receives the token and delegates it to a suitable `AuthenticationProvider` (based on token type).

### 5️⃣ ❗(Only for username/password login) PasswordEncoder is used
- This step **only applies if the token contains raw credentials** (e.g., login form).
- For JWT or session tokens, this step is usually **skipped**.
- If it’s a login attempt, then yes, the password is hashed with `PasswordEncoder` and compared.

### 6️⃣ Authentication Provider Calls UserDetailsService
- The provider extracts a username (or user ID) from the token and calls `UserDetailsService.loadUserByUsername`.

### 7️⃣ UserDetailsService Returns UserDetails
- `UserDetailsService` loads a `UserDetails` object — this contains the username, password (hashed), roles, etc.

### 8️⃣ User Entity Source
- The `UserDetails` is usually backed by a `User` entity from the database or other data source.

### 9️⃣ UserDetails Returned
- `UserDetailsService` returns the `UserDetails` to the `AuthenticationProvider`.

### 🔟 AuthenticationProvider Validates
- If this is a login, the password is validated.
- If it’s a token-based request (JWT/session), it just validates the token’s contents and expiration.

### 1️⃣1️⃣ AuthenticationManager Returns Result
- The `AuthenticationManager` returns a **fully authenticated `Authentication` object** if everything is valid.

### 1️⃣2️⃣ Security Context Holder Stores Auth
- This authenticated object is stored in the `SecurityContextHolder`.

---

### ✅ If Successful:
- The request proceeds to your **Controller → Service → Repository**.

### ❌ If Invalid:
- Spring Security returns a **`401 Unauthorized`** before your controller is touched.


## 🛠️ Implementation

### 1. Add Dependency

In your `pom.xml`, add the following dependency:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
</dependency>
```
- After adding this dependency, all API requests will return 401 Unauthorized due to Spring Security being automatically enabled.
🔗 **Reference:** [Spring Security Servlet Architecture](https://docs.spring.io/spring-security/reference/servlet/architecture.html)

---

### 2. Configure Security Filter Chain

1. Create a new package named `configuration`.
2. Inside it, create a file named `SecurityConfig.java`:

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.build();
    }
}
```

### 3. Define Endpoint Access Rules
- Configure which endpoints are **protected** and which are **public** (e.g., `/register`, `/login`).
- By default, Spring Security enables **CSRF protection** to prevent cross-site request forgery attacks.
- In **development mode**, you should disable CSRF protection to avoid `403 Forbidden` errors for `POST` or `PUT` requests.
