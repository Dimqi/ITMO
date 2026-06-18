#include <iostream>
#include <queue>

int main() {
  std::deque<int> queue_start;
  std::deque<int> queue_end;

  int n;
  char com;
  int goblin;
  std::cin >> n;

  for (int i = 0; i < n; i++) {
    std::cin >> com;
    if (com != '-') {
      std::cin >> goblin;
      if (com == '+') {
        if (queue_start.size() != 0) {
          queue_end.push_back(goblin);
          if (queue_end.size() > queue_start.size()) {
            int temp_num = queue_end.front();
            queue_end.pop_front();
            queue_start.push_back(temp_num);
          }
        } else {
          queue_start.push_back(goblin);
        }
      } else {
        queue_start.push_back(goblin);
        if (queue_start.size() > queue_end.size() + 1) {
          int temp_num = queue_start.back();
          queue_end.push_front(temp_num);
          queue_start.pop_back();
        }
      }
    } else {
      std::cout << queue_start.front() << "\n";
      queue_start.pop_front();
      if (queue_end.size() > queue_start.size()) {
        int temp_num = queue_end.front();
        queue_end.pop_front();
        queue_start.push_back(temp_num);
      }
    }
  }
  return 0;
}
