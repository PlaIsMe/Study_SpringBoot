# Create Exception Handler

## How to Create an Exception Handler
1. Create a new package named `exception`.
2. Create a new file `GlobalExceptionHandler.java` and annotate it with `@ControllerAdvice`.
3. Define `@ExceptionHandler(value = RuntimeException.class)` to catch all runtime exceptions.  
   This will work with `.orElseThrow(() -> new RuntimeException("exception"));`.

---

# Validation

## How to Set Up Validation
1. Add `spring-boot-starter-validation` to your dependencies.
2. Define validation annotations in the DTO/request class.
3. Add `@Valid` before `@RequestBody` to enable request validation.
4. For handling exception of validation, override the `@ExceptionHandler(value = MethodArgumentNotValidException.class)`
