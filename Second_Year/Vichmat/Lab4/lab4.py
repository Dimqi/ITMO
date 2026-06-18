import math
import matplotlib.pyplot as plt
import numpy as np



def solve_linear_system(A, B):
    n = len(A)
    augmented = np.hstack([A, B.reshape(-1, 1)]).astype(float)
    
    for i in range(n):
        max_row = i + np.argmax(np.abs(augmented[i:, i]))
        augmented[[i, max_row]] = augmented[[max_row, i]]
        
        if abs(augmented[i, i]) < 1e-10:
            return None
        
        augmented[i] = augmented[i] / augmented[i, i]
        
        for j in range(n):
            if j != i:
                augmented[j] = augmented[j] - augmented[j, i] * augmented[i]
    
    return augmented[:, -1]


def read_from_file(filename="data.txt"):
    try:
        x, y = [], []
        with open(filename, 'r', encoding='utf-8') as f:
            for line in f:
                line = line.strip()
                if not line:
                    continue
                line = line.replace(',', '.')
                parts = line.split()
                if len(parts) != 2:
                    continue
                x.append(float(parts[0]))
                y.append(float(parts[1]))
        
        if len(x) == 0:
            print("Ошибка: файл не содержит корректных данных")
            return None, None
            
        x = np.array(x)
        y = np.array(y)
        
        if len(x) < 8 or len(x) > 12:
            print(f"Предупреждение: {len(x)} точек (рекомендовано 8-12)")
        return x, y
    except Exception as e:
        print(f"Ошибка чтения: {e}")
        return None, None


def read_from_console():
    print("Введите x y (каждая пара на новой строке, пустая строка - конец)")
    x, y = [], []
    while True:
        line = input().strip()
        if not line:
            if len(x) >= 8:
                break
            else:
                print(f"Нужно минимум 8 точек, введено {len(x)}")
                continue
        
        line = line.replace(',', '.')
        
        parts = line.split()
        if len(parts) != 2:
            print("Нужно два числа")
            continue
        try:
            x.append(float(parts[0]))
            y.append(float(parts[1]))
        except:
            print("Ошибка ввода")
    return np.array(x), np.array(y)


def pearson_correlation(x, y):
    n = len(x)
    x_mean = np.sum(x) / n
    y_mean = np.sum(y) / n
    
    numerator = np.sum((x - x_mean) * (y - y_mean))
    denominator = np.sqrt(np.sum((x - x_mean)**2) * np.sum((y - y_mean)**2))
    
    return numerator / denominator if denominator != 0 else 0

def r_squared(y_true, y_pred):
    ss_res = np.sum((y_true - y_pred)**2)
    ss_tot = np.sum((y_true - np.mean(y_true))**2)
    return 1 - (ss_res / ss_tot) if ss_tot != 0 else 1


def plot_data_and_functions(x, y, results, best_result):
    x_min, x_max = np.min(x), np.max(x)
    margin = (x_max - x_min) * 0.1
    x_plot = np.linspace(x_min - margin, x_max + margin, 200)
    
    plt.figure(figsize=(12, 7))
    plt.scatter(x, y, color='black', s=50, zorder=5, label='Исходные данные')
    
    colors = ['red', 'blue', 'green', 'orange', 'purple', 'brown']
    for i, res in enumerate(results):
        if res['func']:
            y_plot = res['func'](x_plot)
            plt.plot(x_plot, y_plot, color=colors[i % len(colors)], 
                    linewidth=2, label=f"{res['name']} (δ={res['std_dev']:.4f})")
    
    plt.xlabel('x')
    plt.ylabel('y')
    plt.title('Аппроксимация методом наименьших квадратов')
    plt.legend(loc='best', fontsize=9)
    plt.grid(True, alpha=0.3)
    
    info = f"Лучшая: {best_result['name']}\nδ = {best_result['std_dev']:.6f}\nR² = {best_result['r2']:.4f}"
    plt.text(0.02, 0.98, info, transform=plt.gca().transAxes, fontsize=10,
             verticalalignment='top', bbox=dict(boxstyle='round', facecolor='wheat', alpha=0.8))
    
    plt.tight_layout()
    plt.show()

def linear_approx(x, y):
    n = len(x)
    SX = np.sum(x)
    SXX = np.sum(x**2)
    SY = np.sum(y)
    SXY = np.sum(x * y)
    
    delta = SXX * n - SX * SX
    delta1 = SXY * n - SX * SY
    delta2 = SXX * SY - SX * SXY
    
    a = delta1 / delta
    b = delta2 / delta
    
    def func(xi): return a * xi + b
    
    y_pred = func(x)
    std_dev = np.sqrt(np.sum((y_pred - y)**2) / n)
    r2 = r_squared(y, y_pred)
    
    return {
        'name': 'Линейная (ax + b)',
        'params': (a, b),
        'param_names': ('a', 'b'),
        'func': func,
        'std_dev': std_dev,
        'r2': r2,
        'y_pred': y_pred
    }

