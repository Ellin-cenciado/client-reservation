# Client Reservation

A full-stack reservation system with Java Spring Boot backend, React TypeScript frontend, and Mercado Pago payment integration.

## Project Overview

This repository contains a monolithic multi-module Maven project that manages client reservations with payment processing capabilities. The project uses a clear separation between backend services, frontend applications, and shared utilities.

### Tech Stack

**Backend:**
- Java 21
- Spring Boot 3.5.6
- Spring Data JPA + PostgreSQL
- Spring Validation & Mail
- Mercado Pago SDK v3.1.0
- Maven (monolithic multi-module setup)

**Frontend:**
- React 19.2.0 with TypeScript
- Vite (modern build tool)
- Tailwind CSS 4.3.0
- Bootstrap 5.3.8
- Axios for HTTP requests

## Project Structure

```
.
├── backend/               # Spring Boot REST API server
│   └── src/main/java/    # Java source code
├── desktop/              # Desktop application module (JavaFX or similar)
├── common/               # Shared domain models and entities
│   └── src/main/java/    # Common/shared code used by backend
├── client-reservation-vite/  # React + Vite web frontend
│   ├── src/components/   # React components
│   ├── src/types/        # TypeScript interfaces
│   ├── src/utils/        # Utility functions
│   └── package.json      # NPM dependencies
└── pom.xml              # Root Maven POM (manages all modules)
```

## Branches Overview

### Active Development Branches

#### `main` (Default)
- **Status:** Empty placeholder
- **Purpose:** Integration branch for stable releases
- **Usage:** Merge tested features here for production deployment

#### `feat/backend`
- **Status:** Core backend implementation
- **Features:**
  - Spring Boot REST API setup
  - Core business logic structure
  - Database connectivity (PostgreSQL via JPA)
  - Basic project structure (backend, frontend, common modules)
- **Next Steps:** Implement API endpoints and integrate with Mercado Pago

#### `feat/web`
- **Status:** Full-stack development branch
- **Features:**
  - Backend module with Spring Boot configuration
  - React Vite frontend (client-reservation-vite directory)
  - Shared common module for entities
  - Desktop application module placeholder
  - Maven monolithic architecture
- **Purpose:** Main development integration point combining backend and web frontend

#### `web/vite`
- **Status:** Frontend-focused with latest Vite tooling
- **Features:**
  - React 19.2.0 + TypeScript setup
  - Vite 7.2.4 as build tool
  - Tailwind CSS + Bootstrap styling
  - Component-based architecture ready
  - Axios for backend communication
- **Focused On:** Modern web development practices, fast HMR (Hot Module Replacement)
- **Useful For:** Frontend-specific work and UI development

#### `backend/mp-integration`
- **Status:** Payment integration in progress
- **Features:**
  - Mercado Pago SDK (v3.1.0) integrated into backend
  - Extended dependencies for payment processing
  - Full backend + Vite frontend structure
  - Common module for shared entities
- **Purpose:** Implement payment checkout flow and transaction handling
- **Key Capability:** Process client payments through Mercado Pago

#### `feat/restructure`
- **Status:** Refactoring and optimization branch
- **Features:**
  - Reorganized project structure
  - Updated .gitignore for Java + Node.js projects
  - Cleaner separation of concerns
  - Enhanced configuration management
- **Purpose:** Improve code organization and maintainability
- **Notes:** Experimental branch for architectural improvements

---

## Getting Started

### Prerequisites
- **Java 21** (for backend)
- **Node.js 18+** (for frontend)
- **Maven 3.8+** (for Java builds)
- **PostgreSQL** (for database)

### Backend Setup (feat/backend or later branches)
```bash
cd backend
mvn clean install
mvn spring-boot:run
```
Backend runs on `http://localhost:8080`

### Frontend Setup (web/vite branch)
```bash
cd client-reservation-vite
npm install
npm run dev
```
Frontend runs on `http://localhost:5173`

### Build & Deploy
```bash
# Full monolithic build
mvn clean package

# Frontend production build
cd client-reservation-vite
npm run build
```

---

## Key Features (Planned/Implemented)

- ✅ Multi-module Maven architecture
- ✅ Spring Boot REST API framework
- ✅ Modern React + TypeScript frontend
- ✅ Mercado Pago payment integration (backend/mp-integration)
- 🔄 Database schema and JPA entities (common module)
- 🔄 Desktop application module
- ⏳ Authentication & authorization
- ⏳ Email notifications
- ⏳ Reservation management endpoints

---

## Recommended Workflow

1. **Start with `feat/web`** for full-stack development
2. **Use `web/vite`** for frontend-specific features
3. **Use `backend/mp-integration`** for payment implementation
4. **Use `feat/restructure`** to catch up with organizational improvements
5. **Merge to `main`** when features are production-ready

---

## Development Notes

### Environment Variables
Create `.env` files in appropriate directories:
- Backend: `backend/.env` (database, mail config)
- Frontend: `client-reservation-vite/.env` (API endpoint)

**Never commit secrets!** See `.gitignore` for coverage.

### Database
Configure PostgreSQL connection in Spring Boot `application.properties`

### Mercado Pago
Add credentials in backend configuration for payment processing (see `backend/mp-integration` branch)

---

## License

MIT License - See LICENSE file for details

---

## Contact & Support

For questions or issues, refer to the respective branch maintainers and check open issues in the repository.
