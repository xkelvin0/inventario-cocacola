# 🥤 Coca-Cola Inventory System - Nivel Ingeniero

Sistema avanzado de gestión de inventarios desarrollado para la **UTP (Verano 2026)**. Este aplicativo permite el control total de productos, lotes y movimientos de almacén bajo una arquitectura robusta de Spring Boot y MySQL.

## 🚀 Características de Nivel Ingeniero

Este proyecto incluye funcionalidades avanzadas de auditoría y análisis de negocio:

*   **Dashboard de Gestión**: Visualización de Valorización Total del Inventario, Unidades en Stock y Alertas Críticas.
*   **Top 5 de Movimientos**: Identificación automática de productos con mayor rotación.
*   **Alertas Inteligentes**: Sistema de semáforo para lotes vencidos o por vencer (<30 días) con etiquetas de lenguaje natural.
*   **Exportación Profesional**: Generación de reportes en formato **Excel (.xlsx)** utilizando `ExcelJS` directamente en el cliente.
*   **Validaciones Robustas**: Prevención de duplicados, control de stock mínimo y validación de salidas contra stock real.
*   **Búsqueda Avanzada**: Filtrado multidimensional por nombre, categoría y estado (Activo/Inactivo).

## 🛠️ Tecnologías
*   **Backend**: Java 21, Spring Boot 3.2.x, JPA / Hibernate.
*   **Frontend**: Thymeleaf, Bootstrap 5, Bootstrap Icons.
*   **Base de Datos**: MySQL 8.0.
*   **Librerías**: ExcelJS, FileSaver.js.

## 📦 Instalación y Despliegue

1.  Clonar el repositorio: `git clone [url-del-repo]`
2.  Configurar la base de datos en `src/main/resources/application.properties`.
3.  Ejecutar el script SQL inicial para crear las tablas.
4.  Correr con Maven: `./mvnw spring-boot:run`

## 👥 Colaboración
El sistema está diseñado para ser desplegado en un servidor central (ej. Oracle Ampere), permitiendo que múltiples usuarios interactúen con la misma base de datos en tiempo real a través de la web.

---
**Desarrollado para el curso de Análisis y Diseño de Sistemas de Información.**
