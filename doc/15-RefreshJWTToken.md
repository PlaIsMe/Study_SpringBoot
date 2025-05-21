# Refresh Token Flow
![Refresh Token Flow](<images/Refresh Token Flow.png)

This sequence diagram illustrates how a client (such as a mobile or web app) handles expired access tokens using a refresh token:

1. The **client** sends a request to the **backend** API using a stored access token (typically saved in local storage).
2. If the token is **expired**, the **backend** returns a `401 Unauthorized` error.
3. The **client** then sends a **refresh token** request to the **IDP (Identity Provider)**.
4. If the refresh token is still within the **refreshable duration**, the IDP returns a **new access token**.
5. The client retries the original API request with the **new token**.
6. The **backend** validates the new token and returns the **requested resource**.

> ⚠️ If the refresh token is also expired, the client must prompt the user to **log in again**.