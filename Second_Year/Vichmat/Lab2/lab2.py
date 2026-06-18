import math
import matplotlib.pyplot as plt
import numpy as np


equations = [
    {
        'name': 'x^3 + 4.81x^2 - 17.37x + 5.38',
        'f': lambda x: x**3 + 4.81*x**2 - 17.37*x + 5.38,
        'df': lambda x: 3*x**2 + 9.62*x - 17.37,
        'phi': lambda x: math.sqrt((-x**3 + 17.37*x - 5.38) / 4.81),
    },
    {
        'name': 'sin(x) - 0.5',
        'f': lambda x: math.sin(x) - 0.5,
        'df': lambda x: math.cos(x),
        'phi': lambda x: math.asin(0.5)
    },
    {
        'name': 'e^x - 3x',
        'f': lambda x: math.exp(x) - 3*x,
        'df': lambda x: math.exp(x) - 3,
        'phi': lambda x: math.log(3*x)
    },
    {
        'name': 'x^2 - 2',
        'f': lambda x: x**2 - 2,
        'df': lambda x: 2*x,
        'phi': lambda x: math.sqrt(2)
    },
    {
        'name': 'cos(x) - x',
        'f': lambda x: math.cos(x) - x,
        'df': lambda x: -math.sin(x) - 1,
        'phi': lambda x: math.cos(x)
    }
]


systems = [
    {
        'name': 'sin(y) + 2x = 2; y + cos(x-1) = 0.7',
        'F1': lambda x, y: np.sin(y) + 2*x - 2,
        'F2': lambda x, y: y + np.cos(x-1) - 0.7,
        'dF1dx': lambda x, y: 2,
        'dF1dy': lambda x, y: np.cos(y),
        'dF2dx': lambda x, y: -np.sin(x-1),
        'dF2dy': lambda x, y: 1
    },
    {
        'name': 'x^2 + y^2 = 4; y = x^2',
        'F1': lambda x, y: x**2 + y**2 - 4,
        'F2': lambda x, y: y - x**2,
        'dF1dx': lambda x, y: 2*x,
        'dF1dy': lambda x, y: 2*y,
        'dF2dx': lambda x, y: -2*x,
        'dF2dy': lambda x, y: 1
    }
]


def read_from_file(filename="data.txt"):
    try:
        with open(filename, 'r') as f:
            lines = f.readlines()
            a = float(lines[0].strip())
            b = float(lines[1].strip())
            eps = float(lines[2].strip())
            return a, b, eps
    except:
        print("Ошибка чтения файла")
        return None, None, None

def check_interval(f, a, b, num_points=100):
    fa = f(a)
    fb = f(b)
    
    if fa * fb > 0:
        print("Ошибка: функция имеет одинаковые знаки на концах интервала, на интерфале либо нет корней, либо их чётное количество")
        return False
    
    crossings = 0
    step = (b - a) / num_points
    x_prev = a
    f_prev = fa
    
    for i in range(1, num_points + 1):
        x_curr = a + i * step
        f_curr = f(x_curr)
        if f_prev * f_curr < 0:
            crossings += 1
        x_prev, f_prev = x_curr, f_curr
    
    if crossings != 1:
        print(f"Ошибка: на интервале {crossings} корней")
        return False
    
    return True

def choose_initial(f, a, b):
    fa = abs(f(a))
    fb = abs(f(b))
    return a if fa < fb else b

def output_result(result, to_file=False, filename="result.txt"):
    if to_file:
        with open(filename, 'w') as f:
            f.write(str(result))
        print(f"Результат сохранён в {filename}")
    else:
        print("Результат\n:")
        print(result)


def plot_function(eq, a, b):
    x = np.linspace(a, b, 400)
    y = [eq['f'](xi) for xi in x]
    
    plt.figure(figsize=(10, 6))
    plt.plot(x, y, 'b-', linewidth=2, label=eq['name'])
    plt.axhline(0, color='black', linewidth=0.5)
    plt.axvline(0, color='black', linewidth=0.5)
    plt.grid(True, alpha=0.3)
    plt.xlabel('x')
    plt.ylabel('f(x)')
    plt.title(f'График функции: {eq["name"]}')
    plt.show()

