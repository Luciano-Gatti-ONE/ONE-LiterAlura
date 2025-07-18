# 📚 LiterAlura

**LiterAlura** es una aplicación de consola desarrollada en Java que consume datos de una API externa para explorar información de libros y autores. El proyecto permite filtrar libros por idioma, ordenar por descargas, y visualizar estadísticas relevantes. Está diseñado con enfoque educativo, destacando buenas prácticas en programación orientada a objetos, uso de colecciones, manejo de excepciones y trabajo con JSON.

---

## 🚀 Descripción

✅ Permite consultar libros y autores utilizando datos del proyecto *Gutendex* a través de una API externa.  
📈 Filtra y ordena libros por idioma, cantidad de descargas, o autor.  
📚 Recupera información como título, idiomas disponibles, descargas y autor/es.  
📊 Muestra estadísticas sobre los libros obtenidos.  
🔄 El usuario puede interactuar mediante un menú simple y claro por consola.

---

## 🛠️ Características

- **Modular**: Separación clara entre modelo, servicio y presentación.
- **Robusta**: Manejo adecuado de errores, como datos faltantes o problemas en la conexión.
- **Funcional**: Utiliza programación funcional con *Streams* para ordenar, filtrar y resumir datos.
- **Escalable**: Preparada para agregar nuevas funciones como búsquedas personalizadas o integración con base de datos.
- **Documentada**: Código claro, con comentarios explicativos y uso de `@JsonAlias` para mapear JSON correctamente.

---

## ⚙️ Instalación y Uso

```bash
git clone https://github.com/<tu-usuario>/ONE-LiterAlura.git
cd literalura
mvn clean compile
```

Para ejecutar:

```bash
mvn exec:java -Dexec.mainClass="com.aluraCourse.literAlura.Principal"
```

Seguí las instrucciones en la consola para navegar entre las distintas opciones del programa.

---

## 🧩 Estructura del Proyecto

```
src/
 └─ main/java/com/aluraCourse/literAlura/
     ├── LiterAluraApplication.java 
     ├── console/
     │   └── Principal.java              # Clase principal con menú
     ├── model/
     │   ├── Data.java                   # Contenedor de resultados
     │   ├── DataBook.java               # DTO de libro desde la API
     │   ├── DataPerson.java             # DTO de autor desde la API
     │   └── entities/
     │       ├── Book.java               # Entidad libro
     │       ├── Author.java             # Entidad autor
     │       └── Person.java             # Clase abstracta base
     ├── client/
     │   └── GutendexClient.java         # Cliente para consumir la API
     ├── repository/
     │   ├── AuthorRepository.java       # Acceso a datos del autor
     │   └── BookRepository.java         # Acceso a datos del libro
     ├── service/
     │   ├── AuthorService.java          # Lógica de negocio de autores
     │   ├── BookService.java            # Lógica de negocio de libros
     │   └── StatisticsService.java      # Generación de estadísticas
     └── util/
         ├── DataConverter.java          # Conversión de datos
         └── IDataConverter.java         # Interfaz de utilidad
```

---

## 📚 Tecnologías usadas

- **Java 21**
- **HttpClient** – para llamadas HTTP
- **Jackson (ObjectMapper)** – para parseo de JSON (`@JsonAlias`, `@JsonIgnoreProperties`)
- **Maven** – para gestión de dependencias
- **POO & Streams API** – para manipulación eficiente de datos

---

## 🌐 Fuente de Datos

La aplicación consume información del proyecto **[Gutendex](https://gutendex.com)** a través de su API pública:

- Endpoint utilizado: `https://gutendex.com/books`

---

## ✍️ Autor

**Luciano Emmanuel Gatti Flekenstein**  
Aplicación creada como proyecto educativo para [Oracle Next Education (ONE)](https://www.oracle.com/ar/education/oracle-next-education/) y para demostrar buenas prácticas en consumo de APIs, estructura modular y procesamiento de datos en Java.

---

## 📄 Licencia

Proyecto de código abierto. ¡Sos libre de usarlo, estudiarlo y adaptarlo para tus propios fines! 😀
