import { useToast } from "../useToast";
import { authRequest } from "../../restApi/authRequest";
import { useNavigate } from "react-router-dom";

export function useAuth(onLogin) {
    const { toastRef, showToast } = useToast();
    const navigate = useNavigate();

    const submit = async (e, mode, login, password, password2) => {
        e.preventDefault();

        if (!login.trim()) {
            showToast("error", "Ошибка", "Вы не ввели логин");
            return;
        }

        if (!password.trim()) {
            showToast("error", "Ошибка", "Вы не ввели пароль");
            return;
        }

        if (mode === "register") {
            if (!password2.trim()) {
                showToast("error", "Ошибка", "Введите пароль ещё раз");
                return;
            }
            if (password !== password2) {
                showToast("error", "Ошибка", "Пароли не совпадают");
                return;
            }
        }

        try {
            const res = await authRequest(mode, login, password);
            if (!res) return;

            onLogin(res);
            navigate("/main");
        } catch (err) {
            // проверяем текст ошибки
            if (err.message === "Conflict") {
                showToast("error", "Регистрация", "Пользователь уже существует");
            } else if (err.message === "Unknown user") {
                showToast("error", "Авторизация", "Неверный логин или пароль");
            } else {
                showToast("error", "Ошибка", "Произошла ошибка при авторизации");
            }
        }
    };

    return { toastRef, submit };
}
