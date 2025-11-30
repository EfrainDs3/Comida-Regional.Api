<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Comidas Regionales</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 20px;
        }

        .login-container {
            background: white;
            border-radius: 20px;
            box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
            overflow: hidden;
            max-width: 400px;
            width: 100%;
        }

        .login-header {
            background: #4a3728;
            color: #f5f5dc;
            padding: 40px 30px;
            text-align: center;
        }

        .login-header h1 {
            font-size: 28px;
            margin-bottom: 10px;
        }

        .login-header p {
            color: #d4c5b9;
            font-size: 14px;
        }

        .login-form {
            padding: 40px 30px;
        }

        .form-group {
            margin-bottom: 25px;
        }

        .form-group label {
            display: block;
            color: #4a3728;
            font-weight: 600;
            margin-bottom: 8px;
            font-size: 14px;
        }

        .form-group input {
            width: 100%;
            padding: 12px 15px;
            border: 2px solid #e0e0e0;
            border-radius: 8px;
            font-size: 14px;
            transition: all 0.3s;
        }

        .form-group input:focus {
            outline: none;
            border-color: #d2691e;
            box-shadow: 0 0 0 3px rgba(210, 105, 30, 0.1);
        }

        .btn-login {
            width: 100%;
            padding: 14px;
            background: #d2691e;
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s;
        }

        .btn-login:hover {
            background: #b8591a;
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(210, 105, 30, 0.3);
        }

        .btn-login:disabled {
            background: #ccc;
            cursor: not-allowed;
            transform: none;
        }

        .alert {
            padding: 12px 15px;
            border-radius: 8px;
            margin-bottom: 20px;
            font-size: 14px;
        }

        .alert-error {
            background: #fee;
            color: #c33;
            border: 1px solid #fcc;
        }

        .alert-success {
            background: #efe;
            color: #3c3;
            border: 1px solid #cfc;
        }

        .login-footer {
            background: #f9f9f9;
            padding: 20px;
            text-align: center;
            color: #666;
            font-size: 13px;
        }

        .loading {
            display: none;
            text-align: center;
            margin-top: 10px;
        }

        .loading.active {
            display: block;
        }
    </style>
</head>
<body>
    <div class="login-container">
        <div class="login-header">
            <h1>Comidas Regionales</h1>
            <p>Acceso Administrativo</p>
        </div>

        <div class="login-form">
            <div id="message"></div>
            
            <form id="loginForm">
                <div class="form-group">
                    <label for="username">Usuario</label>
                    <input 
                        type="text" 
                        id="username" 
                        name="username" 
                        placeholder="Ingrese su usuario"
                        required
                    >
                </div>

                <div class="form-group">
                    <label for="password">Contraseña</label>
                    <input 
                        type="password" 
                        id="password" 
                        name="password" 
                        placeholder="••••••••"
                        required
                    >
                </div>

                <button type="submit" class="btn-login" id="btnLogin">
                    Iniciar Sesión
                </button>

                <div class="loading" id="loading">
                    Iniciando sesión...
                </div>
            </form>
        </div>

        <div class="login-footer">
            ¿Olvidaste tu contraseña? Contacta al administrador.
        </div>
    </div>

    <script>
        // Configuración de la API
        const API_URL = 'http://localhost:8080';
        const LOGIN_ENDPOINT = '/restful/usuarios/login';

        // Elementos del DOM
        const loginForm = document.getElementById('loginForm');
        const btnLogin = document.getElementById('btnLogin');
        const loading = document.getElementById('loading');
        const messageDiv = document.getElementById('message');

        // Función para mostrar mensajes
        function showMessage(message, type = 'error') {
            messageDiv.innerHTML = `<div class="alert alert-${type}">${message}</div>`;
            setTimeout(() => {
                messageDiv.innerHTML = '';
            }, 5000);
        }

        // Función de login
        async function login(username, password) {
            try {
                // Deshabilitar botón y mostrar loading
                btnLogin.disabled = true;
                loading.classList.add('active');

                // Hacer petición al backend
                const response = await fetch(API_URL + LOGIN_ENDPOINT, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        nombreUsuarioLogin: username,
                        contrasena: password
                    })
                });

                // Verificar si la respuesta es exitosa
                if (!response.ok) {
                    throw new Error('Credenciales inválidas');
                }

                // Obtener datos de la respuesta
                const data = await response.json();

                // Guardar token y datos del usuario en localStorage
                localStorage.setItem('authToken', data.token);
                localStorage.setItem('user', JSON.stringify(data));

                // Mostrar mensaje de éxito
                showMessage('¡Bienvenido! Redirigiendo...', 'success');

                // Redirigir al dashboard después de 1 segundo
                setTimeout(() => {
                    window.location.href = 'dashboard.php';
                }, 1000);

            } catch (error) {
                console.error('Error de login:', error);
                showMessage(error.message || 'Error al iniciar sesión. Verifica tus credenciales.');
            } finally {
                // Rehabilitar botón y ocultar loading
                btnLogin.disabled = false;
                loading.classList.remove('active');
            }
        }

        // Event listener para el formulario
        loginForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            
            const username = document.getElementById('username').value;
            const password = document.getElementById('password').value;

            await login(username, password);
        });

        // Verificar si ya hay una sesión activa
        window.addEventListener('load', () => {
            const token = localStorage.getItem('authToken');
            if (token) {
                // Ya hay sesión, redirigir al dashboard
                window.location.href = 'dashboard.php';
            }
        });
    </script>
</body>
</html>