def quadratic_approx(x, y):
    n = len(x)
    SX = np.sum(x)
    SXX = np.sum(x**2)
    SXXX = np.sum(x**3)
    SXXXX = np.sum(x**4)
    SY = np.sum(y)
    SXY = np.sum(x * y)
    SXXY = np.sum((x**2) * y)
    
    A = np.array([[n, SX, SXX],
                  [SX, SXX, SXXX],
                  [SXX, SXXX, SXXXX]])
    B = np.array([SY, SXY, SXXY])
    
    try:
        a0, a1, a2 = solve_linear_system(A, B)
        if a0 is None:
            return None
    except:
        return None
    
    def func(xi): return a0 + a1 * xi + a2 * xi**2
    
    y_pred = func(x)
    std_dev = np.sqrt(np.sum((y_pred - y)**2) / n)
    r2 = r_squared(y, y_pred)
    
    return {
        'name': 'Полином 2-й степени (a0 + a1·x + a2·x²)',
        'params': (a0, a1, a2),
        'param_names': ('a0', 'a1', 'a2'),
        'func': func,
        'std_dev': std_dev,
        'r2': r2,
        'y_pred': y_pred
    }

def cubic_approx(x, y):
    n = len(x)
    SX = np.sum(x)
    SXX = np.sum(x**2)
    SXXX = np.sum(x**3)
    SXXXX = np.sum(x**4)
    SXXXXX = np.sum(x**5)
    SXXXXXX = np.sum(x**6)
    SY = np.sum(y)
    SXY = np.sum(x * y)
    SXXY = np.sum((x**2) * y)
    SXXXY = np.sum((x**3) * y)
    
    A = np.array([[n, SX, SXX, SXXX],
                  [SX, SXX, SXXX, SXXXX],
                  [SXX, SXXX, SXXXX, SXXXXX],
                  [SXXX, SXXXX, SXXXXX, SXXXXXX]])
    B = np.array([SY, SXY, SXXY, SXXXY])
    
    try:
        a0, a1, a2, a3 = solve_linear_system(A, B)
        if a0 is None:
            return None
    except:
        return None
    
    def func(xi): return a0 + a1 * xi + a2 * xi**2 + a3 * xi**3
    
    y_pred = func(x)
    std_dev = np.sqrt(np.sum((y_pred - y)**2) / n)
    r2 = r_squared(y, y_pred)
    
    return {
        'name': 'Полином 3-й степени (a0 + a1·x + a2·x² + a3·x³)',
        'params': (a0, a1, a2, a3),
        'param_names': ('a0', 'a1', 'a2', 'a3'),
        'func': func,
        'std_dev': std_dev,
        'r2': r2,
        'y_pred': y_pred
    }

def exponential_approx(x, y):
    if np.any(y <= 0):
        return None
    
    ln_y = np.log(y)
    n = len(x)
    SX = np.sum(x)
    SXX = np.sum(x**2)
    SY = np.sum(ln_y)
    SXY = np.sum(x * ln_y)
    
    delta = SXX * n - SX * SX
    delta1 = SXY * n - SX * SY
    delta2 = SXX * SY - SX * SXY
    
    B = delta1 / delta
    A = delta2 / delta
    
    a = math.exp(A)
    b = B
    
    def func(xi): return a * np.exp(b * xi)
    
    y_pred = np.array([func(xi) for xi in x])
    std_dev = np.sqrt(np.sum((y_pred - y)**2) / n)
    r2 = r_squared(y, y_pred)
    
    return {
        'name': 'Экспоненциальная (a·e^(b·x))',
        'params': (a, b),
        'param_names': ('a', 'b'),
        'func': func,
        'std_dev': std_dev,
        'r2': r2,
        'y_pred': y_pred
    }

def logarithmic_approx(x, y):
    if np.any(x <= 0):
        return None
    
    ln_x = np.log(x)
    n = len(x)
    SX = np.sum(ln_x)
    SXX = np.sum(ln_x**2)
    SY = np.sum(y)
    SXY = np.sum(ln_x * y)
    
    delta = SXX * n - SX * SX
    delta1 = SXY * n - SX * SY
    delta2 = SXX * SY - SX * SXY
    
    a = delta1 / delta
    b = delta2 / delta
    
    def func(xi): return a * np.log(xi) + b
    
    y_pred = np.array([func(xi) for xi in x])
    std_dev = np.sqrt(np.sum((y_pred - y)**2) / n)
    r2 = r_squared(y, y_pred)
    
    return {
        'name': 'Логарифмическая (a·ln(x) + b)',
        'params': (a, b),
        'param_names': ('a', 'b'),
        'func': func,
        'std_dev': std_dev,
        'r2': r2,
        'y_pred': y_pred
    }

