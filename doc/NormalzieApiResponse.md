# Normalizing API Response  

## 📌 Why Normalize API Response?  
Normalizing API responses ensures **consistency**, improves **error handling**, and makes API consumption easier for clients.  

## 🚀 Steps to Normalize API Response  

### 1⃣ Create `ApiResponse` Class  
- Define a generic class `ApiResponse<T>` in `dto/request`.  
- This class should contain fields like `code`, `message`, and `result`.  

### 2⃣ Modify Controller Return Objects  
- Update all controller methods to return `ApiResponse<ReturnType>`.  
- Example:  
  ```java
  @PostMapping
  public ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest request) {
      ApiResponse<User> apiResponse = new ApiResponse<>();
      apiResponse.setResult(userService.createRequest(request));
      return apiResponse;
  }
  ```

## ⚡ Advanced Exception Handling  

### 4⃣ Validate `UserCreationRequest`  
- Apply `@Size` annotation to enforce validation rules.  
- Example:  
  ```java
  public class UserCreationRequest {
      @Size(min = 3, message = "USERNAME_INVALID")
      private String username;
  }
  ```

### 2⃣ Define Error Codes  
- Create an `ErrorCode` enum for structured error messages.  
- Example:  
  ```java
  @Getter
  @Setter
  public enum ErrorCode {
      USERNAME_INVALID(1003, "Username must be at least 3 characters");
      
      private final int code;
      private final String message;
  }
  ```

### 6⃣ Customize `MethodArgumentNotValidException` Handling  
- Enhance error handling to return a structured response.  
- Example:  
  ```java
  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  ResponseEntity<ApiResponse> handlingValidation(MethodArgumentNotValidException exception) {
      String enumKey = exception.getFieldError().getDefaultMessage();
      ErrorCode errorCode = ErrorCode.INVALID_KEY;
      try {
          errorCode = ErrorCode.valueOf(enumKey);
      } catch (IllegalArgumentException e) {
      }
      ApiResponse apiResponse = new ApiResponse();
      apiResponse.setCode(errorCode.getCode());
      apiResponse.setMessage(errorCode.getMessage());

      return ResponseEntity.badRequest().body(apiResponse);
  }
  ```

## ✅ Summary  
✔ Use `ApiResponse<T>` to standardize responses.  
✔ Hide `null` fields using `@JsonInclude`.  
✔ Implement structured error handling with custom error codes.  
✔ Improve validation with `@Size` and exception handlers.  

