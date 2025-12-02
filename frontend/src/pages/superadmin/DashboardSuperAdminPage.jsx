import React, { useState, useEffect } from 'react';
import { useQuery } from '@tanstack/react-query';
import {
    FaStore,
    FaUsers,
    FaUserShield,
    FaBoxOpen,
    FaExclamationTriangle,
    FaMoneyBillWave,
    FaRocket
} from 'react-icons/fa';
import {
    AreaChart,
    Area,
    XAxis,
    YAxis,
    CartesianGrid,
    Tooltip,
    ResponsiveContainer,
    PieChart,
    Pie,
    Cell,
    Legend
} from 'recharts';
import { superadminApi } from '../../services/superadminApi';
import { StatCard, ChartContainer } from '../../components/superadmin/DashboardComponents';

// Datos simulados para las gráficas (hasta que haya endpoint real)
const monthlyData = [
    { name: 'Ene', value: 80000 },
    { name: 'Feb', value: 85000 },
    { name: 'Mar', value: 92000 },
    { name: 'Abr', value: 98000 },
    { name: 'May', value: 105000 },
    { name: 'Jun', value: 112000 },
    { name: 'Jul', value: 118000 },
    { name: 'Ago', value: 125000 },
    { name: 'Sep', value: 132000 },
    { name: 'Oct', value: 138000 },
    { name: 'Nov', value: 142300 },
    { name: 'Dic', value: 148000 },
];

const planData = [
    { name: 'Starter', value: 30, color: '#3B82F6' },
    { name: 'Growth', value: 45, color: '#F59E0B' },
    { name: 'Scale', value: 15, color: '#10B981' },
    { name: 'Enterprise', value: 10, color: '#8B5CF6' },
];

