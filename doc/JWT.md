# JWT in Spring Boot

## Token Based Authentication Flow

```
+-------------------+                                    +-------------------+
|      CLIENT       |                                    |      SERVER       |
+-------------------+                                    +-------------------+
        |                                                       |
        | 1. User logs in with {username, password}             |
        | ----------------------------------------------------> |
        |                                                       |
        |                                     2. Creates JWT     |
        |                                                       |
        | 3. Sends encrypted {JWT}                               |
        | <---------------------------------------------------- |
        |                                                       |
        | 4. Saves JWT in localStorage                           |
        |                                                       |
        | 5. Sends Auth request with JWT in header               |
        |   headers: {                                           |
        |     Authorization: "Bearer ${JWT_TOKEN}"              |
        |   }                                                   |
        | ----------------------------------------------------> |
        |                                                       |
        |                                     6. Compares JWT    |
        |                                                       |
        | 7. Sends response on every subsequent request          |
        | <---------------------------------------------------- |
        |                                                       |
+-------------------+                                    +-------------------+
```

## JSON Web Token (JWT) Structure
+---------------------+
|   JSON WEB TOKEN    |                       +--------------------------+
+---------------------+                       |                          |
|                     |                       |     {                    |
|  +-----------+      |                       |         "typ": "JWT",    |
|  |  HEADER   |----------------------------> |         "alg": "HS256"   |
|  +-----------+      |                       |     }                    |
|                     |                       |                          |
|                     |                       +--------------------------+
|                     |
|                     |                       +---------------------------------+
|                     |                       |                                 |
|                     |                       |     {                           |
|  +-----------+      |                       |       "sub": "user10001",       |
|  |  PAYLOAD  |----------------------------> |       "iat": 1569302116,        |
|  +-----------+      |                       |       "role": "admin",          |
|                     |                       |       "user_id": "user10001"    |
|                     |                       |     }                           |
|                     |                       +---------------------------------+
|                     |          
|                     |     
|                     |                       +-------------------------------------+
|                     |                       |   HMAC-SHA256(                      |
|  +--------------+   |                       |     base64urlEncode(header) + '.' + |
|  |  SIGNATURE   |-------------------------> |     base64urlEncode(payload),       |
|  +--------------+   |                       |     secret_salt                     |
|                     |                       |   )                                 |
+---------------------+                       +-------------------------------------+

- **Header**: Contains type and algorithm.
- **Payload**: Contains claims (subject, issued at, etc).
  - ⚠ Avoid storing too much or sensitive data.
- **Signature**: A hash of the header and payload with the secret salt.

---

## How to Implement JWT in Spring Boot

### Add dependency in `pom.xml`:
```xml
<dependency>
  <groupId>com.nimbusds</groupId>
  <artifactId>nimbus-jose-jwt</artifactId>
  <version>9.30.1</version>
</dependency>
```

### Generate JWT Token
```java
private String generateToken(User user) {
    JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

    JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
    .subject(user.getUsername())
    .issuer("mydomain.com")
    .issueTime(new Date())
    .expirationTime(new Date(
        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
    ))
    .claim("userId", user.getId())
    .build();

    Payload payload = new Payload(jwtClaimsSet.toJSONObject());

    JWSObject jwsObject = new JWSObject(header, payload);

    try {
        jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
        return jwsObject.serialize();
    } catch (JOSEException e) {
        e.printStackTrace();
        throw new AppException(ErrorCode.INTERNAL_SERVER_FAILED);
    }
}
```
Use the following tool to generate a secure key:  
🔗 [AES-256-CBC Key Generator](https://generate-random.org/encryption-key-generator?count=1&bytes=32&cipher=aes-256-cbc)

To manually verify a JWT token, use:  
🔗 [JWT.io Debugger](https://jwt.io/)

### Verify JWT token
```
Create dto IntrospectRequest, IntrospectResponse
Create method introspect
```
```java
public IntrospectResponse introspect(IntrospectRequest request) {
    var token = request.getToken();

    JWSVerifier verifier;
    try {
        verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        var verified = signedJWT.verify(verifier);
        return IntrospectResponse.builder().valid(verified && expiryTime.after(new Date())).build();
    } catch (JOSEException e) {
        e.printStackTrace();
        throw new AppException(ErrorCode.INTERNAL_SERVER_FAILED);
    } catch (ParseException e) {
        e.printStackTrace();
        throw new AppException(ErrorCode.INTERNAL_SERVER_FAILED);
    }
}
```