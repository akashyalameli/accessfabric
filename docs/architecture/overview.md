# AccessFabric Architecture Overview

## Introduction

AccessFabric is a modular multi-tenant authentication and authorization platform designed around modern identity-system architecture principles.

The platform focuses on:

- Secure authentication flows
- Tenant isolation
- Stateless authorization
- Token lifecycle management
- Evolutionary modular architecture

The system is intentionally designed as a modular monolith to optimize development velocity while preserving clear service boundaries for future extraction.

---

# Architectural Goals

The primary architectural goals of AccessFabric are:

- Strong separation of concerns
- Security-first design
- Stateless authentication
- Extensible authorization model
- Evolutionary scalability
- Operational simplicity

---

# High-Level Architecture

```plaintext
Client
   ↓
Spring Security Filter Chain
   ↓
JWT Authentication Filter
   ↓
Tenant Context Resolution
   ↓
Controller Layer
   ↓
Application Services
   ↓
Persistence Layer
   ↓
PostgreSQL
```

---

# Modular Structure

```plaintext
accessfabric/
 ├── auth/
 ├── identity/
 ├── tenant/
 └── shared/
```

---

# Module Responsibilities

## Auth Module

Responsible for authentication and token lifecycle management.

### Responsibilities

- User login
- JWT issuance
- JWT validation
- Refresh token rotation
- Session continuity
- Token replay prevention

### Important Components

- `AuthService`
- `JwtService`
- `JwtAuthenticationFilter`
- `RefreshTokenRepository`

---

## Identity Module

Responsible for identity and authorization modeling.

### Responsibilities

- User persistence
- Role modeling
- Identity introspection
- Authorization claim propagation

### Important Components

- `User`
- `Role`
- Identity APIs

---

## Tenant Module

Responsible for tenant-aware platform behavior.

### Responsibilities

- Tenant persistence
- Tenant-scoped identity isolation
- Tenant context propagation

### Design Notes

Tenant context is derived from JWT claims during authenticated request processing.

This avoids insecure client-controlled tenant headers for protected endpoints.

---

## Shared Module

Contains reusable infrastructure and cross-cutting platform configuration.

### Responsibilities

- Security configuration
- OpenAPI configuration
- Utility abstractions
- Shared platform support code

---

# Authentication Flow

```plaintext
User Login Request
   ↓
Credential Validation
   ↓
Password Hash Verification
   ↓
JWT Generation
   ↓
Refresh Token Persistence
   ↓
Access Token Returned
```

---

# Authorization Flow

```plaintext
Incoming Request
   ↓
JWT Validation
   ↓
SecurityContext Population
   ↓
Role Extraction
   ↓
Spring Security Authorization
   ↓
Protected Resource Access
```

---

# Refresh Token Rotation

AccessFabric implements refresh token rotation to reduce replay attack risk.

### Flow

```plaintext
Refresh Request
   ↓
Refresh Token Validation
   ↓
Old Token Revocation
   ↓
New Access Token Generation
   ↓
New Refresh Token Issuance
```

### Security Benefits

- Replay attack mitigation
- Stateless access token renewal
- Controlled token lifecycle management

---

# Tenant Isolation Strategy

AccessFabric uses tenant-scoped identity isolation.

### User uniqueness

```plaintext
tenant_id + email
```

This allows the same email to exist across different tenants while preserving isolation boundaries.

---

# RBAC Design

Current RBAC implementation uses role claims propagated through JWTs.

### Supported Roles

- ADMIN
- USER
- AUDITOR

### Authorization Enforcement

Authorization is enforced through Spring Security route matchers and granted authorities.

---

# Database Strategy

AccessFabric uses PostgreSQL with Flyway-based schema versioning.

### Benefits

- Version-controlled schema evolution
- Repeatable local environments
- Migration traceability

---

# Infrastructure Strategy

The platform currently uses Docker Compose for local infrastructure orchestration.

### Current Services

- PostgreSQL

The backend service is intentionally runnable both locally and containerized to optimize development iteration speed.

---

# Future Evolution

Planned future capabilities include:

- MFA / TOTP
- SAML federation
- OAuth2 social login
- Audit event pipeline
- Frontend administration dashboard
- Rate limiting
- Distributed session management

---

# Architectural Philosophy

AccessFabric intentionally prioritizes:

- clear modular boundaries
- maintainable evolution paths
- operational simplicity
- platform-oriented engineering

The current implementation favors clarity and correctness over premature distributed complexity.
