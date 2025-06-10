# What is a Concurrent Request?

In a real-world project, there can be cases where **multiple users send requests at the same time** (i.e., concurrent requests).  
If your logic checks for data existence before creating new data, a race condition may occur:
- Multiple requests pass the "exists" check at the same time.
- All of them try to insert the same data.
- This leads to **duplicate records** or **data integrity issues**.

## 🔐 Solution: Use Database-Level Constraints

Use `unique` constraints in the database to **ensure that duplication does not occur**, even if multiple threads attempt to insert the same value.

---

## 🧪 Example

```java
@Column(
    name = "username",
    unique = true,
    columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci"
)
String username;
```

- COLLATE utf8mb4_unicode_ci: supports Unicode characters with case-insensitive comparison

## 🛡️ Why Unique Constraints Matter in Concurrency
- Prevent duplicate entries even when concurrent threads bypass your business logic.
- Forces the database to maintain data integrity.
- Reduces the need for complex locking mechanisms in code.

## ✅ Best practice: Always apply @Column(unique = true) for fields that must be globally unique (like username, email, etc.).