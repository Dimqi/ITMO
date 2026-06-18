#include <deque>
#include <iostream>
#include <vector>

int main() {
  int n, k, num;
  std::vector<int> numbers;
  std::deque<int> sort_ind_numbers;
  std::cin >> n >> k;

  for (int i = 0; i < n; i++) {
    std::cin >> num;
    numbers.push_back(num);
  }

  for (int i = 0; i < k; i++) {
    while (sort_ind_numbers.size() != 0 &&
           numbers[sort_ind_numbers.back()] >= numbers[i]) {
      sort_ind_numbers.pop_back();
    }
    sort_ind_numbers.push_back(i);
  }

  std::cout << numbers[sort_ind_numbers.front()] << " ";

  for (int i = k; i < n; i++) {
    if (sort_ind_numbers.front() < i - k + 1) {
      sort_ind_numbers.pop_front();
    }

    while (sort_ind_numbers.size() != 0 &&
           numbers[sort_ind_numbers.back()] >= numbers[i]) {
      sort_ind_numbers.pop_back();
    }
    sort_ind_numbers.push_back(i);

    std::cout << numbers[sort_ind_numbers.front()] << " ";
  }
  return 0;
}
