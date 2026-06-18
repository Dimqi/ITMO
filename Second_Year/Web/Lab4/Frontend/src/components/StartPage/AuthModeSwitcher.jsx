import { Button } from "primereact/button";
import "../../styles/authModeSwitcher.css";

export default function AuthModeSwitcher({ mode, onChange }) {
    return (
        <div className="switcher">
            <Button
                label="Вход"
                className={mode === "login" ? "active-button" : "p-button-text"}
                onClick={() => onChange("login")}
            />
            <Button
                label="Регистрация"
                className={mode === "register" ? "active-button" : "p-button-text"}
                onClick={() => onChange("register")}
            />
        </div>
    );
}
