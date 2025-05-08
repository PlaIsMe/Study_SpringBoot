# Role Permission Authorization

## 📌 Role-Permission Structure

In a standard system:

```yaml
User -> many Role
Role -> many Permission
```


---

## ✅ Implementation Steps

### 1. Create Entities
- `Role` entity
- `Permission` entity

### 2. Create DTOs and Mappers
- Request and Response objects for `Role` and `Permission`
- Mappers to convert between entities and DTOs

### 3. Repository Layer
- `RoleRepository`
- `PermissionRepository`

### 4. Service Layer
- `RoleService`
- `PermissionService`

### 5. Controller Layer
- `RoleController`
- `PermissionController`

---

## 🚀 Application Startup Initialization

Use an `ApplicationRunner` to seed the database with default roles and permissions:

### 🟢 Default Permissions

```json
[
  {
    "name": "CREATE_DATA",
    "description": "Create data permission"
  },
  {
    "name": "DELETE_DATA",
    "description": "Delete data permission"
  },
  {
    "name": "UPDATE_DATA",
    "description": "Update data permission"
  }
]
```

### 🟢 Default Roles

```json
[
  {
    "name": "ADMIN",
    "description": "Admin role",
    "permissions": ["CREATE_DATA", "UPDATE_DATA", "DELETE_DATA"]
  },
  {
    "name": "USER",
    "description": "User role",
    "permissions": ["CREATE_DATA", "UPDATE_DATA"]
  }
]
```

---

## 🔐 Token Configuration
In the **authentication service**:
Modify the token's `scope` or `authorities` to include:
```yaml
ROLE_ADMIN, CREATE_DATA, UPDATE_DATA, DELETE_DATA
```

---

## 🔒 Method-Level Authorization

Use Spring Security’s `@PreAuthorize` annotation:

```java
@PreAuthorize("hasAuthority('CREATE_DATA')")
public void createSomething() {
    // Your logic
}
```
