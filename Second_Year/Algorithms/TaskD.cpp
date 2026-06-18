#include <iostream>

int main() {
  int a, b, c, d;
  long long k;
  std::cin >> a >> b >> c >> d >> k;

  long long ak = a;
  bool stable = d * (b - 1) >= c;

  for (long long i = 0; i < k; i++) {
    long long ak_old = ak;
    ak = ak * b - c;
    if (ak >= d && stable) {
      ak = d;
      break;
    } else if (ak <= 0) {
      ak = 0;
      break;
    } else if (ak_old == ak) {
      break;
    }
  }

  std::cout << ak << std::endl;
  return 0;
}

/*
int main() {
  int a, b, c, d;
  long long k;
  std::cin >> a >> b >> c >> d >> k;

  long long ak = a;
  bool stable = d * (b - 1) >= c;

  if (b == 1) {
    ak = a - c * k;
    ak = (ak + d - std::abs(ak - d)) / 2;
    if (ak < 0) {
      ak = 0;
    }
  } else {
    for (long long i = 0; i < k; i++) {
      ak = ak * b - c;
      if (ak >= d && stable) {
        ak = d;
        break;
      } else if (ak <= 0) {
        ak = 0;
        break;
      }
    }
  }

  std::cout << ak << std::endl;
  return 0;
}
*/

/*long long log_pow(long long b, long long k) {
  long long res = 1;
  while (k > 0) {
    if (k % 2 == 1)
      res *= b;
    b *= b;
    k /= 2;
  }
  return res;
}*/