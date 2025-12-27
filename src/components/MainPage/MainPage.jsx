
import "../../styles/MainPageStyles.css";
import Graph from "../Graph/Graph";
import ResultTable from "../ResultTable";
import MainMenu from "../NavigationMenu";
import CoordinateForm from "./CoordinateForm";
import { Toast } from "primereact/toast";
import { checkHit } from "../../restApi/checkHitRequest";
import { useCoordinates } from "./useCoordinates";
import { useResults } from "./useResults";
import { useToast } from "../useToast";
import { validateForm } from "./validators";

export function MainPage({ onLogout }) {
    const { x, y, r, updateX, updateY, updateR } = useCoordinates();
    const { results, addResult } = useResults();
    const { toastRef, showToast } = useToast();

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!validateForm(x, y, r, showToast)) return;

        try {
            const res = await checkHit(x, y, r);
            if (!res) return;

            addResult(res);
            showToast(res.hit ? "info" : "error",
                "Результат",
                res.hit ? "Попадание" : "Промах");
        }catch(err) {
            if (err.message === "Unauthorized") {
                showToast(
                    "error",
                    "Ошибка авторизации",
                    "Авторизуйтесь в системе заново");
            }
        }


    };

    return (
        <div id="MainPage">
            <Toast ref={toastRef} />
            <MainMenu onLogout={onLogout} />

            <div id="form-and-svg-container">
                <CoordinateForm
                    x={x}
                    y={y}
                    r={r}
                    onXChange={updateX}
                    onYChange={updateY}
                    onRChange={updateR}
                    onSubmit={handleSubmit}
                />

                <Graph
                    r={r}
                    results={results}
                    addResult={addResult}
                    showToast={showToast}
                />
            </div>

            <ResultTable results={results} />
        </div>
    );
}
