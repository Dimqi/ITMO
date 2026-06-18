#include <iostream>
#include <queue>
#include <set>
#include <unordered_map>
#include <vector>

int main() {
  int n, k, p;
  std::cin >> n >> k >> p;

  std::vector<int> actions(p);
  std::vector<std::vector<int>> car_position(n + 1);
  std::vector<int> curr_pos_car(n + 1, 0);

  std::set<int> floor;
  std::set<std::pair<int, int>> priority;
  std::unordered_map<int, int> in_cache;

  int count_action = 0;

  for (int i = 0; i < p; i++) {
    std::cin >> actions[i];
    car_position[actions[i]].push_back(i);
  }

  auto get_next = [&](int car) {
    if (curr_pos_car[car] >= (int)car_position[car].size()) {
      return 100000000;
    }
    return car_position[car][curr_pos_car[car]];
  };

  for (int i = 0; i < p; i++) {
    int cur_car = actions[i];

    if (curr_pos_car[cur_car] < (int)car_position[cur_car].size() &&
        car_position[cur_car][curr_pos_car[cur_car]] == i) {
      curr_pos_car[cur_car]++;
    }

    int dist_cur = get_next(cur_car);

    if (floor.count(cur_car)) {
      if (in_cache.count(cur_car)) {
        priority.erase({in_cache[cur_car], cur_car});
      }

      in_cache[cur_car] = dist_cur;
      priority.insert({dist_cur, cur_car});
      continue;
    }

    count_action++;

    if ((int)floor.size() < k) {
      floor.insert(cur_car);
      in_cache[cur_car] = dist_cur;
      priority.insert({dist_cur, cur_car});
    } else {
      auto it = prev(priority.end());
      int victim = it->second;

      priority.erase(it);
      floor.erase(victim);
      in_cache.erase(victim);

      floor.insert(cur_car);
      in_cache[cur_car] = dist_cur;
      priority.insert({dist_cur, cur_car});
    }
  }

  std::cout << count_action << "\n";
}