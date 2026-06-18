import { useState } from "react";
import { Card } from "primereact/card";
import AuthModeSwitcher from "./AuthModeSwitcher";
import AuthForm from "./AuthForm";
import { useAuth } from "./authFormLogic";

import "../../styles/StartPageStyles.css";
import {Toast} from "primereact/toast";

export default function StartPage({ onLogin }) {
    const [mode, setMode] = useState("login");
    const [login, setLogin] = useState("");
    const [password, setPassword] = useState("");
    const [password2, setPassword2] = useState("");

    const { toastRef, submit } = useAuth(onLogin);

    return (
        <div className="start-page">
            <Toast ref={toastRef} />

            <div className="header">
                <h2>ФИО: Федоров Дмитрий Александрович</h2>
                <h3>Группа: P3206</h3>
                <h3>Вариант: 91781</h3>
            </div>


            <Card className="start-card">
                <AuthModeSwitcher mode={mode} onChange={setMode} />

                <AuthForm
                    mode={mode}
                    login={login}
                    password={password}
                    password2={password2}
                    onLoginChange={setLogin}
                    onPasswordChange={setPassword}
                    onPassword2Change={setPassword2}
                    onSubmit={(e) =>
                        submit(e, mode, login, password, password2)
                    }
                />
            </Card>
        </div>
    );
}