def plot_system(system, x_range, y_range):
    x = np.linspace(x_range[0], x_range[1], 100)
    y = np.linspace(y_range[0], y_range[1], 100)
    X, Y = np.meshgrid(x, y)
    
    Z1 = system['F1'](X, Y)
    Z2 = system['F2'](X, Y)

    plt.figure(figsize=(10, 8))
    contour1 = plt.contour(X, Y, Z1, levels=[0], colors='blue', linewidths=2)
    contour2 = plt.contour(X, Y, Z2, levels=[0], colors='red', linewidths=2)
    
    plt.xlabel('x')
    plt.ylabel('y')
    plt.title(f'График системы: {system["name"]}')
    plt.grid(True, alpha=0.3)
    plt.show()

def half_division_method(eq, a, b, eps, max_iter=100):
    print("\n" + f"{'№ шага':<8} {'a':<10} {'b':<10} {'x':<10} {'f(x)':<10} {'|a-b|':<10}")
    
    fa = eq['f'](a)
    fb = eq['f'](b)
    
    if fa * fb > 0:
        return None, 0, "На интервале нет корня или их несколько"
    
    iter_count = 0
    while (b - a) > eps and iter_count < max_iter:
        c = (a + b) / 2
        fc = eq['f'](c)
        
        print("\n" + f"{iter_count+1:<8} {a:<10.3f} {b:<10.3f} {c:<10.3f} {fc:<10.3f} {abs(a-b):<10.5f}")
        
        if abs(fc) < eps:
            return c, iter_count+1, "OK"
        
        if fa * fc < 0:
            b = c
            fb = fc
        else:
            a = c
            fa = fc
        
        iter_count += 1
    
    x = (a + b) / 2
    print("\n" + f"{iter_count+1:<8} {a:<10.3f} {b:<10.3f} {x:<10.3f} {eq['f'](x):<10.3f} {abs(a-b):<10.5f}")
    return x, iter_count+1, "OK"

def secant_method(eq, a, b, eps, max_iter=100):

    print("\n" + f"{'№ итер.':<8} {'x_k-1':<10} {'x_k':<10} {'x_k+1':<10} {'f(x_k+1)':<10} {'|x_k+1-x_k|':<10}")
    
    x0 = choose_initial(eq['f'], a, b)
    x1 = x0 + 2 * eps
    
    if x1 > b:
        x1 = x0 - 2 * eps
    if x1 < a:
        x1 = x0 + 2 * eps
    
    x_prev, x_curr = x0, x1
    
    for i in range(max_iter):
        f_prev = eq['f'](x_prev)
        f_curr = eq['f'](x_curr)
        
        if abs(f_curr - f_prev) < 1e-15:
            x_curr = x_prev + eps
            f_curr = eq['f'](x_curr)
            if abs(f_curr - f_prev) < 1e-15:
                return None, i,
        
        x_next = x_curr - f_curr * (x_curr - x_prev) / (f_curr - f_prev)
        f_next = eq['f'](x_next)
        diff = abs(x_next - x_curr)
        
        print("\n" + f"{i+1:<8} {x_prev:<10.3f} {x_curr:<10.3f} {x_next:<10.3f} {f_next:<10.3f} {diff:<10.5f}")
        
        if diff < eps:
            return x_next, i+1, "OK"
        
        x_prev, x_curr = x_curr, x_next
    
    return x_curr, max_iter, "Достигнут лимит итераций"