const DashboardSuperAdminPage = () => {
    const [userData, setUserData] = useState(null);

    useEffect(() => {
        const user = JSON.parse(localStorage.getItem('user') || '{}');
        setUserData(user);
    }, []);

    const { data: stats, isLoading, error } = useQuery({
        queryKey: ['superadmin-stats'],
        queryFn: async () => {
            try {
                return await superadminApi.getEstadisticas();
            } catch (err) {
                console.error("Error cargando estadísticas:", err);
                // Retornar datos dummy si falla (para desarrollo)
                return {
                    totalRestaurantes: 58,
                    totalUsuarios: 1240,
                    totalPerfiles: 8,
                    totalModulos: 12
                };
            }
        }
    });

    if (isLoading) {
        return (
            <div className="flex justify-center items-center h-64">
                <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-red-600"></div>
            </div>
        );
    }

    return (
        <div className="space-y-8">
            {/* Header */}
            <div>
                <h1 className="text-2xl font-bold text-gray-900">Tablero corporativo</h1>
                <p className="text-gray-500 mt-1">
                    Supervisa la salud del ecosistema: seguridad, tiendas, suscripciones y soporte.
                </p>
            </div>

            {/* Alert Banner */}
            <div className="bg-red-50 border-l-4 border-red-500 p-4 rounded-r-lg">
                <div className="flex">
                    <div className="flex-shrink-0">
                        <FaExclamationTriangle className="h-5 w-5 text-red-500" />
                    </div>
                    <div className="ml-3">
                        <p className="text-sm text-red-700">
                            <span className="font-bold">Datos de referencia:</span> Estos insights utilizan datos simulados mientras se conectan los endpoints de facturación.
                        </p>
                    </div>
                </div>
            </div>

            {/* Stats Grid */}
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
                <StatCard
                    title="Tiendas activas"
                    value={stats?.totalRestaurantes || 0}
                    trend="+6 vs. mes anterior"
                    trendLabel="Operando actualmente"
                    icon={FaStore}
                    color="blue"
                />
                <StatCard
                    title="Nuevas en onboarding"
                    value="12"
                    trend="+3 respecto a meta"
                    trendLabel="Últimos 30 días"
                    icon={FaRocket}
                    color="green"
                />
                <StatCard
                    title="Facturación bruta"
                    value="S/ 142,300"
                    trend="+12.4%"
                    trendLabel="Mes en curso"
                    icon={FaMoneyBillWave}
                    color="green"
                />
                <StatCard
                    title="Tickets abiertos"
                    value="9"
                    trend="3 críticos"
                    trendLabel="Soporte prioritario"
                    icon={FaExclamationTriangle}
                    color="orange"
                />
            </div>

            {/* Charts Section */}
            <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
                {/* Area Chart */}
                <div className="lg:col-span-2">
                    <ChartContainer title="Facturación mensual confirmada">
                        <ResponsiveContainer width="100%" height="100%">
                            <AreaChart data={monthlyData} margin={{ top: 10, right: 30, left: 0, bottom: 0 }}>
                                <defs>
                                    <linearGradient id="colorValue" x1="0" y1="0" x2="0" y2="1">
                                        <stop offset="5%" stopColor="#3B82F6" stopOpacity={0.8} />
                                        <stop offset="95%" stopColor="#3B82F6" stopOpacity={0} />
                                    </linearGradient>
                                </defs>
                                <CartesianGrid strokeDasharray="3 3" vertical={false} stroke="#E5E7EB" />
                                <XAxis
                                    dataKey="name"
                                    axisLine={false}
                                    tickLine={false}
                                    tick={{ fill: '#6B7280', fontSize: 12 }}
                                    dy={10}
                                />
                                <YAxis
                                    axisLine={false}
                                    tickLine={false}
                                    tick={{ fill: '#6B7280', fontSize: 12 }}
                                />
                                <Tooltip
                                    contentStyle={{ borderRadius: '8px', border: 'none', boxShadow: '0 4px 6px -1px rgba(0, 0, 0, 0.1)' }}
                                />
                                <Area
                                    type="monotone"
                                    dataKey="value"
                                    stroke="#3B82F6"
                                    strokeWidth={3}
                                    fillOpacity={1}
                                    fill="url(#colorValue)"
                                />
                            </AreaChart>
                        </ResponsiveContainer>
                    </ChartContainer>
                </div>

                {/* Pie Chart */}
                <div>
                    <ChartContainer title="Distribución por planes">
                        <ResponsiveContainer width="100%" height="100%">
                            <PieChart>
                                <Pie
                                    data={planData}
                                    cx="50%"
                                    cy="50%"
                                    innerRadius={60}
                                    outerRadius={80}
                                    paddingAngle={5}
                                    dataKey="value"
                                >
                                    {planData.map((entry, index) => (
                                        <Cell key={`cell-${index}`} fill={entry.color} />
                                    ))}
                                </Pie>
                                <Tooltip />
                                <Legend
                                    layout="horizontal"
                                    verticalAlign="bottom"
                                    align="center"
                                    iconType="circle"
                                />
                            </PieChart>
                        </ResponsiveContainer>
                    </ChartContainer>
                </div>
            </div>

            {/* Activity Section */}
            <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
                <ChartContainer title="Tickets críticos en soporte">
                    <div className="space-y-4 overflow-y-auto h-full pr-2">
                        {[
                            { id: 'SUP-3412', title: 'La Panadería de Lucho', status: 'CRITICA', type: 'ABIERTO', due: 'vence: 20 Nov 08:00' },
                            { id: 'SUP-3405', title: 'Dulce Aroma Miraflores', status: 'ALTA', type: 'PENDIENTE', due: 'vence: 20 Nov 14:00' },
                            { id: 'SUP-3399', title: 'Bakery Express', status: 'ALTA', type: 'ABIERTO', due: 'vence: 21 Nov 10:00' },
                        ].map((ticket) => (
                            <div key={ticket.id} className="flex items-center justify-between p-3 hover:bg-gray-50 rounded-lg transition-colors border-b border-gray-100 last:border-0">
                                <div>
                                    <p className="text-sm font-medium text-gray-900">{ticket.id} - {ticket.title}</p>
                                    <p className="text-xs text-gray-500">{ticket.due}</p>
                                </div>
                                <div className="flex items-center space-x-2">
                                    <span className={cn(
                                        "text-[10px] font-bold px-2 py-1 rounded",
                                        ticket.status === 'CRITICA' ? "bg-red-100 text-red-700" : "bg-orange-100 text-orange-700"
                                    )}>
                                        {ticket.status}
                                    </span>
                                    <span className="text-[10px] font-medium text-gray-500 bg-gray-100 px-2 py-1 rounded">
                                        {ticket.type}
                                    </span>
                                </div>
                            </div>
                        ))}
                    </div>
                </ChartContainer>

                <ChartContainer title="Actividad sensible de seguridad">
                    <div className="space-y-4 overflow-y-auto h-full pr-2">
                        {[
                            { title: 'Bloqueo de IP', desc: 'Intento fallido de autenticación (5) bloqueado automáticamente.', time: '19 Nov 22:15', level: 'Medio', color: 'text-orange-600' },
                            { title: 'Token revocado', desc: 'Token de integración de tienda "Nueva Miga" revocado por caducidad.', time: '19 Nov 18:42', level: 'Bajo', color: 'text-blue-600' },
                            { title: 'Nuevo superadmin', desc: 'Se creó cuenta para soporte regional (andina@suite.pe).', time: '19 Nov 11:05', level: 'Alto', color: 'text-red-600' },
                        ].map((activity, i) => (
                            <div key={i} className="flex items-start justify-between p-3 hover:bg-gray-50 rounded-lg transition-colors border-b border-gray-100 last:border-0">
                                <div>
                                    <p className="text-sm font-medium text-gray-900">{activity.title}</p>
                                    <p className="text-xs text-gray-500 mt-0.5">{activity.desc}</p>
                                </div>
                                <div className="text-right flex-shrink-0 ml-4">
                                    <p className="text-xs text-gray-400">{activity.time}</p>
                                    <p className={cn("text-[10px] font-bold mt-1", activity.color)}>{activity.level}</p>
                                </div>
                            </div>
                        ))}
                    </div>
                </ChartContainer>
            </div>
        </div>
    );
};

// Helper function para clases condicionales (duplicada aquí por si no se exporta correctamente, aunque idealmente se importa)
function cn(...inputs) {
    return inputs.filter(Boolean).join(' ');
}

export default DashboardSuperAdminPage;
