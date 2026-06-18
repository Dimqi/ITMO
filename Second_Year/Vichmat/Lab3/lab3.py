import math

functions = {
    1: ("2*x**3 - 3*x**2 + 7*x - 10", lambda x: 2*x**3 - 3*x**2 + 7*x - 10),
    2: ("sin(x)", lambda x: math.sin(x)),
    3: ("cos(x)", lambda x: math.cos(x)),
    4: ("x**2", lambda x: x**2),
    5: ("exp(x)", lambda x: math.exp(x)),
    6: ("1/x (разрыв в 0)", lambda x: 1/x if x != 0 else float('inf')),
    7: ("1/sqrt(x) (разрыв в 0)", lambda x: 1/math.sqrt(x) if x > 0 else float('inf')),
    8: ("1/(x-2) (разрыв в 2)", lambda x: 1/(x-2) if x != 2 else float('inf')),
    9: ("1/(x-1)**2 (разрыв в 1)", lambda x: 1/(x-1)**2 if x != 1 else float('inf'))
}

methods = {
    1: ("Метод левых прямоугольников", "rectangle_left", 1),
    2: ("Метод правых прямоугольников", "rectangle_right", 1),
    3: ("Метод средних прямоугольников", "rectangle_middle", 2),
    4: ("Метод трапеций", "trapezoidal", 2),
    5: ("Метод Симпсона", "simpson", 4)
}

class NumericalIntegrator:
    def __init__(self, func, a, b, eps, n_start=4):
        self.func = func
        self.a = a
        self.b = b
        self.eps = eps
        self.n_start = n_start

    def rectangle_left(self, n):
        h = (self.b - self.a) / n
        x = self.a
        integral = 0
        for i in range(n):
            val = self.func(x)
            if val == float('inf') or val == float('-inf'):
                return float('inf')
            integral += val
            x += h
        return integral * h

    def rectangle_right(self, n):
        h = (self.b - self.a) / n
        x = self.a + h
        integral = 0
        for i in range(n):
            val = self.func(x)
            if val == float('inf') or val == float('-inf'):
                return float('inf')
            integral += val
            x += h
        return integral * h

    def rectangle_middle(self, n):
        h = (self.b - self.a) / n
        x = self.a + h / 2
        integral = 0
        for i in range(n):
            val = self.func(x)
            if val == float('inf') or val == float('-inf'):
                return float('inf')
            integral += val
            x += h
        return integral * h

    def trapezoidal(self, n):
        h = (self.b - self.a) / n
        left = self.func(self.a)
        right = self.func(self.b)
        if left == float('inf') or left == float('-inf') or right == float('inf') or right == float('-inf'):
            return float('inf')
        integral = (left + right) / 2
        x = self.a + h
        for i in range(1, n):
            val = self.func(x)
            if val == float('inf') or val == float('-inf'):
                return float('inf')
            integral += val
            x += h
        return integral * h

    def simpson(self, n):
        if n % 2 != 0:
            n += 1
        h = (self.b - self.a) / n
        left = self.func(self.a)
        right = self.func(self.b)
        if left == float('inf') or left == float('-inf') or right == float('inf') or right == float('-inf'):
            return float('inf')
        integral = left + right

        x = self.a + h
        for i in range(1, n, 2):
            val = self.func(x)
            if val == float('inf') or val == float('-inf'):
                return float('inf')
            integral += 4 * val
            x += 2 * h

        x = self.a + 2 * h
        for i in range(2, n - 1, 2):
            val = self.func(x)
            if val == float('inf') or val == float('-inf'):
                return float('inf')
            integral += 2 * val
            x += 2 * h

        return integral * h / 3

    def runge_rule(self, method, n, k):
        I_n = method(n)
        I_2n = method(2 * n)
        if I_n == float('inf') or I_2n == float('inf'):
            return float('inf'), float('inf')
        error = abs(I_2n - I_n) / (2 ** k - 1)
        return I_2n, error

    def compute(self, method, p, max_iter=20):
        history = []
        n = self.n_start
        
        I_start = method(n)
        if I_start == float('inf'):
            return [], float('inf'), n
        
        history.append({
            "n": n,
            "value": I_start,
            "error": None
        })
        
        for iteration in range(max_iter):
            I_2n, error = self.runge_rule(method, n, p)
            
            if I_2n == float('inf'):
                return history, float('inf'), 2 * n
            
            history.append({
                "n": 2 * n,
                "value": I_2n,
                "error": error
            })
            
            if error < self.eps:
                return history, I_2n, 2 * n
            
            n *= 2
            if n > 2 ** max_iter:
                return history, I_2n, n
        
        return history, I_2n, n

    def find_discontinuity(self):
        for point in [self.a, self.b, (self.a + self.b) / 2]:
            try:
                val = self.func(point)
                if abs(val) > 1e10:
                    return point
            except:
                return point
        
        for i in range(1, 100):
            point = self.a + i * (self.b - self.a) / 100
            try:
                if abs(self.func(point)) > 1e10:
                    return point
            except:
                return point
        return None

    def compute_improper(self, method, p, max_iter=20):
        c = self.find_discontinuity()
        if c is None:
            return self.compute(method, p, max_iter)
        
        original_a, original_b = self.a, self.b
        delta = 1e-7
        

        if abs(c - self.a) < 1e-12:
            for _ in range(8):
                self.a = original_a + delta
                if self.a >= self.b:
                    delta *= 0.5
                    continue
                hist, res, n_f = self.compute(method, p, max_iter)
                if res != float('inf'):
                    self.a, self.b = original_a, original_b
                    return hist, res, n_f
                delta *= 0.1

            self.a, self.b = original_a, original_b
            return [], float('inf'), 0
        

        if abs(c - self.b) < 1e-12:
            for _ in range(8):
                self.b = original_b - delta
                if self.b <= self.a:
                    delta *= 0.5
                    continue
                hist, res, n_f = self.compute(method, p, max_iter)
                if res != float('inf'):
                    self.a, self.b = original_a, original_b
                    return hist, res, n_f
                delta *= 0.1
            self.a, self.b = original_a, original_b
            return [], float('inf'), 0
        

        for _ in range(8):
            left_b = c - delta
            right_a = c + delta
            if left_b <= self.a or right_a >= self.b:
                delta *= 0.5
                continue
            
            total_hist = []
            total_res = 0
            ok = True
            
            self.a, self.b = original_a, left_b
            hist1, res1, _ = self.compute(method, p, max_iter)
            if res1 != float('inf'):
                total_res += res1
                total_hist.extend(hist1)
            else:
                ok = False
            
            self.a, self.b = right_a, original_b
            hist2, res2, _ = self.compute(method, p, max_iter)
            if res2 != float('inf'):
                total_res += res2
                total_hist.extend(hist2)
            else:
                ok = False
            
            self.a, self.b = original_a, original_b
            
            if ok and total_res != float('inf'):
                return total_hist, total_res, max(len(hist1), len(hist2)) * 2
            
            delta *= 0.5
        
        return [], float('inf'), 0


