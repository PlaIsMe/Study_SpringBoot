# Authorization with JWT, `SecurityContextHolder`, `@PreAuthorize`, and `@PostAuthorize`

## 1. Remove Endpoint-Based Authorization

Authorization using endpoint-based configuration is less common.  
Method-level authorization is more commonly used and provides better granularity.

---

## 2. Enable Method Security

In your `SecurityConfig` class, add the following annotation:

```java
@EnableMethodSecurity
```

---

## 3. Restrict Method Access to Admin Only

Use `@PreAuthorize` to check roles before the method is executed.

```java
@PreAuthorize("hasRole('ADMIN')")
public void yourMethod() {
    // method body
}
```
The method will not be called if the request doesn't match the role.
This is efficient because it avoids executing the method if unauthorized.

---

## 4. Restrict Access Based on Returned Object
Use `@PostAuthorize` to perform authorization after the method has executed.

```java
@PostAuthorize("returnObject.username == authentication.name")
public User getUserById(Long userId) {
    // returns a User object
}
```
The method will be executed, but the result will only be returned if the condition is true.
Useful when access depends on the method's result.

---

## 5. Accessing Current User's Username
You can get the currently authenticated username using:

```java
String username = SecurityContextHolder.getContext().getAuthentication().getName();
```