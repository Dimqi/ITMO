import React from "react";
import {checkHit} from "../restApi/checkHitRequest";

export default function Graph({ r = 1, showToast, addResult, results}) {
    const center = 275;
    const baseScale = 50;
    const scale = baseScale * r;


    const  handleClick = async (event) => {

        const rect = event.currentTarget.getBoundingClientRect();
        const x = (event.clientX - rect.left - center) / baseScale;
        const y = (center - (event.clientY - rect.top)) / baseScale;

        if (x < -5 || x > 5 || y < -5 || y > 5) {
            showToast("ошибка", "клик вне графика", "error")
            return;
        }

        console.log("x = "+ x,"y = "+ y,"r = "+ r);

        const res = await checkHit(x, y, r);
        showToast("Проверка", res.hit);
        addResult(res);

    };

    function checkHitGraph(xStr, yStr, rStr) {
        const x = parseFloat(xStr);
        const y = parseFloat(yStr);
        const r = parseFloat(rStr);

        let hit = false;


        if (x <= 0 && x >= -r && y <= 0 && y >= -r / 2) {
            hit = true;
        }


        if (x >= 0 && y >= 0 && (x*x + y*y) <= r*r) {
            hit = true;
        }


        const x1 = 0, y1 = 0;
        const x2 = r / 2, y2 = 0;
        const x3 = 0, y3 = -r;

        const denominator = ((y2 - y3)*(x1 - x3) + (x3 - x2)*(y1 - y3));
        const a = ((y2 - y3)*(x - x3) + (x3 - x2)*(y - y3)) / denominator;
        const b = ((y3 - y1)*(x - x3) + (x1 - x3)*(y - y3)) / denominator;
        const c = 1 - a - b;

        if (a >= 0 && b >= 0 && c >= 0) {
            hit = true;
        }

        return hit;
    }


    const rect = {
        x: center - scale,
        y: center,
        width: scale,
        height: scale / 2
    };

    const arcPath = `M${center},${center} 
                     L${center},${center - scale} 
                     A${scale},${scale} 0 0,1 ${center + scale},${center}`;

    const triangle = {
        p1: `${center},${center}`,
        p2: `${center + scale / 2},${center}`,
        p3: `${center},${center +scale}`
    };

    const sx = (value) => center + value * scale;
    const sy = (value) => center - value * scale;

    return (
        <svg width="550" height="550" style={{ cursor: "pointer" }} onClick={handleClick}>

            {/* --- Фигуры --- */}
            <rect {...rect} fill="lightblue" />

            <path d={arcPath} fill="lightblue" />

            <polygon points={`${triangle.p1} ${triangle.p2} ${triangle.p3}`} fill="lightblue" />

            {/* --- Ось X --- */}
            <line x1="50" y1={center} x2="500" y2={center} stroke="black" strokeWidth="2" />

            {/* Метки X */}
            <line x1={sx(-1)} x2={sx(-1)} y1={center - 5} y2={center + 5} stroke="black" />
            <text x={sx(-1)} y={center - 10} fontSize="14">-{r}</text>

            <line x1={sx(-0.5)} x2={sx(-0.5)} y1={center - 5} y2={center + 5} stroke="black" />
            <text x={sx(-0.5)} y={center - 10} fontSize="14">-{r / 2}</text>

            <line x1={sx(0.5)} x2={sx(0.5)} y1={center - 5} y2={center + 5} stroke="black" />
            <text x={sx(0.5)} y={center - 10} fontSize="14">{r / 2}</text>

            <line x1={sx(1)} x2={sx(1)} y1={center - 5} y2={center + 5} stroke="black" />
            <text x={sx(1)} y={center - 10} fontSize="14">{r}</text>

            <line x1="492" y1={center - 3} x2="500" y2={center} stroke="black" />
            <line x1="492" y1={center + 3} x2="500" y2={center} stroke="black" />
            <text x="510" y={center} fontSize="14">x</text>

            {/* --- Ось Y --- */}
            <line x1={center} y1="50" x2={center} y2="500" stroke="black" strokeWidth="2" />

            {/* Метки Y */}
            <line y1={sy(1)} y2={sy(1)} x1={center - 5} x2={center + 5} stroke="black" />
            <text y={sy(1)} x={center + 10} fontSize="14">{r}</text>

            <line y1={sy(0.5)} y2={sy(0.5)} x1={center - 5} x2={center + 5} stroke="black" />
            <text y={sy(0.5)} x={center + 10} fontSize="14">{r / 2}</text>

            <line y1={sy(-0.5)} y2={sy(-0.5)} x1={center - 5} x2={center + 5} stroke="black" />
            <text y={sy(-0.5)} x={center + 10} fontSize="14">-{r / 2}</text>

            <line y1={sy(-1)} y2={sy(-1)} x1={center - 5} x2={center + 5} stroke="black" />
            <text y={sy(-1)} x={center + 10} fontSize="14">-{r}</text>

            <line x1={center} y1="50" x2={center - 8} y2="58" stroke="black" />
            <line x1={center} y1="50" x2={center + 8} y2="58" stroke="black" />
            <text x={center + 10} y="55" fontSize="14">y</text>

            <g>
                {results.map((result, index) => (
                    <circle
                        key={index}
                        cx={center + result.x * baseScale}
                        cy={center - result.y * baseScale}
                        r={5}
                        fill={checkHitGraph(result.x, result.y, r) ? "green" : "red"}
                    />
                ))}
            </g>

        </svg>
    );
}
