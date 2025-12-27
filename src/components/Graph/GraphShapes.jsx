export default function GraphShapes({ center, scale }) {
    const rect = {
        x: center - scale,
        y: center,
        width: scale,
        height: scale / 2
    };

    const arcPath = `
        M${center},${center}
        L${center},${center - scale}
        A${scale},${scale} 0 0,1 ${center + scale},${center}
    `;

    const trianglePoints = `
        ${center},${center}
        ${center + scale / 2},${center}
        ${center},${center + scale}
    `;

    return (
        <>
            <rect {...rect} fill="lightblue" />
            <path d={arcPath} fill="lightblue" />
            <polygon points={trianglePoints} fill="lightblue" />
        </>
    );
}
