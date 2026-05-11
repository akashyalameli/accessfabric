# AuthBridge

Enterprise-grade multi-tenant authentication and authorization platform built for modern SaaS systems.

AuthBridge demonstrates how production-ready identity infrastructure can be designed using modular architecture, secure token lifecycle management, tenant isolation, and role-based access control.

> Designed and built as a platform-engineering showcase for enterprise-grade authentication systems.

---

## Why AuthBridge?

Modern SaaS platforms require more than simple login APIs.

They need:

- Strong tenant isolation
- Secure session lifecycle management
- Refresh token rotation
- Role-based authorization
- Scalable modular architecture
- Extensibility for federation protocols like SAML and OAuth2

AuthBridge is an engineering-focused implementation of these capabilities.

It demonstrates how identity systems can evolve from foundational authentication into a full enterprise IAM platform.

---

## Core Features

### Multi-Tenant Identity Architecture
- Tenant-aware authentication flows
- Tenant-scoped user uniqueness
- Tenant context propagation across request lifecycle

---

### JWT Authentication
- Secure JWT generation and validation
- Tenant-aware token claims
- Role-aware token claims
- Stateless authentication pipeline

---

### Refresh Token Rotation
- Refresh token persistence
- Rotation on refresh
- Replay attack prevention
- Revocation support

---

### Role-Based Access Control (RBAC)
Supported roles:

- ADMIN
- USER
- AUDITOR

Capabilities:

- Route-level role enforcement
- JWT authority propagation
- Role updates reflected during token refresh

---

### Security Lifecycle Management
- BCrypt password hashing
- Expired/revoked refresh token cleanup
- Swagger JWT authorization support

---

### Database Versioning
- Flyway-based schema migrations
- Version-controlled database evolution

---

## Architecture

AuthBridge follows a **modular monolith** architecture designed for future service extraction.

### Backend Modules

```plaintext
authbridge/
 ├── auth/
 ├── identity/
 ├── tenant/
 └── shared/
```

### Module Responsibilities

#### Auth
Authentication flows and token lifecycle management

- Registration
- Login
- Refresh token rotation
- JWT issuance
- JWT validation

---

#### Identity
User identity and role modeling

- User domain
- Role definitions
- Identity introspection

---

#### Tenant
Tenant lifecycle and context resolution

- Tenant creation
- Tenant scoping
- Request tenant context propagation

---

#### Shared
Cross-cutting platform infrastructure

- Security configuration
- OpenAPI configuration
- Utility abstractions

---

## Tech Stack

### Backend
- Java 21
- Spring Boot 4
- Spring Security
- Spring Data JPA
- Flyway
- PostgreSQL
- Lombok
- springdoc-openapi

---

### Infrastructure
- Docker
- Docker Compose

---

### Tooling
- Maven
- Swagger / OpenAPI

---

## Security Flow

### Authentication

```plaintext
Register
   ↓
Password Hashing
   ↓
Login
   ↓
JWT + Refresh Token
   ↓
Protected Resource Access
```

---

### Refresh Token Rotation

```plaintext
Login
   ↓
Access + Refresh Token
   ↓
Refresh Request
   ↓
Old Refresh Token Revoked
   ↓
New Token Pair Issued
```

---

### Authorization

```plaintext
JWT Validation
   ↓
Security Context Population
   ↓
Role Extraction
   ↓
Spring Security Authorization
```

---

## Running Locally

### Prerequisites

- Java 21
- Docker
- Docker Compose
- Maven

---

### Clone Repository

```bash
git clone https://github.com/akashyalameli/authbridge.git
cd authbridge
```

---

### Start PostgreSQL

```bash
docker compose up -d
```

---

### Optional Environment Variables

```bash
AUTHBRIDGE_JWT_SECRET=your-secure-secret
```

---

### Run Backend

From backend directory:

```bash
./mvnw spring-boot:run
```

Windows:

```bash
mvnw.cmd spring-boot:run
```

---

### Swagger UI

Open:

```plaintext
http://localhost:8080/swagger
```

---

## Example API Usage

### Create Tenant

```http
POST /api/tenant
```

---

### Register User

```http
POST /api/auth/register
```

---

### Login

```http
POST /api/auth/login
```

Returns:

```json
{
  "accessToken": "...",
  "refreshToken": "..."
}
```

---

### Refresh Access Token

```http
POST /api/auth/refresh
```

---

### Identity Introspection

```http
GET /api/identity/me
```

---

## Current Status

### Implemented
- Multi-tenancy
- JWT authentication
- Refresh token rotation
- RBAC
- Token cleanup
- Swagger authorization integration

---

### In Progress
- Frontend administration dashboard

---

### Planned
- MFA / TOTP
- SAML 2.0 support
- OAuth2 social login
- Audit logging
- Session management dashboard
- Rate limiting
- Federation flows

---

## Design Principles

AuthBridge emphasizes:

### Security First
Authentication and authorization modeled as core platform concerns

### Evolutionary Architecture
Built as modular monolith with clear future microservice extraction paths

### Operational Simplicity
Minimal infrastructure complexity while preserving enterprise design patterns

### Platform-Oriented Engineering
Designed around real-world authentication system architecture patterns

---

## Project Roadmap

### Phase 1
Core Authentication Foundation

### Phase 2
Tenant Isolation

### Phase 3
Refresh Token Lifecycle

### Phase 4
RBAC

### Phase 5
MFA

### Phase 6
Federation (SAML / OAuth2)

### Phase 7
Frontend Dashboard

---

## Author

**Akash Yalameli**

Lead Full-Stack Software Engineer

GitHub: https://github.com/akashyalameli

---

## Purpose

AuthBridge was built as a platform-engineering showcase to demonstrate enterprise-grade authentication and authorization system design.
