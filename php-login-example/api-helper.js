// api-helper.js - Utilidades para hacer peticiones autenticadas a la API

const API_URL = 'http://localhost:8080';

/**
 * Función para hacer peticiones autenticadas a la API
 * @param {string} endpoint - Endpoint de la API (ej: '/restful/usuarios')
 * @param {object} options - Opciones de fetch (method, body, etc.)
 * @returns {Promise} - Promesa con la respuesta
 */
async function apiRequest(endpoint, options = {}) {
    // Obtener token del localStorage
    const token = localStorage.getItem('authToken');

    if (!token) {
        throw new Error('No hay sesión activa');
    }

    // Configurar headers por defecto
    const headers = {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
        ...options.headers
    };

    // Hacer la petición
    const response = await fetch(API_URL + endpoint, {
        ...options,
        headers
    });

    // Si el token expiró o es inválido, redirigir al login
    if (response.status === 401 || response.status === 403) {
        localStorage.removeItem('authToken');
        localStorage.removeItem('user');
        window.location.href = 'login.php';
        throw new Error('Sesión expirada');
    }

    // Si hay error, lanzar excepción
    if (!response.ok) {
        const error = await response.text();
        throw new Error(error || 'Error en la petición');
    }

    // Retornar datos
    return await response.json();
}

/**
 * Métodos de API específicos
 */
const API = {
    // Usuarios
    usuarios: {
        getAll: () => apiRequest('/restful/usuarios'),
        getById: (id) => apiRequest(`/restful/usuarios/${id}`),
        create: (data) => apiRequest('/restful/usuarios', {
            method: 'POST',
            body: JSON.stringify(data)
        }),
        update: (id, data) => apiRequest(`/restful/usuarios/${id}`, {
            method: 'PUT',
            body: JSON.stringify(data)
        }),
        delete: (id) => apiRequest(`/restful/usuarios/${id}`, {
            method: 'DELETE'
        })
    },

    // Categorías
    categorias: {
        getAll: () => apiRequest('/restful/categorias'),
        getById: (id) => apiRequest(`/restful/categorias/${id}`),
        create: (data) => apiRequest('/restful/categorias', {
            method: 'POST',
            body: JSON.stringify(data)
        }),
        update: (id, data) => apiRequest(`/restful/categorias/${id}`, {
            method: 'PUT',
            body: JSON.stringify(data)
        }),
        delete: (id) => apiRequest(`/restful/categorias/${id}`, {
            method: 'DELETE'
        })
    },

    // Platos
    platos: {
        getAll: () => apiRequest('/restful/platos'),
        getById: (id) => apiRequest(`/restful/platos/${id}`),
        create: (data) => apiRequest('/restful/platos', {
            method: 'POST',
            body: JSON.stringify(data)
        }),
        update: (id, data) => apiRequest(`/restful/platos/${id}`, {
            method: 'PUT',
            body: JSON.stringify(data)
        }),
        delete: (id) => apiRequest(`/restful/platos/${id}`, {
            method: 'DELETE'
        })
    },

    // Restaurantes
    restaurantes: {
        getAll: () => apiRequest('/restful/restaurantes'),
        getById: (id) => apiRequest(`/restful/restaurantes/${id}`),
        create: (data) => apiRequest('/restful/restaurantes', {
            method: 'POST',
            body: JSON.stringify(data)
        }),
        update: (id, data) => apiRequest(`/restful/restaurantes/${id}`, {
            method: 'PUT',
            body: JSON.stringify(data)
        }),
        delete: (id) => apiRequest(`/restful/restaurantes/${id}`, {
            method: 'DELETE'
        })
    }
};

// Ejemplo de uso:
/*
// Obtener todos los usuarios
API.usuarios.getAll()
    .then(usuarios => {
        console.log('Usuarios:', usuarios);
    })
    .catch(error => {
        console.error('Error:', error);
    });

// Crear un nuevo usuario
API.usuarios.create({
    nombreUsuario: 'Juan',
    apellidoUsuario: 'Pérez',
    nombreUsuarioLogin: 'jperez',
    contrasena: '12345678',
    dniUsuario: '12345678',
    telefonoUsuario: '987654321',
    idPerfil: 1,
    idSucursal: 1,
    estado: true
})
    .then(usuario => {
        console.log('Usuario creado:', usuario);
    })
    .catch(error => {
        console.error('Error:', error);
    });
*/
