# Daily Progress — 21 Jan 2026

## Spring Security Basics
- Framework for authentication & authorization in Spring Boot.
- Provides role-based access control.
- Protects against CSRF, XSS, session fixation, brute force attacks.
- Works by intercepting requests before they reach controllers.

## JWT (JSON Web Token)
- Stateless token carrying user identity and roles.
- Structure: Header + Payload + Signature.
- Used in APIs so clients log in once, then send the token with each request.
- Validation checks: signature, expiration, claims.

## SecurityConfig Setup
- Configured Spring Security with SecurityFilterChain.
- For JWT: disabled CSRF and added JWT filter.
- Compared Basic Auth vs JWT:
  - Basic → sends username/password each time.
  - JWT → sends signed token.

## Common Errors & Fixes
- **No validator for @NotBlank** → occurs on non‑String fields (like enums). Fix: use @NotNull.
- **Request method 'GET' not supported** → endpoint missing @GetMapping.
- **Request method 'PUT' not supported** → mismatch between /users/preferences vs /users/{id}/preferences.
- **Preferences showing null** → service layer wasn’t updating/saving entity fields properly.

## UserController Design
- Endpoints built:
  - Get user by ID
  - Update preferences
  - Become curator
  - Protected profile endpoint
- Learned importance of error handling and authorization checks (users shouldn’t update others’ preferences).

## Security Threats Explained
- **CSRF:** tricking a logged‑in user into unwanted actions.
- **XSS:** injecting malicious scripts into web pages.
- **Session fixation:** forcing a victim to use a known session ID.
- **Brute force:** repeatedly guessing passwords.

## Additional Work
- Worked on **token validation** today.
- Tested JWT behavior in **Postman**:
  - Changed the **expiry date** of the token to simulate an expired session.
  - Sent requests using **Authorization: Bearer <token>** header.
  - Verified that expired tokens were correctly rejected with `401 Unauthorized`.
  - Confirmed that valid tokens allowed access to protected endpoints.

## Additional Work
- Worked on **token validation** today.
