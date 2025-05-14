# How to Validate with a Custom Annotation

## Example: Validating `dob` (Date of Birth)

### Step 1: Create `validator` Package

### Step 2: Create `DobConstraint` Class

Use the following annotations:

- `@Target` — Indicates where this annotation can be applied, e.g.,  
  `@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })`
- `@Retention` — Defines at what point the annotation is available (e.g., RUNTIME).
- `@Constraint` — Specifies that the class will handle validation.

For validating age by date of birth, define a parameter:

```java
int min();
```

### Step 3: Use the Annotation` in a Request DTO

```java
@DobConstraint(min = 10)
private LocalDate dob;
```

This means the minimum age required is 10.

### Step 4: Create `DobValidator` Class

- Implement the `ConstraintValidator` interface.
- Implement the following methods:
  - `initialize()` — Gets the annotation value (e.g., `min` age).
  - `isValid()` — Contains the logic to determine if the input data is valid.