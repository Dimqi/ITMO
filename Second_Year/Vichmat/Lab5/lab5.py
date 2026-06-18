import math
import numpy as np
import matplotlib.pyplot as plt

functions = {
    1: ("sin(x)", np.sin),
    2: ("x^2", lambda x: x**2)
}


def read_from_file(filename="data.txt"):
    try:
        x, y = [], []
        with open(filename, 'r', encoding='utf-8') as f:
            for line in f:
                line = line.strip().replace(',', '.')
                if not line:
                    continue
                parts = line.split()
                if len(parts) == 2:
                    x.append(float(parts[0]))
                    y.append(float(parts[1]))
        
        if not x:
            print("Ошибка: файл не содержит корректных данных.")
            return None, None

        return np.array(x), np.array(y)
    except Exception as e:
        print(f"Ошибка при чтении файла: {e}")
        return None, None


def read_from_console():
    print("Введите x и y (каждая пара на новой строке через пробел, пустая строка - конец):")
    x, y = [], []
    while True:
        line = input().strip().replace(',', '.')
        if not line:
            if len(x) >= 2:
                break
            else:
                print(f"Нужно минимум 2 точки, введено {len(x)}.")
                continue
        
        parts = line.split()
        if len(parts) != 2:
            print("нужно ввести два числа через пробел.")
            continue
            
        try:
            x.append(float(parts[0]))
            y.append(float(parts[1]))
        except ValueError:
            print("некорректный ввод чисел.")
            
    return np.array(x), np.array(y)


def read_from_function():
    print("\nДоступные функции:")
    for key, (name, _) in functions.items():
        print(f"{key}. {name}")

    while True:
        try:
            choice = int(input("\nВыберите номер функции: "))
            if choice in functions:
                name, func = functions[choice]
                break
            print("Ошибка: выберите корректный номер из списка.")
        except ValueError:
            print("Ошибка: введите целое число.")

    while True:
        try:
            line = input("Введите границы интервала [a, b] через пробел: ").replace(',', '.').split()
            if len(line) != 2:
                print("Ошибка: введите два числа.")
                continue
            a, b = float(line[0]), float(line[1])
            if a == b:
                print("Ошибка: границы не могут совпадать.")
                continue
            start, end = min(a, b), max(a, b)
            break
        except ValueError:
            print("Ошибка: введите корректные числа для границ.")

    while True:
        try:
            n = int(input("Введите количество узлов (минимум 2): "))
            if n >= 2:
                break
            print("Ошибка: нужно минимум 2 узла")
        except ValueError:
            print("Ошибка: введите целое число.")

    x = np.linspace(start, end, n)
    y = func(x)
    
    print(f"{'x':<12}{'y':<12}")
    for i in range(len(x)):
        print(f"{x[i]:<12.4f}{y[i]:<12.4f}")
        
    return x, y


def get_finite_diff_table(y):
    n = len(y)
    table = np.zeros((n, n))
    table[:, 0] = y
    for j in range(1, n):
        for i in range(n - j):
            table[i, j] = table[i + 1, j - 1] - table[i, j - 1]
    return table


def get_divided_diff_table(x, y):
    n = len(y)
    table = np.zeros((n, n))
    table[:, 0] = y
    for j in range(1, n):
        for i in range(n - j):
            numerator = table[i + 1, j - 1] - table[i, j - 1]
            denominator = x[i + j] - x[i]
            table[i, j] = numerator / denominator
    return table


def print_diff_table(x_nodes, table, title="конечных"):
    print(f"\nТаблица {title} разностей:")
    prefix = "f" if title == "разделенных" else "Δ^"
    
    header = f"{'xi':<12}{'yi':<12}"
    for i in range(1, table.shape[1]):
        col_name = f"{prefix}{i}" if title == "разделенных" else f"{prefix}{i}y"
        header += f"{col_name:<12}"
    print(header)

    for i in range(len(x_nodes)):
        row_str = f"{x_nodes[i]:<12.4f}{table[i, 0]:<12.4f}"
        for j in range(1, len(x_nodes) - i):
            row_str += f"{table[i, j]:<12.4f}"
        print(row_str)


