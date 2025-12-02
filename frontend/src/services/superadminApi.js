import axiosInstance from '../config/axios';

const api = axiosInstance;

// ============================================
// AUTENTICACIÓN SUPERADMIN
// ============================================
export const superadminAuthAPI = {
    loginWithToken: (token) => api.post('/restful/superadmin/login', { token }),
    getEstadisticas: () => api.get('/restful/superadmin/estadisticas'),
};

// ============================================
// GESTIÓN DE ROLES (tabla perfil)
// ============================================
export const rolesAPI = {
    getAll: () => api.get('/restful/superadmin/roles'),
    getById: (id) => api.get(`/restful/superadmin/roles/${id}`),
    create: (data) => api.post('/restful/superadmin/roles', data),
    update: (id, data) => api.put(`/restful/superadmin/roles/${id}`, data),
    delete: (id) => api.delete(`/restful/superadmin/roles/${id}`),
    getPermisos: (id) => api.get(`/restful/superadmin/roles/${id}/permisos`),
    assignPermisos: (id, idsModulos) => api.post(`/restful/superadmin/roles/${id}/permisos`, idsModulos),
};

// ============================================
// GESTIÓN DE PERMISOS (tabla modulo)
// ============================================
export const permisosAPI = {
    getAll: () => api.get('/restful/superadmin/permisos'),
    getById: (id) => api.get(`/restful/superadmin/permisos/${id}`),
    create: (data) => api.post('/restful/superadmin/permisos', data),
    update: (id, data) => api.put(`/restful/superadmin/permisos/${id}`, data),
    delete: (id) => api.delete(`/restful/superadmin/permisos/${id}`),
};

// ============================================
// GESTIÓN DE RESTAURANTES
// ============================================
export const restaurantesAPI = {
    getAll: () => api.get('/restful/superadmin/restaurantes'),
    getById: (id) => api.get(`/restful/superadmin/restaurantes/${id}`),
    create: (data) => api.post('/restful/superadmin/restaurantes', data),
    update: (id, data) => api.put(`/restful/superadmin/restaurantes/${id}`, data),
    delete: (id) => api.delete(`/restful/superadmin/restaurantes/${id}`),
};

// ============================================
// GESTIÓN DE USUARIOS
// ============================================
export const usuariosAPI = {
    getUsuarios: async () => {
        const response = await api.get('/restful/superadmin/usuarios');
        return response.data;
    },
    createUsuario: async (usuario) => {
        const response = await api.post('/restful/superadmin/usuarios', usuario);
        return response.data;
    },
    updateUsuario: async (usuario) => {
        const response = await api.put(`/restful/superadmin/usuarios/${usuario.idUsuario}`, usuario);
        return response.data;
    },
    deleteUsuario: async (id) => {
        await api.delete(`/restful/superadmin/usuarios/${id}`);
    },
};

// ============================================
// API UNIFICADA (superadminApi)
// ============================================
// Se exporta un objeto unificado para facilitar imports, 
// pero se recomienda usar los exports nombrados si es posible.
// NOTA: Para métodos nuevos (usuarios, estadisticas dashboard), devolvemos data directa.
// Para métodos legacy (roles, permisos), devolvemos response de axios para no romper código existente.

export const superadminApi = {
    // Auth & Dashboard
    loginWithToken: superadminAuthAPI.loginWithToken,
    getEstadisticas: async () => {
        const response = await superadminAuthAPI.getEstadisticas();
        return response.data;
    },

    // Roles (Legacy: returns Axios Response)
    getRoles: rolesAPI.getAll,

    // Permisos (Legacy: returns Axios Response)
    getPermisos: permisosAPI.getAll,

    // Restaurantes (Legacy: returns Axios Response)
    getRestaurantes: restaurantesAPI.getAll,
    createRestaurante: restaurantesAPI.create,

    // Usuarios (New: returns Data)
    getUsuarios: usuariosAPI.getUsuarios,
    createUsuario: usuariosAPI.createUsuario,
    updateUsuario: usuariosAPI.updateUsuario,
    deleteUsuario: usuariosAPI.deleteUsuario,
};