import { checkHitGraph } from "./graphUtils";

export default function GraphPoints({ results, center, baseScale, r }) {
    return (
        <g>
            {results.map((res, i) => (
                <circle
                    key={i}
                    cx={center + res.x * baseScale}
                    cy={center - res.y * baseScale}
                    r={5}
                    fill={checkHitGraph(res.x, res.y, r) ? "green" : "red"}
                />
            ))}
        </g>
    );
}
