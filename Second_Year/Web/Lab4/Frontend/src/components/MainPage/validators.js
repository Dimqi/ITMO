import Decimal from "decimal.js";

export function validateForm(x, y, r, showToast) {
    const xFl = parseFloat(x);
    const rFl = parseFloat(r);

    if (isNaN(xFl)) {
        showToast("error","Ошибка ввода", "Выберите X");
        return false;
    }


    if (isNaN(rFl) || rFl <= 0) {
        showToast("error","Ошибка ввода", "Выберите R" );
        return false;
    }


    if (y === null) {
        showToast("error","Введите ввода", "Выберите Y");
        return false;
    }

    const value = y.trim();
    if (value === "") {
        showToast("error","Введите ввода", "Выберите Y");
        return false;
    }

    let dec;
    try {
        dec = new Decimal(value);
    } catch {
        return false; // не число
    }
    const min = new Decimal("-5");
    const max = new Decimal("5");

    if (!(dec.greaterThanOrEqualTo(min) && dec.lessThanOrEqualTo(max))) {
        showToast("error","Введите ввода", "Y должен быть от -5 до 5");
        return false;
    }



    return true;
}
