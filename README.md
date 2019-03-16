# API Boilerplate

Simple Javalin + Kotlin based API boilerplate. Please note that this project is WIP.

## Checklist

- Restful (HTTP + JSON with correct HTTP methods) / GraphQL based ✔️
- Build + dependency management tool (e.g. npm, gradle) ✔️
- Testing
  - Unit ✔️
    - Usually needs a mocking library
  - Integration (request / db level) ✔️ 
  - Contract / Pact
  - CI testing with test DB etc. ✔️
  - Test DB should be cleaned after each (unit) test run ✔️
- Linting / style check
- Multi env. configuration ✔️
  - Local, QA, Production
- Secrets management ✔️
  - Also may vary depending on env.
- CORS in development
  - Also may vary depending on env.
- Hot reload in development (automatic restart)
- JSON + XML parsing / generation
- HTTP client (for service level integration) or maybe Prot. Buffer / gRPC
- Request param validation (JSON body validation)
- Logging + log format customization
- DB connection pooling ✔️
- DB seeding / data loading
- ORM / Query builder ✔️
- DB migration ✔️
- Error handling + reporting
- CI / CD
- Code quality analysis / measurement
- Dependency Injection (if applicable, for better testable code) ✔️
- Authenticated paths / Non-Authenticated paths
- JWT Authentication
- Authorization
- API documentation / spec (e.g. openAPI / Swagger)
- Docker + Docker-Compose setup
- Horizontal scaling strategy (API should be stateless for example)
- Data pagination