def simple_iteration_method(eq, a, b, eps, max_iter=100):

    if 'phi' not in eq:
        return None, 0, "Не задана функция ф(x)"
    
    q_max = 0
    for x_test in [a, (a+b)/2, b]:
        try:
            dphi = (eq['phi'](x_test + 1e-5) - eq['phi'](x_test)) / 1e-5
            q_max = max(q_max, abs(dphi))
        except:
            pass
    
    print(f"q = |φ'(x)| = {q_max:.3f}")
    if q_max >= 1:
        print("Условие сходимости не выполняется")
        return None, 0, "Условие сходимости не выполняется"
    else:
        print("Условие сходимости выполняется")
    
    print("\n" + f"{'№ итер.':<8} {'x_k':<10} {'x_k+1':<10} {'f(x_k+1)':<10} {'|Δx|':<10}")
    
    x = choose_initial(eq['f'], a, b)
    print(f"{0:<8} {x:<10.3f} {'-':<10} {'-':<10} {'-':<10}")
    
    for i in range(max_iter):
        try:
            x_next = eq['phi'](x)
        except:
            return None, i, "Ошибка вычисления φ(x)"
        
        f_next = eq['f'](x_next)
        diff = abs(x_next - x)
        
        print(f"{i+1:<8} {x:<10.3f} {x_next:<10.3f} {f_next:<10.3f} {diff:<10.5f}")
        
        if diff < eps:
            return x_next, i+1, "OK"
        
        x = x_next
    
    return x, max_iter, "Достигнут лимит итераций"

def newton_system(system, x0, y0, eps, max_iter=100):
    
    print("\n" + f"{'№ итер.':<8} {'x_k':<10} {'y_k':<10} {'Δx':<8} {'Δy':<8} {'F1':<10} {'F2':<10}")
    
    x, y = x0, y0
    F1 = system['F1'](x, y)
    F2 = system['F2'](x, y)
    print(f"{0:<8} {x:<10.3f} {y:<10.3f} {'-':<8} {'-':<8} {F1:<10.3f} {F2:<10.5f}")
    
    for i in range(max_iter):
        F1 = system['F1'](x, y)
        F2 = system['F2'](x, y)
        
        if abs(F1) < eps and abs(F2) < eps:
            return (x, y), i, (0, 0), "OK"
        
        J11 = system['dF1dx'](x, y)
        J12 = system['dF1dy'](x, y)
        J21 = system['dF2dx'](x, y)
        J22 = system['dF2dy'](x, y)
        
        det = J11 * J22 - J12 * J21
        
        if abs(det) < 1e-15:
            return None, i, "Определитель близок к нулю"
        
        dx = (-F1 * J22 + F2 * J12) / det
        dy = (-J11 * F2 + J21 * F1) / det
        
        x_new = x + dx
        y_new = y + dy
        
        dx_abs = abs(dx)
        dy_abs = abs(dy)
        
        F1_new = system['F1'](x_new, y_new)
        F2_new = system['F2'](x_new, y_new)
        
        print(f"{i+1:<8} {x_new:<10.3f} {y_new:<10.3f} {dx_abs:<8.3f} {dy_abs:<8.3f} {F1_new:<10.3f} {F2_new:<10.5f}")
        
        if max(dx_abs, dy_abs) < eps:
            return (x_new, y_new), i+1, (dx_abs, dy_abs), "OK"
        
        x, y = x_new, y_new
    
    return (x, y), max_iter, (dx_abs, dy_abs), "достигнут лимит итераций"


