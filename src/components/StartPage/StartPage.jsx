import React, {useRef, useState} from "react";
import { Card } from "primereact/card";
import { InputText } from "primereact/inputtext";
import { Password } from "primereact/password";
import { Button } from "primereact/button";
import { useNavigate } from "react-router-dom";
import {authRequest} from "../../restApi/authRequest";

import "../../styles/StartPageStyles.css";
import {Toast} from "primereact/toast";

export default function StartPage({ onLogin }) {
    const [mode, setMode] = useState("login"); // "login" | "register"
    const [login, setLogin] = useState("");
    const [password, setPassword] = useState("");
    const [password2, setPassword2] = useState("");

    const navigate = useNavigate();

    const toast = useRef(null);


    const handleSubmit = async (e) => {
        e.preventDefault();
        try {

            if (!login || login.trim() === ""){
                toast.current.show({
                    severity: "error",
                    summary: "Вы не ввели логин",
                    life: 3000
                });
                return;
            }

            if (!password || password.trim() === ""){
                toast.current.show({
                    severity: "error",
                    summary: "Вы не ввели пароль",
                    life: 3000
                });
                return;
            }
            if ((!password2 || password2.trim() === "" )&& mode==="register"){
                toast.current.show({
                    severity: "error",
                    summary: "Выведите пароль ещё раз, чтобы подтвердить его",
                    life: 3000
                });
                return;
            }

            if (password !== password2 && mode==="register"){
                toast.current.show({
                    severity: "error",
                    summary: "Пароли не совпадают!",
                    life: 3000
                });
                return;
            }

            const res = await authRequest(mode, login, password);
            if (!res) return;

            onLogin(res);
            navigate("/main");
        }
        catch (e) {
            toast.current.show({
                severity: "error",
                summary: "Ошибка при авторизации"
            })
        }
    };


    return (
        <div className="start-page">
            <Toast ref={toast}/>

                <div className="header">
                    <h2>ФИО: Федоров Дмитрий Александрович</h2>
                    <h3>Группа: P3206</h3>
                    <h3>Вариант: 91781</h3>
                </div>

            <Card className="start-card">
                <div className="switcher">
                    <Button
                        label="Вход"
                        className={mode === "login" ? "active-button" : "p-button-text"}
                        onClick={() => setMode("login")}
                    />
                    <Button
                        label="Регистрация"
                        className={mode === "register" ? "active-button" : "p-button-text"}
                        onClick={() => setMode("register")}
                    />
                </div>

                <form onSubmit={handleSubmit} className="form">
                    <label htmlFor="login">Логин:</label>
                        <InputText
                            id="login"
                            value={login}
                            onChange={(e) => setLogin(e.target.value)}
                            className="input"
                            placeholder="Введите логин"
                        />


                    <label htmlFor="password">Пароль:</label>
                        <Password
                            id="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            className="input"
                            placeholder="Введите пароль"
                            toggleMask
                        />



                    {mode === "register" && (
                        <div>
                            <Password
                                id="password2"
                                value={password2}
                                onChange={(e) => setPassword2(e.target.value)}
                                className="input"
                                placeholder="Подтвердите пароль"
                                toggleMask
                            />
                        </div>
                    )}

                    <Button
                        label={mode === "login" ? "Войти" : "Зарегистрироваться"}
                        type="submit"
                        className="submit-button"
                    />
                </form>
            </Card>
        </div>
    );
}
