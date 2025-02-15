# Project Initialization

## How to Initialize the Project
1. Go to [Spring Initializr](https://start.spring.io/)
2. Configure the project with the following settings:
   - **Project:** Maven
   - **Language:** Java
   - **Spring Boot Version:** Default
   - **Packaging:** JAR
   - **Java Version:** 21
   - **Dependencies:**
     - Spring Web
     - Spring Data JPA
     - MySQL Driver
3. Click **Generate** to download the project.

---

## How to Connect to MySQL in Docker

1. Pull the latest MySQL Docker image:
   ```sh
   docker pull mysql:latest
   ```
2. Run a MySQL container:
   ```sh
   docker run --name springboot-study -p 3306:3306 -e MYSQL_ROOT_PASSWORD=rootroot -d mysql:latest
   ```
3. Exec and create new database:
    ```sh
    docker exec -it mysql mysql -u root -p
    CREATE DATABASE springboot_study;
    ```

---

# Comparison: UUID vs. long as Primary Key

| **Aspect**       | **UUID (String)** | **long (Auto-incremented)** |
|------------------|------------------|-----------------------------|
| **Uniqueness**   | Globally unique   | Unique within the database  |
| **Scalability**  | Good for distributed systems | Potential contention in high-scale systems |
| **Performance**  | Slower (128-bit, random) | Faster (64-bit, sequential) |
| **Indexing**     | Worse (larger, less efficient indexing) | Better (smaller, sequential indexing) |
| **Replication**  | Easier for multi-region DBs | Harder if IDs need merging |
| **Sorting**      | Poor (random values) | Excellent (monotonic increasing) |
| **Security**     | Harder to guess (better for external exposure) | Predictable, easy to guess |

---

## When to Use **UUID (String id)**
✅ When you need **globally unique identifiers** across multiple databases or regions (e.g., microservices, sharding).  
✅ When **security is a concern** (e.g., exposing IDs in URLs).  
✅ When records need to be **created offline and synced later**.  
✅ When working with **NoSQL databases** (e.g., MongoDB).  

## When to Use **long id**
✅ When you need **better performance** for indexing and lookups.  
✅ When using a **single centralized database** (e.g., a monolithic system).  
✅ When **database inserts are frequent**, and you want better cache locality.  
✅ When **sequential ordering of IDs** is needed.  

---

## **Verdict for a Huge System**
- If your system is **distributed** and may scale across multiple databases, use **UUID**.
- If your system is a **single, high-performance relational database**, use **long** for efficiency.
- **For a compromise**, you can use a combination of both:
  - Use **long** as the primary key for performance.
  - Use a **UUID** as an external reference ID (e.g., for API exposure or distributed uniqueness).

