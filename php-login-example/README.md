# Login PHP para Backend Spring Boot - Comidas Regionales

Este directorio contiene ejemplos de código PHP/JavaScript para que tus compañeros puedan conectarse a tu backend de Spring Boot.

## 📁 Archivos Incluidos

- **`login.php`** - Página de login con diseño moderno
- **`dashboard.php`** - Página de dashboard que muestra información del usuario
- **`api-helper.js`** - Utilidades para hacer peticiones autenticadas a la API

## 🚀 Cómo Usar

### 1. Configuración del Backend

Asegúrate de que tu backend de Spring Boot esté corriendo en:
```
http://localhost:8080
```

### 2. Configurar CORS (si no está configurado)

Tu backend debe permitir peticiones desde el origen donde correrá el PHP. Si usas XAMPP o similar, asegúrate de que el CORS esté configurado en `WebConfig.java`.

### 3. Ejecutar los archivos PHP

Puedes usar cualquiera de estas opciones:

#### Opción A: XAMPP/WAMP
1. Copia la carpeta `php-login-example` a `htdocs` (XAMPP) o `www` (WAMP)
2. Abre el navegador en: `http://localhost/php-login-example/login.php`

#### Opción B: Servidor PHP integrado
```bash
cd php-login-example
php -S localhost:3000
```
Luego abre: `http://localhost:3000/login.php`

## 🔐 Credenciales de Prueba

Usa las credenciales que tengas configuradas en tu base de datos MySQL. Por ejemplo:
- **Usuario**: `EfrainDs3` o `Joy`
- **Contraseña**: `12345678`

## 📡 Endpoints de la API

El login se conecta al endpoint:
```
POST http://localhost:8080/restful/usuarios/login
```

**Body de la petición:**
```json
{
  "nombreUsuarioLogin": "usuario",
  "contrasena": "contraseña"
}
```

**Respuesta esperada:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "idUsuario": 1,
  "nombreUsuario": "Juan",
  "apellidoUsuario": "Pérez",
  "nombreUsuarioLogin": "jperez",
  "dniUsuario": "12345678",
  "telefonoUsuario": "987654321",
  "estado": true,
  "idPerfil": 1,
  "idSucursal": 1
}
```

## 🛠️ Uso de api-helper.js

Para hacer peticiones autenticadas a otros endpoints, incluye `api-helper.js` en tu página:

```html
<script src="api-helper.js"></script>
<script>
    // Obtener todos los usuarios
    API.usuarios.getAll()
        .then(usuarios => {
            console.log('Usuarios:', usuarios);
        })
        .catch(error => {
            console.error('Error:', error);
        });

    // Obtener todas las categorías
    API.categorias.getAll()
        .then(categorias => {
            console.log('Categorías:', categorias);
        });

    // Crear un nuevo plato
    API.platos.create({
        nombrePlato: 'Ceviche',
        descripcionPlato: 'Plato típico peruano',
        precioPlato: 25.00,
        idCategoria: 1,
        estado: true
    })
        .then(plato => {
            console.log('Plato creado:', plato);
        });
</script>
```

## 🔑 Manejo de Autenticación

El token JWT se guarda automáticamente en `localStorage` después del login:
- **Token**: `localStorage.getItem('authToken')`
- **Usuario**: `localStorage.getItem('user')`

Todas las peticiones autenticadas incluyen el header:
```
Authorization: Bearer <token>
```

## 🚨 Manejo de Errores

Si el token expira o es inválido (error 401/403), el usuario será redirigido automáticamente al login.

## 📝 Endpoints Disponibles

### Usuarios
- `GET /restful/usuarios` - Listar todos
- `GET /restful/usuarios/{id}` - Obtener por ID
- `POST /restful/usuarios` - Crear
- `PUT /restful/usuarios/{id}` - Actualizar
- `DELETE /restful/usuarios/{id}` - Eliminar

### Categorías
- `GET /restful/categorias` - Listar todas
- `GET /restful/categorias/{id}` - Obtener por ID
- `POST /restful/categorias` - Crear
- `PUT /restful/categorias/{id}` - Actualizar
- `DELETE /restful/categorias/{id}` - Eliminar

### Platos
- `GET /restful/platos` - Listar todos
- `GET /restful/platos/{id}` - Obtener por ID
- `POST /restful/platos` - Crear
- `PUT /restful/platos/{id}` - Actualizar
- `DELETE /restful/platos/{id}` - Eliminar

### Restaurantes
- `GET /restful/restaurantes` - Listar todos
- `GET /restful/restaurantes/{id}` - Obtener por ID
- `POST /restful/restaurantes` - Crear
- `PUT /restful/restaurantes/{id}` - Actualizar
- `DELETE /restful/restaurantes/{id}` - Eliminar

## 🎨 Personalización

Puedes modificar los estilos CSS en `login.php` y `dashboard.php` para que coincidan con el diseño de tu proyecto.

## ⚠️ Notas Importantes

1. **CORS**: Asegúrate de que tu backend permita peticiones desde el origen donde corre el PHP
2. **HTTPS**: En producción, usa HTTPS para todas las peticiones
3. **Seguridad**: Nunca expongas credenciales en el código del cliente
4. **Token**: El token se guarda en localStorage, considera usar httpOnly cookies en producción

## 🤝 Compartir con Compañeros

Para compartir estos archivos con tus compañeros:

1. Comparte toda la carpeta `php-login-example`
2. Indícales la URL de tu backend: `http://localhost:8080`
3. Proporciona credenciales de prueba
4. Comparte este README para que sepan cómo usarlo

## 📞 Soporte

Si tus compañeros tienen problemas:
1. Verificar que el backend esté corriendo
2. Verificar que el CORS esté configurado
3. Revisar la consola del navegador (F12) para ver errores
4. Verificar que las credenciales sean correctas