def main():
    print("Численное интегрирование")

    print("\nДоступные функции:")
    for key, (desc, _) in functions.items():
        print(f"{key}. {desc}")

    while True:
        try:
            func_choice = int(input("\nВыберите функцию (1-9): "))
            if func_choice in functions:
                break
            print("Ошибка: введите число от 1 до 9")
        except ValueError:
            print("Ошибка: введите целое число")

    func_desc, func = functions[func_choice]
    print(f"Выбрана функция: f(x) = {func_desc}")

    while True:
        try:
            a = float(input("\nВведите нижний предел a: "))
            b = float(input("Введите верхний предел b: "))
            if a < b:
                break
            print("Ошибка: a должно быть меньше b")
        except ValueError:
            print("Ошибка: введите число")

    while True:
        try:
            eps = float(input("\nВведите точность вычисления (например, 0.001): "))
            if eps > 0:
                break
            print("Ошибка: точность должна быть положительной")
        except ValueError:
            print("Ошибка: введите число")

    print("\nДоступные методы:")
    for key, (name, _, _) in methods.items():
        print(f"{key}. {name}")

    while True:
        try:
            method_choice = int(input("\nВыберите метод (1-5): "))
            if 1 <= method_choice <= 5:
                break
            print("Ошибка: введите число от 1 до 5")
        except ValueError:
            print("Ошибка: введите целое число")

    integrator = NumericalIntegrator(func, a, b, eps)

    print("Результаты:")

    name, method_str, p = methods[method_choice]
    method = getattr(integrator, method_str)

    c = integrator.find_discontinuity()
    if c is not None:
        if abs(c - a) < 1e-12 and "1/x" in func_desc and "sqrt" not in func_desc:
            print("Интеграл не существует (расходится)")
            return
        if a < c < b and ("1/(x-2)" in func_desc or "1/(x-1)**2" in func_desc):
            print("Интеграл не существует (расходится)")
            return

    history, result, n_final = integrator.compute_improper(method, p)

    if result == float('inf'):
        print("Интеграл не существует")
    else:
        print(f"Начальное n = {integrator.n_start}")

        for step in history:
            if step["error"] is None:
                print(f"n = {step['n']:4d} | Значение = {step['value']:.8f} | Погрешность = (нет данных)")
            else:
                print(f"n = {step['n']:4d} | Значение = {step['value']:.8f} | Погрешность = {step['error']:.5f}")

        print(f"\nТребуемая точность {eps} достигнута при n = {n_final}")
        print(f"Значение интеграла: {result:.8f}")


if __name__ == "__main__":
    main()