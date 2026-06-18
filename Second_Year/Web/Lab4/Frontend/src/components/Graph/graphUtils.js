export function checkHitGraph(x, y, r) {
    let hit = false;

    // Прямоугольник
    if (x <= 0 && x >= -r && y <= 0 && y >= -r / 2) {
        hit = true;
    }

    // Сектор
    if (x >= 0 && y >= 0 && (x * x + y * y) <= r * r) {
        hit = true;
    }

    // Треугольник
    const x1 = 0, y1 = 0;
    const x2 = r / 2, y2 = 0;
    const x3 = 0, y3 = -r;

    const denominator =
        (y2 - y3) * (x1 - x3) + (x3 - x2) * (y1 - y3);

    const a =
        ((y2 - y3) * (x - x3) + (x3 - x2) * (y - y3)) / denominator;
    const b =
        ((y3 - y1) * (x - x3) + (x1 - x3) * (y - y3)) / denominator;
    const c = 1 - a - b;

    if (a >= 0 && b >= 0 && c >= 0) {
        hit = true;
    }

    return hit;
}
