import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import { ProtectedRoute } from './components/ProtectedRoute';
import { LoginPage } from './pages/LoginPage';
import { DashboardPage } from './pages/DashboardPage';
import { FlatDetailPage } from './pages/FlatDetailPage'; // Импортируем карточку
import { MainLayout } from "./pages/MainLayout.jsx";

function App() {
    return (
        <AuthProvider>
            <BrowserRouter>
                <Routes>
                    <Route path="/login" element={<LoginPage />} />

                    <Route element={<ProtectedRoute />}>
                        <Route element={<MainLayout />}>
                            <Route path="/main" element={<DashboardPage />} />

                            <Route path="/flats/:id" element={<FlatDetailPage />} />
                        </Route>
                    </Route>

                    <Route path="/" element={<Navigate to="/main" replace />} />
                    <Route path="*" element={<Navigate to="/main" replace />} />
                </Routes>
            </BrowserRouter>
        </AuthProvider>
    );
}

export default App;