def lagrange_interpolation(x_nodes, y_nodes, x_val):
    n = len(x_nodes)
    result = 0
    for i in range(n):
        polynomial = 1.0
        for j in range(n):
            if i != j:
                polynomial *= (x_val - x_nodes[j]) / (x_nodes[i] - x_nodes[j])
        result += y_nodes[i] * polynomial
    return result


def newton_forward_divided(x_nodes, div_table, x_val):
    n = len(x_nodes)
    result = div_table[0, 0]
    product = 1.0
    for i in range(1, n):
        product *= (x_val - x_nodes[i - 1])
        result += div_table[0, i] * product
    return result


def newton_backward_divided(x_nodes, div_table, x_val):
    n = len(x_nodes)
    result = div_table[n - 1, 0]
    product = 1.0
    for i in range(1, n):
        product *= (x_val - x_nodes[n - i])
        result += div_table[n - i - 1, i] * product
    return result


def gauss_first_formula(x_nodes, fin_table, x_val):
    n = len(x_nodes)
    mid = n // 2
    h = x_nodes[1] - x_nodes[0]
    t = (x_val - x_nodes[mid]) / h
    result = fin_table[mid, 0]
    p_term = 1.0
    for i in range(1, n):
        if i % 2 == 1:
            p_term *= (t + (i // 2)) / i
        else:
            p_term *= (t - (i // 2)) / i
        row = mid - (i // 2)
        if 0 <= row < n - i:
            result += p_term * fin_table[row, i]
    return result


def gauss_second_formula(x_nodes, fin_table, x_val):
    n = len(x_nodes)
    mid = n // 2
    h = x_nodes[1] - x_nodes[0]
    t = (x_val - x_nodes[mid]) / h
    result = fin_table[mid, 0]
    p_term = 1.0
    for i in range(1, n):
        if i % 2 == 1:
            p_term *= (t - (i // 2)) / i
        else:
            p_term *= (t + (i // 2)) / i
        row = mid - ((i + 1) // 2)
        if 0 <= row < n - i:
            result += p_term * fin_table[row, i]
    return result



def plot_results(x_nodes, y_nodes, target_x, div_table, fin_table):
    x_min, x_max = np.min(x_nodes), np.max(x_nodes)
    margin = (x_max - x_min) * 0.1
    x_plot = np.linspace(x_min - margin, x_max + margin, 200)
    
    plt.figure(figsize=(12, 7))
    plt.scatter(x_nodes, y_nodes, color='black', s=60, zorder=5, label='Узлы интерполяции')
    

    y_lagrange = [lagrange_interpolation(x_nodes, y_nodes, xi) for xi in x_plot]
    plt.plot(x_plot, y_lagrange, color='blue', linewidth=2, label='Многочлен Лагранжа')
    

    y_newton = [newton_forward_divided(x_nodes, div_table, xi) for xi in x_plot]
    plt.plot(x_plot, y_newton, color='green', linestyle='--', linewidth=2, label='Многочлен Ньютона')
    

    is_equidistant = np.allclose(np.diff(x_nodes), x_nodes[1] - x_nodes[0])
    if is_equidistant and len(x_nodes) > 1:
        y_gauss = [gauss_first_formula(x_nodes, fin_table, xi) for xi in x_plot]
        plt.plot(x_plot, y_gauss, color='magenta', linestyle=':', linewidth=2, label='Многочлен Гаусса (1-я ф.)')
    
    plt.axvline(target_x, color='red', alpha=0.3, label=f'Искомый x = {target_x}')
    
    plt.xlabel('x')
    plt.ylabel('y')
    plt.title('Сравнение интерполяционных многочленов')
    plt.legend(loc='best')
    plt.grid(True, alpha=0.3)
    plt.tight_layout()
    plt.show()

def stirling_interpolation(x_nodes, fin_table, x_val):
    n, mid = len(x_nodes), len(x_nodes) // 2
    h = x_nodes[1] - x_nodes[0]
    t = (x_val - x_nodes[mid]) / h
    result = fin_table[mid, 0]
    for i in range(1, (n // 2) + 1):

        term1 = 1.0
        for k in range(i - 1): 
            term1 *= (t**2 - k**2)

        fact_odd = math.factorial(2 * i - 1)

        if mid - i >= 0 and mid - i + 1 < n:
            diff_avg = (fin_table[mid - i, 2 * i - 1] + fin_table[mid - i + 1, 2 * i - 1]) / 2
            result += (t * term1 / fact_odd) * diff_avg

        term2 = term1 * (t**2 - (i - 1)**2) if i > 1 else t**2
        fact_even = math.factorial(2 * i)
        if mid - i >= 0:
            result += (term2 / fact_even) * fin_table[mid - i, 2 * i]
    return result

def bessel_interpolation(x_nodes, fin_table, x_val):
    n = len(x_nodes)
    mid = n // 2
    h = x_nodes[1] - x_nodes[0]
    t = (x_val - x_nodes[mid]) / h
    
    result = (fin_table[mid, 0] + fin_table[mid + 1, 0]) / 2
    
    result += (t - 0.5) * fin_table[mid, 1]
    
    for i in range(1, n // 2 + 1):
        factor_even = 1.0
        factor_even *= t * (t - 1)
        for k in range(1, i):
            factor_even *= (t + k) * (t - k - 1)
        
        fact_even = 1
        for j in range(2, 2 * i + 1):
            fact_even *= j
        
        if mid - i >= 0 and mid - i + 1 < n and 2 * i < n:
            avg_diff = (fin_table[mid - i, 2 * i] + fin_table[mid - i + 1, 2 * i]) / 2
            result += (factor_even / fact_even) * avg_diff
        
        factor_odd = (t - 0.5) * t * (t - 1)
        for k in range(1, i):
            factor_odd *= (t + k) * (t - k - 1)
        
        fact_odd = 1
        for j in range(2, 2 * i + 2):
            fact_odd *= j
        
        if mid - i >= 0 and 2 * i + 1 < n:
            result += (factor_odd / fact_odd) * fin_table[mid - i, 2 * i + 1]
    
    return result

def main():
    print("1. Консоль, 2. Файл, 3. Функция")
    choice = input("")
    if choice == '1': 
        x, y = read_from_console()
    elif choice == '2': 
        x, y = read_from_file()
    else: 
        x, y = read_from_function()

    if x is None: 
        return
    target_x = float(input("\nВведите X: ").replace(',', '.'))
    finite_table = get_finite_diff_table(y)
    divided_table = get_divided_diff_table(x, y)
    
    print_diff_table(x, finite_table, "конечных")
    print_diff_table(x, divided_table, "разделенных")

    print(f"Лагранж: {lagrange_interpolation(x, y, target_x):.6f}")
    
    n = len(x)
    if target_x <= x[n // 2]:
        print(f"Ньютон (вперед): {newton_forward_divided(x, divided_table, target_x):.6f}")
    else:
        print(f"Ньютон (назад): {newton_backward_divided(x, divided_table, target_x):.6f}")
    
    is_equidistant = np.allclose(np.diff(x), x[1] - x[0])

    if is_equidistant:
        mid = n // 2
        if target_x >= x[mid]:
            print(f"Гаусс (1-я ф.): {gauss_first_formula(x, finite_table, target_x):.6f}")
        else:
            print(f"Гаусс (2-я ф.): {gauss_second_formula(x, finite_table, target_x):.6f}")


        print(f"Стирлинг: {stirling_interpolation(x, finite_table, target_x):.6f}")
        print(f"Бессель:  {bessel_interpolation(x, finite_table, target_x):.6f}")
    else:
        print("Центральные методы недоступны (нужен равный шаг)")
    
    plot_results(x, y, target_x, divided_table, finite_table)

if __name__ == "__main__":
    main()