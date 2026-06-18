import { useEffect, useState } from "react";
import { checkHitHistory } from "../../restApi/checkHitHistory";

export function useResults() {
    const [results, setResults] = useState(() => {
        const saved = localStorage.getItem("results");
        return saved ? JSON.parse(saved) : [];
    });

    const addResult = (newResult) => {
        setResults(prev => {
            const updated = [...prev, newResult];
            localStorage.setItem("results", JSON.stringify(updated));
            return updated;
        });
    };

    useEffect(() => {
        // если localStorage пустой — подгружаем с сервера
        if (results.length === 0) {
            fetchResultsFromServer();
        }
    }, []);

    const fetchResultsFromServer = async () => {
        try {
            const data = await checkHitHistory(); // fetch с сервера
            if (data && data.length > 0) {
                setResults(data);
                localStorage.setItem("results", JSON.stringify(data));
            }
        } catch (err) {
            console.error("Ошибка при получении данных из БД:", err);
        }
    };

    return { results, addResult };
}
