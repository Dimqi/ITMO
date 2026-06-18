#include <iostream>
#include <vector>

int main() {
  int n, k;
  std::vector<int> corral;
  std::vector<int> distance;
  std::cin >> n >> k;
  int max = 0;
  int binary_distance;

  for (int i = 0; i < n; i++) {
    int a;
    std::cin >> a;
    corral.push_back(a);
  }

  int min_distance = 1;
  int max_distance = corral.back() - corral[0];

  for (int i = 1; i < n; i++) {
    distance.push_back(corral[i] - corral[i - 1]);
  }
  if (k == 2) {
    std::cout << max_distance;
    return 0;
  }

  while (min_distance <= max_distance) {
    binary_distance = (max_distance + min_distance) / 2;
    int current_distance = 0;
    int cows = k - 1;
    for (int j = 1; j < n; j++) {
      if (current_distance + distance[j - 1] >= binary_distance) {
        cows -= 1;
        current_distance = 0;
      } else {
        current_distance += distance[j - 1];
      }
    }
    if (cows <= 0) {
      min_distance = binary_distance + 1;
      if (binary_distance > max) {
        max = binary_distance;
      }
    } else {
      max_distance = binary_distance - 1;
    }
  }

  std::cout << max;

  return 0;
}

/*  for (int i = 0; i < (corral.size()-1) %2; i++) {
    sum_start += corral[i];
    sum_end += corral[corral.size() - 1 - i];

    int diff = abs(sum_start - sum_end);
    if (diff < min_diff) {
      min_diff = diff;
    }


  }*/