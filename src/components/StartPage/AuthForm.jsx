import { InputText } from "primereact/inputtext";
import { Password } from "primereact/password";
import { Button } from "primereact/button";
import "../../styles/authFormStyles.css";
export default function AuthForm({
                                     mode,
                                     login,
                                     password,
                                     password2,
                                     onLoginChange,
                                     onPasswordChange,
                                     onPassword2Change,
                                     onSubmit
                                 }) {
    return (
        <form onSubmit={onSubmit} className="form">
            <label htmlFor="login">Логин:</label>
            <InputText
                id="login"
                value={login}
                onChange={(e) => onLoginChange(e.target.value)}
                className="input"
                placeholder="Введите логин"
            />

            <label htmlFor="password">Пароль:</label>
            <Password
                id="password"
                value={password}
                onChange={(e) => onPasswordChange(e.target.value)}
                className="input"
                placeholder="Введите пароль"
                toggleMask
            />

            {mode === "register" && (
                <Password
                    id="password2"
                    value={password2}
                    onChange={(e) => onPassword2Change(e.target.value)}
                    className="input"
                    placeholder="Подтвердите пароль"
                    toggleMask
                />
            )}

            <Button
                label={mode === "login" ? "Войти" : "Зарегистрироваться"}
                type="submit"
                className="submit-button"
            />
        </form>
    );
}
