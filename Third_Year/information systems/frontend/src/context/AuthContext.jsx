import React, { createContext, useContext, useState } from 'react';
import api from '../api/axios.js';

const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
    const [token, setToken] = useState(() => localStorage.getItem('accessToken'));


    const authenticate = async (mode, username, password) => {
        try {
            const response = await api.post(`/auth/${mode}`, { username, password });

            const token = response.data?.token;

            if (token) {
                localStorage.setItem('accessToken', token);
                setToken(token);
            }
        } catch (error) {
            const fallbackText = mode === 'login' ? 'Ошибка входа' : 'Ошибка регистрации';
            const errorMessage = error.response?.data?.errorMessage || error.response?.data?.message || fallbackText;

            throw new Error(errorMessage);
        }
    };

    const logout = () => {
        localStorage.removeItem('accessToken');
        setToken(null);
    };

    const isAuthenticated = !!token;

    return (
        <AuthContext.Provider value={{ token, authenticate, logout, isAuthenticated }}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error('useAuth должен использоваться внутри AuthProvider');
    }
    return context;
};