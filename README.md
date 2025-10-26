# 📘 MiAppListsIDNP

## 🧩 Descripción general

**MiAppListsIDNP** es una aplicación sencilla en **Kotlin** desarrollada con **Jetpack Compose**, cuyo propósito es manejar dinámicamente una lista de elementos (como cursos o identificadores) desde una interfaz de usuario reactiva.  
La app permite **agregar** o **modificar** elementos en una lista, mostrando de forma inmediata los cambios en la pantalla gracias al uso de **`mutableStateListOf`**.

---

## ⚙️ Funcionamiento general del código base

En la versión base, la aplicación utiliza una estructura simple que combina campos de texto (`TextField`), un botón (`Button`), y una lista mostrada con `LazyColumn`.

### Ejemplo de estructura base

```kotlin
@Composable
fun ListApp() {
    var id by remember { mutableStateOf("") }
    var curso by remember { mutableStateOf("") }
    val items = remember { mutableStateListOf<Item>() }

    Column {
        OutlinedTextField(value = id, onValueChange = { id = it }, label = { Text("ID") })
        OutlinedTextField(value = curso, onValueChange = { curso = it }, label = { Text("Curso") })

        Button(onClick = {
            if (id.isNotBlank() && curso.isNotBlank()) {
                val index = items.indexOfFirst { it.id == id }
                if (index != -1) {
                    items[index] = Item(id, curso)
                } else {
                    items.add(Item(id, curso))
                }
                id = ""
                curso = ""
            }
        }) {
            Text("Guardar")
        }

        LazyColumn {
            items(items) { item ->
                Text("${item.id}: ${item.nombre}")
            }
        }
    }
}
```
## 🧠 Explicación detallada del flujo del bloque if

El corazón de la lógica se encuentra en esta parte:

```kotlin
if (id.isNotBlank() && curso.isNotBlank()) {
    // Busca el índice del item
    val index = items.indexOfFirst { it.id == id }
    if (index != -1) {
        // Modifica directamente en la lista
        items[index] = Item(id, curso)
    } else {
        // Agrega nuevo item
        items.add(Item(id, curso))
    }
    id = ""
    curso = ""
}
```
## 🔍 Paso a paso:

Condición principal

```kotlin
if (id.isNotBlank() && curso.isNotBlank())
```
Esta condición asegura que ambos campos (`id` y `curso`) no estén vacíos antes de procesar la acción.
* `isNotBlank()` devuelve true solo si la cadena no está vacía ni compuesta únicamente por espacios.
* Así se evita intentar guardar datos incompletos o inválidos.

### 💡 Importancia: previene errores de lógica y mantiene la integridad de los datos ingresados.

Búsqueda del elemento existente
```kotlin
val index = items.indexOfFirst { it.id == id }
```
Aquí se busca en la lista items un elemento cuyo identificador (`id`) coincida con el ingresado.
* Si lo encuentra, devuelve su posición en la lista (índice).
* Si no lo encuentra, devuelve `-1`.

### 💡 Esto permite saber si el elemento debe modificarse o agregarse.

Condición secundaria: modificar o agregar
```kotlin
if (index != -1) {
    items[index] = Item(id, curso)
} else {
    items.add(Item(id, curso))
}
```
* Si index `!= -1`: el elemento ya existe, entonces se reemplaza su valor directamente dentro de la lista.
* Si no existe `(index == -1)`: se agrega un nuevo item al final de la lista.

### 💡 Gracias a que items es un `mutableStateListOf`, cualquier cambio (modificar o agregar) se refleja automáticamente en la UI.

Reinicio de los campos:
```kotlin
id = ""
curso = ""
```
Una vez completada la acción, los campos de texto se vacían, dejando la interfaz lista para una nueva entrada.
* Esto mejora la experiencia del usuario, evitando que repita manualmente el borrado de texto.

## ⚛️ Rol de mutableStateListOf
La lista se declara así:
```kotlin
val items = remember { mutableStateListOf<Item>() }
```
* mutableStateListOf es una lista observable por Jetpack Compose.
* Cada vez que se modifica su contenido (añadir, eliminar o reemplazar), la interfaz se recompone automáticamente.
* No se requiere notificación manual ni `notifyDataSetChanged()` como en Android clásico.

### 💡 En otras palabras, cualquier cambio en items se refleja en pantalla al instante.
## 📊 Comparativa: código base vs versión mejorada

| Aspecto | Código Base | Código Mejorado |
|---------|-------------|-----------------|
| Lista | `mutableListOf()` | `mutableStateListOf()` |
| Actualización UI | No reactiva | Automáticamente reactiva |
| Validación de campos | No validaba entradas | Usa `if (id.isNotBlank() && curso.isNotBlank())` |
| Flujo de actualización | Solo agregaba | Agrega o modifica según ID |
| Reinicio de campos | Manual o ausente | Automático tras guardar |

## 💬 Ejemplo de flujo de interacción

- El usuario escribe `id = "1"` y `curso = "Android"`
- Presiona **Guardar** → se agrega un nuevo item
- Luego escribe el mismo `id = "1"` y cambia `curso = "Kotlin"`
- Presiona **Guardar** nuevamente → el elemento se modifica, no se duplica
- La lista se actualiza en tiempo real mostrando el nuevo valor

## 🧠 Conclusión

La estructura `if (id.isNotBlank() && curso.isNotBlank())` actúa como una capa de validación lógica y funcional, asegurando que solo se procesen entradas válidas y controlando el flujo entre agregar y modificar elementos.

Combinado con `mutableStateListOf`, se logra un comportamiento reactivo, limpio y seguro, garantizando que los datos y la interfaz siempre estén sincronizados.

**Autor:** Joshep Antony Ccahuana Larota
