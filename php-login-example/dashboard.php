<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - Comidas Regionales</title>
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
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        .navbar h1 {
            font-size: 24px;
        }

        .user-info {
            display: flex;
            align-items: center;
            gap: 20px;
        }

        .btn-logout {
            background: #d2691e;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            font-weight: 600;
        }

        .btn-logout:hover {
            background: #b8591a;
        }

        .container {
            max-width: 1200px;
            margin: 40px auto;
            padding: 0 20px;
        }

        .welcome-card {
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            margin-bottom: 30px;
        }

        .welcome-card h2 {
            color: #4a3728;
            margin-bottom: 10px;
        }

        .data-section {
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        .data-section h3 {
            color: #4a3728;
            margin-bottom: 20px;
        }

        .user-details {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin-top: 20px;
        }

        .detail-item {
            padding: 15px;
            background: #f9f9f9;
            border-radius: 8px;
            border-left: 4px solid #d2691e;
        }

        .detail-item label {
            font-weight: 600;
            color: #666;
            font-size: 12px;
            text-transform: uppercase;
            display: block;
            margin-bottom: 5px;
        }

        .detail-item p {
            color: #333;
            font-size: 16px;
        }
    </style>
</head>
<body>
    <nav class="navbar">
        <h1>Comidas Regionales - Dashboard</h1>
        <div class="user-info">
            <span id="userName">Cargando...</span>
            <button class="btn-logout" onclick="logout()">Cerrar Sesión</button>
        </div>
    </nav>

    <div class="container">
        <div class="welcome-card">
            <h2>¡Bienvenido!</h2>
            <p>Has iniciado sesión correctamente en el sistema.</p>
        </div>

        <div class="data-section">
            <h3>Información del Usuario</h3>
            <div class="user-details" id="userDetails">
                <!-- Se llenará dinámicamente con JavaScript -->
            </div>
        </div>
    </div>

    <script>
        // Verificar autenticación al cargar la página
        window.addEventListener('load', () => {
            const token = localStorage.getItem('authToken');
            const user = localStorage.getItem('user');

            if (!token || !user) {
                // No hay sesión, redirigir al login
                window.location.href = 'login.php';
                return;
            }

            // Mostrar información del usuario
            displayUserInfo(JSON.parse(user));
        });

        // Función para mostrar información del usuario
        function displayUserInfo(userData) {
            // Actualizar nombre en navbar
            document.getElementById('userName').textContent = 
                userData.nombreUsuario || userData.nombreUsuarioLogin || 'Usuario';

            // Crear detalles del usuario
            const userDetailsDiv = document.getElementById('userDetails');
            
            const details = [
                { label: 'ID Usuario', value: userData.idUsuario || 'N/A' },
                { label: 'Nombre', value: userData.nombreUsuario || 'N/A' },
                { label: 'Apellido', value: userData.apellidoUsuario || 'N/A' },
                { label: 'Usuario Login', value: userData.nombreUsuarioLogin || 'N/A' },
                { label: 'DNI', value: userData.dniUsuario || 'N/A' },
                { label: 'Teléfono', value: userData.telefonoUsuario || 'N/A' },
                { label: 'Estado', value: userData.estado ? 'Activo' : 'Inactivo' },
                { label: 'Perfil ID', value: userData.idPerfil || 'N/A' },
            ];

            userDetailsDiv.innerHTML = details.map(detail => `
                <div class="detail-item">
                    <label>${detail.label}</label>
                    <p>${detail.value}</p>
                </div>
            `).join('');
        }

        // Función para cerrar sesión
        function logout() {
            // Limpiar localStorage
            localStorage.removeItem('authToken');
            localStorage.removeItem('user');

            // Redirigir al login
            window.location.href = 'login.php';
        }
    </script>
</body>
</html>
