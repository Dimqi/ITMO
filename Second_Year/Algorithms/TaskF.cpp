#include <iostream>
#include <vector>

int main() {
  std::string n;
  std::vector<std::string> max_number;
  std::string result;
  bool pose = false;
  int index = 0;

  while (std::cin >> n) {
    pose = false;
    index = (int)max_number.size();

    for (int i = 0; i < (int)max_number.size(); i++) {
      std::string s_prev = max_number[i];
      std::string s_curr = n;

      int prev_size = (int)s_prev.size();
      int curr_size = (int)s_curr.size();

      for (int j = 0; j < curr_size + prev_size; j++) {
        char c1;
        if (j < curr_size) {
          c1 = s_curr[j];
        } else {
          c1 = s_prev[j - curr_size];
        }

        char c2;
        if (j < prev_size) {
          c2 = s_prev[j];
        } else {
          c2 = s_curr[j - prev_size];
        }

        if (c1 > c2) {
          index = i;
          pose = true;
          break;
        }
        if (c1 < c2) {
          break;
        }
      }
      if (pose) {
        break;
      }
    }
    max_number.insert(max_number.begin() + index, n);
  }

  for (int i = 0; i < (int)max_number.size(); i++) {
    result += max_number[i];
  }

  std::cout << result;

  return 0;
}