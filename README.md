# 🔐 Spring Boot JWT API

Proyecto de ejemplo con autenticación mediante JWT, documentación Swagger/OpenAPI y pruebas unitarias. Usa Spring Boot 3.5.4 y base de datos MySQL.

Esta API permite **registrar usuarios, iniciar sesión y gestionar tareas personales asociadas a cada cuenta**. Es un backend básico ideal para cualquier aplicación tipo "to-do list" con seguridad integrada.

---

## 🚀 Tecnologías utilizadas

- Java 17
- Spring Boot 3
  - Spring Web
  - Spring Security
  - Spring Data JPA
  - Spring Validation
- JWT (`io.jsonwebtoken`)
- MySQL
- Swagger UI / OpenAPI (`springdoc-openapi`)
- JUnit 5 + Mockito
- Lombok
- Maven

---

## ⚙️ Configuración del proyecto

### Pre-requisitos

- Java 17+
- Maven
- MySQL (con una base de datos llamada `JWT_Task`)
- IDE recomendado: IntelliJ o VS Code

### Configuración en `application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/JWT_Task
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update

jwt.secret=Q2xhdmU0MyZTRUNSRVQzMkJZVEVTU0VTQ1JFVDEyMX==
jwt.expiration=86400000

springdoc.swagger-ui.path=/swagger-ui.html
springdoc.api-docs.path=/api-docs
```

---

## 🧑‍💻 Funcionalidades principales

- Registro de usuarios
- Inicio de sesión con JWT
- Protección de endpoints privados
- CRUD de tareas asociadas a cada usuario autenticado
- Documentación Swagger
- Pruebas unitarias con JUnit y Mockito

---

## 🔐 Autenticación JWT

Este proyecto implementa autenticación basada en JWT:

- Registro y login de usuarios
- Generación y validación de tokens
- Protección de rutas privadas

### Ejemplo de headers para autorización:

```
Authorization: Bearer {token}
```

---

## 📚 Documentación Swagger

Puedes acceder a la documentación interactiva de la API en:

```
http://localhost:8080/swagger-ui.html
```

---

## 📦 Ejecución

Para iniciar la aplicación localmente:

```bash
mvn spring-boot:run
```

---

## 🧪 Pruebas

Las pruebas unitarias están escritas con JUnit 5 y Mockito. Para ejecutarlas:

```bash
mvn test
```

---

## ✍️ Autor

Desarrollado por **Ernesto Zazueta**  
🔗 GitHub: [@Netokingpo22](https://github.com/Netokingpo22)  
💼 Especializado en desarrollo backend con Java y Spring Boot.

---

## 📄 Licencia

Este proyecto es de uso libre para fines educativos o como base para tus propios proyectos.