def main():
    while True:
        print("1 - решение нелинейного уравнения")
        print("2 - решение системы нелинейных уравнений")
        print("0 - выход")
        
        choice = input("\nваш выбор: ")
        
        if choice == "0":
            break
        
        elif choice == "1":
            print("доступные уравнения:")
            for i, eq in enumerate(equations):
                print(f"{i+1}. {eq['name']}")
            
            try:
                eq_idx = int(input("выберите номер уравнения: ")) - 1
                if eq_idx < 0 or eq_idx >= len(equations):
                    print("Уравнения с таким номером нет")
                    continue
                
                eq = equations[eq_idx]
                
                source = input("Ввести данные с клавиатуры (k) или из файла (f)? ")
                
                if source.lower() == 'f':
                    a, b, eps = read_from_file()
                    if a is None:
                        continue
                else:
                    a = float(input("Левая граница a: "))
                    b = float(input("Правая граница b: "))
                    eps = float(input("Точность например 0.01: "))
                
                if not check_interval(eq['f'], a, b):
                    print("Попробуйте другой интервал")
                    continue
                
                print("\nДоступные методы:")
                print("1 - Метод половинного деления")
                print("4 - Метод секущих")
                print("5 - Метод простой итерации")
                
                method = input("Выберите метод: ")
                
                result = None
                iterations = 0
                status = ""
                
                if method == "1":
                    result, iterations, status = half_division_method(eq, a, b, eps)
                elif method == "4":
                    result, iterations, status = secant_method(eq, a, b, eps)
                elif method == "5":
                    result, iterations, status = simple_iteration_method(eq, a, b, eps)
                else:
                    print("Неверный метод")
                    continue
                
                if result is not None:
                    method_names = {"1": "половинного деления", "4": "секущих", "5": "простой итерации"}
                    output_text = f"""
                        Уравнение: {eq['name']}
                        Метод: {method_names[method]}
                        Интервал: [{a}, {b}]
                        Точность: {eps}

                        Найденный корень: x = {result:.3f}
                        Значение функции: f(x) = {eq['f'](result):.6f}
                        Число итераций: {iterations}
                        Статус: {status}
                        """
                    print(output_text)
                    
                    save = input("Сохранить результат в файл? y/n: ")
                    if save.lower() == 'y':
                        output_result(output_text, True)
                    
                    plot = input("Построить график? y/n: ")
                    if plot.lower() == 'y':
                        plot_function(eq, a, b)
                else:
                    print(f"Ошибка: {status}")
                    
            except Exception as e:
                print(f"Ошибка: {e}")
        
        elif choice == "2":
            print("Доступные системы:")
            for i, sys in enumerate(systems):
                print(f"{i+1}. {sys['name']}")
            
            try:
                sys_idx = int(input("Выберите номер системы: ")) - 1
                if sys_idx < 0 or sys_idx >= len(systems):
                    print("Систем с таким номером нет")
                    continue
                
                system = systems[sys_idx]
                
                x0 = float(input("Начальное приближение x0: "))
                y0 = float(input("Начальное приближение y0: "))
                eps = float(input("Точность (например 0.01): "))
                
                print("\nДоступные методы:")
                print("6 - Метод Ньютона")
                
                method = input("Выберите номер метода: ")
                
                result = None
                iterations = 0
                errors = None
                status = ""
                
                if method == "6":
                    result, iterations, errors, status = newton_system(system, x0, y0, eps)
                else:
                    print("Неверный номер метода")
                    continue
                
                if result is not None:
                    x, y = result
                    dx, dy = errors if errors else (0, 0)
                    
                    output_text = f"""
                        Система: {system['name']}
                        Метод: Ньютона
                        Начальное приближение: x0 = {x0}, y0 = {y0}
                        Точность: {eps}

                        Найденное решение:
                        x = {x:.3f}
                        y = {y:.3f}

                        Проверка:
                        F1(x,y) = {system['F1'](x, y):.6f}
                        F2(x,y) = {system['F2'](x, y):.6f}

                        Погрешности на последней итерации:
                        |x_k+1 - x_k| = {dx:.8f}
                        |y_k+1 - y_k| = {dy:.8f}

                        Число итераций: {iterations}
                        Статус: {status}
                        """
                    print(output_text)
                    
                    save = input("Сохранить результат в файл? y/n: ")
                    if save.lower() == 'y':
                        output_result(output_text, True)
                    
                    plot = input("Построить график системы? y/n: ")
                    if plot.lower() == 'y':
                        x_range = [x0 - 2, x0 + 2]
                        y_range = [y0 - 2, y0 + 2]
                        plot_system(system, x_range, y_range)
                else:
                    print(f"Ошибка: {status}")
                    
            except Exception as e:
                print(f"Ошибка: {e}")

if __name__ == "__main__":
    main()