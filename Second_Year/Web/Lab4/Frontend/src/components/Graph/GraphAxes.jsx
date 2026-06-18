export default function GraphAxes({ center, scale, r }) {
    const sx = (v) => center + v * scale;
    const sy = (v) => center - v * scale;

    return (
        <>
            {/* Ось X */}
            <line x1="50" y1={center} x2="500" y2={center} stroke="black" strokeWidth="2" />

            <line x1={sx(-1)} x2={sx(-1)} y1={center - 5} y2={center + 5} stroke="black" />
            <text x={sx(-1)} y={center - 10}>-{r}</text>

            <line x1={sx(-0.5)} x2={sx(-0.5)} y1={center - 5} y2={center + 5} stroke="black" />
            <text x={sx(-0.5)} y={center - 10}>-{r / 2}</text>

            <line x1={sx(0.5)} x2={sx(0.5)} y1={center - 5} y2={center + 5} stroke="black" />
            <text x={sx(0.5)} y={center - 10}>{r / 2}</text>

            <line x1={sx(1)} x2={sx(1)} y1={center - 5} y2={center + 5} stroke="black" />
            <text x={sx(1)} y={center - 10}>{r}</text>

            <line x1="492" y1={center - 8} x2="500" y2={center} stroke="black" />
            <line x1="492" y1={center + 8} x2="500" y2={center} stroke="black" />
            <text x="510" y={center} fontSize="14">x</text>

            {/* Ось Y */}
            <line x1={center} y1="50" x2={center} y2="500" stroke="black" strokeWidth="2" />

            <line y1={sy(1)} y2={sy(1)} x1={center - 5} x2={center + 5} stroke="black" />
            <text y={sy(1)} x={center + 10}>{r}</text>

            <line y1={sy(0.5)} y2={sy(0.5)} x1={center - 5} x2={center + 5} stroke="black" />
            <text y={sy(0.5)} x={center + 10}>{r / 2}</text>

            <line y1={sy(-0.5)} y2={sy(-0.5)} x1={center - 5} x2={center + 5} stroke="black" />
            <text y={sy(-0.5)} x={center + 10}>-{r / 2}</text>

            <line y1={sy(-1)} y2={sy(-1)} x1={center - 5} x2={center + 5} stroke="black" />
            <text y={sy(-1)} x={center + 10}>-{r}</text>

            <line x1={center} y1="50" x2={center - 8} y2="58" stroke="black" />
            <line x1={center} y1="50" x2={center + 8} y2="58" stroke="black" />
            <text x={center + 10} y="55" fontSize="14">y</text>
        </>
    );
}
