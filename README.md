# Task Tracker CLI

## Pequeña aplicación de línea de comandos hecha en Java para gestionar tareas.

Es un proyecto simple para trabajar con:

- Programación orientada a objetos
- Manejo de archivos
- Uso de CLI (Command Line Interface)

### Funcionalidades

- Crear una tarea

```bash
java -cp out TaskCLI add "Descripción de la tarea"
```

- Listar todas las tareas

```bash
java -cp out TaskCLI list
```

- Marcar tarea como completada

```bash
java -cp out TaskCLI mark-done ID
```

- Marcar tarea como en progreso

```bash
java -cp out TaskCLI mark-in-progress ID
```

- Eliminar tarea

```bash
java -cp out TaskCLI delete ID
```

- Listar tareas en progreso

```bash
java -cp out TaskCLI list in-progress
```

- Listar tareas completadas

```bash
java -cp out TaskCLI list done
```

### Tecnologías

- Java

### Cómo ejecutar el proyecto

- Compilar:

```bash
javac -d out src/*.java
```

- Ejecutar:

```bash
java -cp out TaskCLI
```

- Objetivo del proyecto

Implementar una aplicación CLI para la gestión de tareas utilizando Java.

https://roadmap.sh/projects/task-tracker
