#include <iostream>
#include <vector>

std::vector<int> graph[105];
std::vector<int> groups;

int search_next_group(int group) {
  int next_group;
  if (group == 1) {
    next_group = 2;
  } else {
    next_group = 1;
  }
  return next_group;
}

bool recursive_search_pair(int stud, int group) {
  groups[stud] = group;

  for (int i = 0; i < (int)graph[stud].size(); i++) {
    int next_stud = graph[stud][i];

    if (groups[next_stud] == 0) {
      int next_group = search_next_group(group);

      if (!recursive_search_pair(next_stud, next_group)) {
        return false;
      }
    } else if (groups[next_stud] == groups[stud]) {
      return false;
    }
  }
  return true;
}

int main() {
  int n, m;
  std::cin >> n >> m;

  for (int i = 0; i < m; i++) {
    int fir, sec;
    std::cin >> fir >> sec;

    graph[fir - 1].push_back(sec - 1);
    graph[sec - 1].push_back(fir - 1);
  }

  for (int i = 0; i < n; i++) {
    groups.push_back(0);
  }

  for (int i = 0; i < n; i++) {
    if (groups[i] == 0) {
      if (!recursive_search_pair(i, 1)) {
        std::cout << "NO";
        return 0;
      }
    }
  }

  std::cout << "YES";
  return 0;
}