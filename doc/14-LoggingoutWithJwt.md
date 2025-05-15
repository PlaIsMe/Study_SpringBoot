# Log out with JWT 

If the token expires in 1 hour, and the user logs out before that, the token must be considered expired immediately upon logout.

## Step 1: Add a Claim to Store the Token ID

Add a unique token ID to your JWT when generating it:

```java
.jwtID(UUID.randomUUID().toString())
```

## Step 2: Create an Entity to Store Logged Out Tokens

```java
    @Id
    String id;
    Date expiryTime;
```
- Remember to create a job that runs daily to remove expired tokens by checking the `expiryTime` field.

- Create a repository layer to manage logged out tokens.

## Step 3: Update Security Configuration - JWT Decoder

- Create a `CustomJwtDecoder` class to override the default JWT decoding behavior.
- In this class, use the `introspect` method to check whether the token ID (`jti`) exists in the logged out token store.
- If the token is found (i.e., it has been logged out), reject the token as invalid.
