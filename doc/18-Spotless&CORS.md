
# How to Format Code with Spotless

## 🛠 Installation in `pom.xml`

```xml
<plugin>
    <groupId>com.diffplug.spotless</groupId>
    <artifactId>spotless-maven-plugin</artifactId>
    <version>2.43.0</version>
    <configuration>
        <java>
            <removeUnusedImports /> <!-- Automatically removes unused import statements -->
            <toggleOffOn/> <!-- Allows sections of code to be excluded from formatting using // spotless:off -->
            <trimTrailingWhitespace/> <!-- Trims any trailing whitespaces -->
            <endWithNewline/> <!-- Ensures the file ends with a newline -->
            <indent>
                <tabs>true</tabs> <!-- Indents using tabs -->
                <spacesPerTab>4</spacesPerTab> <!-- Defines 4 spaces per tab for alignment -->
            </indent>
            <palantirJavaFormat/> <!-- Uses Palantir Java format style (Google Java Format base) -->
            <importOrder>
                <order>java,jakarta,org,com,com.diffplug,</order> <!-- Custom order of import groups -->
            </importOrder>
        </java>
    </configuration>
    <executions>
        <execution>
            <phase>compile</phase> <!-- Runs formatting on compile phase -->
            <goals>
                <goal>apply</goal> <!-- Applies formatting -->
            </goals>
        </execution>
    </executions>
</plugin>
```

## ▶ How to Run

- Check format without changing files:  
```bash
mvn spotless:check
```

- Format and apply changes:  
```bash
mvn spotless:apply
```

---

# 🌐 CORS Configuration

## What is CORS?

**CORS (Cross-Origin Resource Sharing)** is a mechanism that allows your web app running at one domain (e.g., `http://frontend.com`) to access resources from a different domain (e.g., `http://api.backend.com`).

Browsers implement CORS to prevent malicious cross-origin requests unless explicitly allowed.

## 🛡 How it Works

Add the following configuration to your Spring Security configuration class:

```java
@Bean
public CorsFilter corsFilter() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();

    corsConfiguration.addAllowedOrigin("*"); // ⚠ Replace * with your specific web domain for production!
    corsConfiguration.addAllowedMethod("*"); // Allow all HTTP methods (GET, POST, etc.)
    corsConfiguration.addAllowedHeader("*"); // Allow all request headers

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfiguration); // Apply to all endpoints

    return new CorsFilter(source);
}
```

### ⚠ Security Tip

In production, never use `"*"` in `addAllowedOrigin`. Always specify your frontend domain:

```java
corsConfiguration.addAllowedOrigin("https://yourfrontenddomain.com");
```

---

✅ With this setup, you ensure both clean code formatting and safe cross-domain communication.
