# Advanced Exception Handling

## 1. Add `HttpStatusCode` to `exception.ErrorCode.java`
- Add a private `HttpStatusCode` field to `ErrorCode` class.
- This helps standardize error codes, e.g., for handling `403 Forbidden` when API is called with wrong role.

## 2. Update `exception.GlobalExceptionHandler.java`

### Modify `handlingAppException`
- Update the method to return the appropriate `ErrorCode` along with the response.

### Add `handlingAccessDeniedException`
- Handle `AccessDeniedException` to return standardized `403 Forbidden` error response.

## 3. Handle 401 Unauthenticated Errors

> Note: This cannot be handled inside `GlobalExceptionHandler` because it occurs at filter layers before reaching the service.

### Create `JwtAuthenticationEntryPoint.java` in `configuration` package
- Implements `AuthenticationEntryPoint`
- Override and implement the `commence()` method
- This method will be called when authentication fails and should return a standardized `401 Unauthorized` error response.
