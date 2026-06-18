#include <iostream>
#include <vector>
int main() {
  std::string s;
  std::cin >> s;

  int animals[26];
  int trap[26];

  bool possible = true;

  for (int i = 0; i < 26; i++) {
    animals[i] = -1;
    trap[i] = -1;
  }

  for (int i = 0; i < s.length(); i++) {
    int index = s[i] - 'a';
    if (int(s[i]) <= 90) {
      index = s[i] - 'A';
      trap[index] = i;
    } else {
      index = s[i] - 'a';
      animals[index] = i;
    }
  }

  std::cout << "\n--- Итоговое содержимое ---" << std::endl;
  std::cout << "trap (ловушки):" << std::endl;
  for (int i = 0; i < 26; i++) {
    if (trap[i] != -1) {
      std::cout << "  trap[" << i << "] (" << char('A' + i) << ") = " << trap[i]
                << std::endl;
    }
  }

  std::cout << "animals (животные):" << std::endl;
  for (int i = 0; i < 26; i++) {
    if (animals[i] != -1) {
      std::cout << "  animals[" << i << "] (" << char('a' + i)
                << ") = " << animals[i] << std::endl;
    }
  }

  for (int i = 0; i < 26; i++) {
    if ((abs(animals[i] - trap[i]) % 2 == 0) and animals[i] != -1 and
        trap[i] != 1) {
      possible = false;
    }
  }

  if (possible) {
    std::cout << "Possible\n";
    for (int i = 0; i < 26; i++) {
      if (trap[i] != -1) {
        std::cout << animals[i-s.length()/2];
      }
    }
  } else {
    std::cout << "Impossible\n";
  }
  return 0;
}