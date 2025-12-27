import React, { useState, useEffect } from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import StartPage from "./components/StartPage/StartPage";
import {MainPage} from "./components/MainPage/MainPage";
import "primereact/resources/themes/lara-light-purple/theme.css";
import "primereact/resources/primereact.min.css";
import "primeicons/primeicons.css";

function App() {
    const [token, setToken] = useState(localStorage.getItem("token"));

    const handleLogin = (newToken) => {
        localStorage.setItem("token", newToken.token);
        setToken(newToken.token);
    };

    const handleLogout = () => {
        localStorage.removeItem("token");
        setToken(null);
    };

    return (
        <Router>
            <Routes>
                <Route
                    path="/"
                    element={token ? <Navigate to="/main" /> : <StartPage onLogin={handleLogin} />}
                />
                <Route
                    path="/main"
                    element={token ? <MainPage onLogout={handleLogout} /> : <Navigate to="/" />}
                />
                <Route path="*" element={<Navigate to="/" />} />
            </Routes>
        </Router>
    );
}

export default App;

