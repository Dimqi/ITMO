#include <algorithm>
#include <iostream>
#include <vector>

using namespace std;

int city_count;
int map[1000][1000];
bool is_reach[1000];

void check_outbound(int curr_city, int tank_vol) {
  is_reach[curr_city] = true;
  for (int next_city = 0; next_city < city_count; next_city++) {
    if (!is_reach[next_city] && map[curr_city][next_city] <= tank_vol) {
      check_outbound(next_city, tank_vol);
    }
  }
}

void check_inbound(int curr_city, int tank_vol) {
  is_reach[curr_city] = true;
  for (int next_city = 0; next_city < city_count; next_city++) {
    if (!is_reach[next_city] && map[next_city][curr_city] <= tank_vol) {
      check_inbound(next_city, tank_vol);
    }
  }
}

bool can_connect_all(int tank_vol) {
  for (int i = 0; i < city_count; i++)
    is_reach[i] = false;

  check_outbound(0, tank_vol);

  for (int i = 0; i < city_count; i++) {
    if (!is_reach[i])
      return false;
  }

  for (int i = 0; i < city_count; i++)
    is_reach[i] = false;

  check_inbound(0, tank_vol);

  for (int i = 0; i < city_count; i++) {
    if (!is_reach[i])
      return false;
  }

  return true;
}

int find_min_fuel(const vector<int>& prices, int low_idx, int high_idx) {
  if (low_idx == high_idx)
    return prices[low_idx];

  int mid_idx = low_idx + (high_idx - low_idx) / 2;

  if (can_connect_all(prices[mid_idx])) {
    return find_min_fuel(prices, low_idx, mid_idx);
  } else {
    return find_min_fuel(prices, mid_idx + 1, high_idx);
  }
}

int main() {
  cin >> city_count;

  vector<int> unique_prices;
  unique_prices.reserve(city_count * city_count);
  unique_prices.push_back(0);

  for (int i = 0; i < city_count; i++) {
    for (int j = 0; j < city_count; j++) {
      cin >> map[i][j];
      unique_prices.push_back(map[i][j]);
    }
  }

  sort(unique_prices.begin(), unique_prices.end());
  unique_prices.erase(unique(unique_prices.begin(), unique_prices.end()), unique_prices.end());

  cout << find_min_fuel(unique_prices, 0, unique_prices.size() - 1);

  return 0;
}