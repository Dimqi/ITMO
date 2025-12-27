import GraphShapes from "./GraphShapes";
import GraphAxes from "./GraphAxes";
import GraphPoints from "./GraphPoints";
import { checkHit } from "../../restApi/checkHitRequest";

export default function Graph({ r = 1, showToast, addResult, results }) {
    const center = 275;
    const baseScale = 50;
    const scale = baseScale * r;

    const handleClick = async (e) => {

        const rect = e.currentTarget.getBoundingClientRect();
        const x = (e.clientX - rect.left - center) / baseScale;
        const y = (center - (e.clientY - rect.top)) / baseScale;

        if (x < -5 || x > 5 || y < -5 || y > 5) {
            showToast("error","ошибка", "клик вне графика");
            return;
        }

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
        <svg width="550" height="550" onClick={handleClick} style={{ cursor: "pointer" }}>
            <GraphShapes center={center} scale={scale} />
            <GraphAxes center={center} scale={scale} r={r} />
            <GraphPoints
                results={results}
                center={center}
                baseScale={baseScale}
                r={r}
            />
        </svg>
    );
}
