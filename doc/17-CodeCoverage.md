# ✅ Code Coverage with JaCoCo in Spring Boot

## 📌 What is Code Coverage?

**Code coverage** measures how much of your source code is tested by unit tests.  
It helps you:

- Identify untested parts of your codebase
- Improve test quality and confidence
- Track testing progress over time

---

## ⚙️ How to Set Up JaCoCo

Add the following plugin to your `pom.xml`:

```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.12</version>
    <executions>
        <execution>
            <goals>
                <goal>prepare-agent</goal>
            </goals>
        </execution>
        <execution>
            <id>report</id>
            <phase>prepare-package</phase>
            <goals>
                <goal>report</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <excludes>
            <exclude>com/study/springboot/dto/**</exclude>
            <exclude>com/study/springboot/entity/**</exclude>
            <exclude>com/study/springboot/mapper/**</exclude>
            <exclude>com/study/springboot/configuration/**</exclude>
        </excludes>
    </configuration>
</plugin>
```

---

## 🚫 Why Exclude These Packages?

| Package          | Reason                                                                 |
|------------------|------------------------------------------------------------------------|
| `dto`            | Only contains data transfer objects (POJOs) with no logic to test      |
| `entity`         | Contains JPA entities; no business logic to cover                      |
| `mapper`         | Usually simple mappings; often generated or trivial                    |
| `configuration`  | Spring configuration classes, not part of the core logic being tested  |
|------------------|------------------------------------------------------------------------|

These files don't contain business logic, so including them would **unfairly reduce your code coverage percentage**.

---

## 🚀 Running JaCoCo

```bash
./mvnw test jacoco:report
```

After the run, open the report at:

```
target/site/jacoco/index.html
```

This report shows coverage results in an HTML format.

---

## 📊 Metrics in the JaCoCo Report

| Metric         | Meaning                                                                 |
|----------------|-------------------------------------------------------------------------|
| **Total Coverage** | Percentage of code covered by tests                              |
| **Missed Branches** | Number of conditional branches (e.g., `if`, `switch`) not executed |
| **Cxty**        | Cyclomatic Complexity: number of paths through the code               |
| **Missed**      | Lines or branches not covered                                          |

---

## 📁 Sample Output Location

```
Project Root/
└── target/
    └── site/
        └── jacoco/
            └── index.html ✅ ← open this file
```

---

## ✅ Summary

- JaCoCo helps evaluate how well your code is tested.
- Exclude boilerplate classes from coverage.
- Use the generated HTML report to improve your tests.
