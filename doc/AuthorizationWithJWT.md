# Authorization with JWT in Spring Boot

This guide demonstrates how to secure specific APIs in a Spring Boot application using JWT and role-based authorization.

## Goal

Only allow users with proper permissions (roles) to call specific APIs using JWT tokens.

---

## Implementation Steps

### 1. Define Roles in the User Entity

In the `User` entity, add:

```java
private Set<String> roles;
```

## Why Use `Set` for Roles?

Use a `Set<String>` for storing user roles to ensure that each role is unique and avoid duplication.

---

### 2. Update `UserResponse`

Include the user's roles in the response DTO if needed for front-end or client logic.

---

### 3. Define Role Enum

Create a new `enums` package and add a `Role.java` file:

```java
public enum Role {
    USER,
    ADMIN
}
```

---

### 4. Set Default Role in `UserService`
In the `createUser` method of `UserService`, assign the default role to new users:
Create an admin user by adding ApplicationInitConfig.java

---

### 5. Restrict `/users` Endpoint to Admin Only

In `SecurityConfig`, configure the `/users` endpoint so that only users with the `ADMIN` role can access it.

---

### 6. Add `scope` Claim to JWT Token

In `AuthenticationService`, when generating the JWT token, add a `scope` claim that includes the user's roles.

- Roles must be separated by a space (`" "`), as required by OAuth2 standards.
- Example: `"scope": "USER ADMIN"`

---

### 7. To retrieve the Authenticated User's Info

Use the following code to access the current authenticated user's name and roles:

```java
var authentication = SecurityContextHolder.getContext().getAuthentication();
log.info("Username: {}", authentication.getName());
authentication.getAuthorities().forEach(grantedAuthority ->
    log.info(grantedAuthority.getAuthority())
);
```

### 7. Authorize with Scope in SecurityConfig

To authorize using the `scope` claim, update the `SecurityFilterChain` in `SecurityConfig`.

By default, Spring Security uses the `SCOPE_` prefix for values in the `scope` claim.

You can modify this behavior using `JwtAuthenticationConverter`: