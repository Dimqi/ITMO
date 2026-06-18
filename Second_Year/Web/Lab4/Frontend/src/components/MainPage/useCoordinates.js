import { useState } from "react";

export function useCoordinates() {
    const [x, setX] = useState(() => {
        const saved = localStorage.getItem("x");
        return saved === null ? "" : Number(saved);
    });

    const [y, setY] = useState(() => {
        const saved = localStorage.getItem("y");
        return saved ?? "";
    });

    const [r, setR] = useState(() => {
        const saved = localStorage.getItem("r");
        return saved === null ? 1 : Number(saved);
    });

    const updateX = (val) => {
        localStorage.setItem("x", val);
        setX(val);
    };

    const updateY = (val) => {
        localStorage.setItem("y", val);
        setY(val);
    };

    const updateR = (val) => {
        localStorage.setItem("r", val);
        setR(val);
    };

    return { x, y, r, updateX, updateY, updateR };
}