def power_approx(x, y):
    if np.any(x <= 0) or np.any(y <= 0):
        return None
    
    ln_x = np.log(x)
    ln_y = np.log(y)
    n = len(x)
    SX = np.sum(ln_x)
    SXX = np.sum(ln_x**2)
    SY = np.sum(ln_y)
    SXY = np.sum(ln_x * ln_y)
    
    delta = SXX * n - SX * SX
    delta1 = SXY * n - SX * SY
    delta2 = SXX * SY - SX * SXY
    
    B = delta1 / delta
    A = delta2 / delta
    
    a = math.exp(A)
    b = B
    
    def func(xi): return a * (xi ** b)
    
    y_pred = np.array([func(xi) for xi in x])
    std_dev = np.sqrt(np.sum((y_pred - y)**2) / n)
    r2 = r_squared(y, y_pred)
    
    return {
        'name': 'Степенная (a·x^b)',
        'params': (a, b),
        'param_names': ('a', 'b'),
        'func': func,
        'std_dev': std_dev,
        'r2': r2,
        'y_pred': y_pred
    }

def main():

    approx_funcs = {
        1: linear_approx,
        2: quadratic_approx,
        3: cubic_approx,
        4: exponential_approx,
        5: logarithmic_approx,
        6: power_approx
    }
    

    print("Метод наименьших квадратов")
    
    source = input("Источник данных файл/консоль (1/2): ")
    if source == '1':
        filename = input("Имя файла: ")
        x, y = read_from_file(filename)
    else:
        x, y = read_from_console()
    
    if x is None or len(x) < 3:
        print("Ошибка: недостаточно данных")
        return
    
    n = len(x)
    print(f"\nДанные ({n} точек):")
    for i in range(n):
        print(f"  x[{i+1}]={x[i]:.4f}  y[{i+1}]={y[i]:.4f}")

    results = {}
    
    for key, func in approx_funcs.items():
        result = func(x, y)
        if result is None:
            continue
        
        results[key] = result
        
        print(f"\n{result['name']}")
        for name, val in zip(result['param_names'], result['params']):
            print(f"  {name} = {val:.6f}")
        print(f"  Среднекв. отклонение δ = {result['std_dev']:.6f}")
        print(f"  R² = {result['r2']:.6f}")
        
        if result['name'].startswith('Линейная'):
            r_pearson = pearson_correlation(x, y)
            print(f"  Корреляция Пирсона r = {r_pearson:.6f}")
        
        print("\n   i    x_i       y_i    φ(x_i)     ε_i")
        for i in range(n):
            eps = result['y_pred'][i] - y[i]
            print(f"  {i+1:2d}   {x[i]:8.4f}  {y[i]:8.4f}  {result['y_pred'][i]:8.4f}  {eps:8.4f}")
    
    if results:
        best_key = min(results.keys(), key=lambda k: results[k]['std_dev'])
        best = results[best_key]
        
        print("Лучшая аппроксимирующая функция")

        print(f"{best['name']}")
        print(f"δ = {best['std_dev']:.6f}, R² = {best['r2']:.6f}")
        
        plot_data_and_functions(x, y, list(results.values()), best)
        
        if input("\nСохранить результаты в файл? (y/n): ").lower() == 'y':
            fname = input("Имя файла: ")
            with open(fname, 'w', encoding='utf-8') as f:
                f.write("Метод наименьших квадратов\n")
                f.write(f"Источник данных: {'файл' if source == '1' else 'консоль'}\n")
                f.write(f"\nДанные ({n} точек):\n")
                for i in range(n):
                    f.write(f"  x[{i+1}]={x[i]:.4f}  y[{i+1}]={y[i]:.4f}\n")
                
                for key, res in results.items():
                    f.write(f"\n{res['name']}\n")
                    for name, val in zip(res['param_names'], res['params']):
                        f.write(f"  {name} = {val:.6f}\n")
                    f.write(f"  Среднекв. отклонение δ = {res['std_dev']:.6f}\n")
                    f.write(f"  R² = {res['r2']:.6f}\n")
                    
                    if res['name'].startswith('Линейная'):
                        r_pearson = pearson_correlation(x, y)
                        f.write(f"  Корреляция Пирсона r = {r_pearson:.6f}\n")
                    
                    f.write("\n   i    x_i       y_i    φ(x_i)     ε_i\n")
                    for i in range(n):
                        eps = res['y_pred'][i] - y[i]
                        f.write(f"  {i+1:2d}   {x[i]:8.4f}  {y[i]:8.4f}  {res['y_pred'][i]:8.4f}  {eps:8.4f}\n")
                
                best_key = min(results.keys(), key=lambda k: results[k]['std_dev'])
                best = results[best_key]
                f.write("\nЛучшая аппроксимирующая функция\n")
                f.write(f"{best['name']}\n")
                f.write(f"δ = {best['std_dev']:.6f}, R² = {best['r2']:.6f}\n")
            
            print(f"Сохранено в {fname}")
    
    print("\nПрограмма завершена.")

if __name__ == "__main__":
    main()