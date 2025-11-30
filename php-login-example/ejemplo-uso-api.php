<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ejemplo de Uso de API - Comidas Regionales</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #f5f5f5;
        }

        .navbar {
            background: #4a3728;
            color: white;
            padding: 15px 30px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .container {
            max-width: 1200px;
            margin: 40px auto;
            padding: 0 20px;
        }

        .card {
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            margin-bottom: 30px;
        }

        .card h2 {
            color: #4a3728;
            margin-bottom: 20px;
        }

        .btn {
            background: #d2691e;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            margin-right: 10px;
            margin-bottom: 10px;
        }

        .btn:hover {
            background: #b8591a;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background: #4a3728;
            color: white;
        }

        .loading {
            text-align: center;
            padding: 20px;
            color: #666;
        }

        .error {
            background: #fee;
            color: #c33;
            padding: 15px;
            border-radius: 5px;
            margin-top: 10px;
        }

        .success {
            background: #efe;
            color: #3c3;
            padding: 15px;
            border-radius: 5px;
            margin-top: 10px;
        }
    </style>
</head>
<body>
    <nav class="navbar">
        <h1>Ejemplo de Uso de API</h1>
        <button class="btn" onclick="window.location.href='dashboard.php'">Volver al Dashboard</button>
    </nav>

    <div class="container">
        <!-- Sección de Usuarios -->
        <div class="card">
            <h2>Usuarios</h2>
            <button class="btn" onclick="loadUsuarios()">Cargar Usuarios</button>
            <div id="usuariosContent"></div>
        </div>

        <!-- Sección de Categorías -->
        <div class="card">
            <h2>Categorías</h2>
            <button class="btn" onclick="loadCategorias()">Cargar Categorías</button>
            <div id="categoriasContent"></div>
        </div>

        <!-- Sección de Platos -->
        <div class="card">
            <h2>Platos</h2>
            <button class="btn" onclick="loadPlatos()">Cargar Platos</button>
            <div id="platosContent"></div>
        </div>

        <!-- Sección de Restaurantes -->
        <div class="card">
            <h2>Restaurantes</h2>
            <button class="btn" onclick="loadRestaurantes()">Cargar Restaurantes</button>
            <div id="restaurantesContent"></div>
        </div>
    </div>

    <!-- Incluir el helper de API -->
    <script src="api-helper.js"></script>

    <script>
        // Verificar autenticación
        window.addEventListener('load', () => {
            const token = localStorage.getItem('authToken');
            if (!token) {
                window.location.href = 'login.php';
            }
        });

        // Función para cargar usuarios
        async function loadUsuarios() {
            const content = document.getElementById('usuariosContent');
            content.innerHTML = '<div class="loading">Cargando usuarios...</div>';

            try {
                const usuarios = await API.usuarios.getAll();
                
                if (usuarios.length === 0) {
                    content.innerHTML = '<p>No hay usuarios registrados.</p>';
                    return;
                }

                let html = '<table><thead><tr>';
                html += '<th>ID</th><th>Nombre</th><th>Apellido</th><th>Usuario</th><th>DNI</th><th>Estado</th>';
                html += '</tr></thead><tbody>';

                usuarios.forEach(usuario => {
                    html += '<tr>';
                    html += `<td>${usuario.idUsuario}</td>`;
                    html += `<td>${usuario.nombreUsuario}</td>`;
                    html += `<td>${usuario.apellidoUsuario}</td>`;
                    html += `<td>${usuario.nombreUsuarioLogin}</td>`;
                    html += `<td>${usuario.dniUsuario}</td>`;
                    html += `<td>${usuario.estado ? 'Activo' : 'Inactivo'}</td>`;
                    html += '</tr>';
                });

                html += '</tbody></table>';
                content.innerHTML = html;
            } catch (error) {
                content.innerHTML = `<div class="error">Error al cargar usuarios: ${error.message}</div>`;
            }
        }

        // Función para cargar categorías
        async function loadCategorias() {
            const content = document.getElementById('categoriasContent');
            content.innerHTML = '<div class="loading">Cargando categorías...</div>';

            try {
                const categorias = await API.categorias.getAll();
                
                if (categorias.length === 0) {
                    content.innerHTML = '<p>No hay categorías registradas.</p>';
                    return;
                }

                let html = '<table><thead><tr>';
                html += '<th>ID</th><th>Nombre</th><th>Descripción</th><th>Restaurante ID</th><th>Estado</th>';
                html += '</tr></thead><tbody>';

                categorias.forEach(categoria => {
                    html += '<tr>';
                    html += `<td>${categoria.idCategoria}</td>`;
                    html += `<td>${categoria.nombreCategoria}</td>`;
                    html += `<td>${categoria.descripcionCategoria || 'N/A'}</td>`;
                    html += `<td>${categoria.idRestaurante}</td>`;
                    html += `<td>${categoria.estado ? 'Activo' : 'Inactivo'}</td>`;
                    html += '</tr>';
                });

                html += '</tbody></table>';
                content.innerHTML = html;
            } catch (error) {
                content.innerHTML = `<div class="error">Error al cargar categorías: ${error.message}</div>`;
            }
        }

        // Función para cargar platos
        async function loadPlatos() {
            const content = document.getElementById('platosContent');
            content.innerHTML = '<div class="loading">Cargando platos...</div>';

            try {
                const platos = await API.platos.getAll();
                
                if (platos.length === 0) {
                    content.innerHTML = '<p>No hay platos registrados.</p>';
                    return;
                }

                let html = '<table><thead><tr>';
                html += '<th>ID</th><th>Nombre</th><th>Descripción</th><th>Precio</th><th>Categoría ID</th><th>Estado</th>';
                html += '</tr></thead><tbody>';

                platos.forEach(plato => {
                    html += '<tr>';
                    html += `<td>${plato.idPlato}</td>`;
                    html += `<td>${plato.nombrePlato}</td>`;
                    html += `<td>${plato.descripcionPlato || 'N/A'}</td>`;
                    html += `<td>S/ ${plato.precioPlato.toFixed(2)}</td>`;
                    html += `<td>${plato.idCategoria}</td>`;
                    html += `<td>${plato.estado ? 'Activo' : 'Inactivo'}</td>`;
                    html += '</tr>';
                });

                html += '</tbody></table>';
                content.innerHTML = html;
            } catch (error) {
                content.innerHTML = `<div class="error">Error al cargar platos: ${error.message}</div>`;
            }
        }

        // Función para cargar restaurantes
        async function loadRestaurantes() {
            const content = document.getElementById('restaurantesContent');
            content.innerHTML = '<div class="loading">Cargando restaurantes...</div>';

            try {
                const restaurantes = await API.restaurantes.getAll();
                
                if (restaurantes.length === 0) {
                    content.innerHTML = '<p>No hay restaurantes registrados.</p>';
                    return;
                }

                let html = '<table><thead><tr>';
                html += '<th>ID</th><th>Nombre</th><th>Dirección</th><th>Teléfono</th><th>Estado</th>';
                html += '</tr></thead><tbody>';

                restaurantes.forEach(restaurante => {
                    html += '<tr>';
                    html += `<td>${restaurante.idRestaurante}</td>`;
                    html += `<td>${restaurante.nombreRestaurante}</td>`;
                    html += `<td>${restaurante.direccionRestaurante || 'N/A'}</td>`;
                    html += `<td>${restaurante.telefonoRestaurante || 'N/A'}</td>`;
                    html += `<td>${restaurante.estado ? 'Activo' : 'Inactivo'}</td>`;
                    html += '</tr>';
                });

                html += '</tbody></table>';
                content.innerHTML = html;
            } catch (error) {
                content.innerHTML = `<div class="error">Error al cargar restaurantes: ${error.message}</div>`;
            }
        }
    </script>
</body>
</html>
