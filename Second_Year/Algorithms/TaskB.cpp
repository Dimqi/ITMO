#include <iostream>
#include <map>
#include <vector>

int main() {
  std::string s;
  std::cin >> s;

  std::vector<char> stack;
  std::vector<int> animals_stack;
  std::vector<int> trap_stack;
  std::map<int, int> trap_animals_position;

  bool possible = true;
  int animals_position_count = 0;
  int trap_position_count = 0;

  for (int i = 0; i < (int)s.length(); i++) {
    if (int(s[i]) > 90) {
      animals_position_count += 1;
      animals_stack.push_back(animals_position_count);
    } else {
      trap_position_count += 1;
      trap_stack.push_back(trap_position_count);
    }

    if (!stack.empty() && abs(stack[stack.size() - 1] - s[i]) == 32) {
      stack.pop_back();
      trap_animals_position[trap_stack.back()] = animals_stack.back();
      trap_stack.pop_back();
      animals_stack.pop_back();
    } else {
      stack.push_back(s[i]);
    }
  }

  possible = stack.empty();

  if (possible) {
    std::cout << "Possible\n";
    for (int i = 0; i < (int)trap_animals_position.size(); i++) {
      std::cout << trap_animals_position[i + 1] << " ";
    }
  } else {
    std::cout << "Impossible\n";
  }

  return 0;
}
