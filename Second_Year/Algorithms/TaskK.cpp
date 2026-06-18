#include <iostream>
#include <map>
#include <set>

using namespace std;

int main() {
  long n;
  int m;
  cin >> n >> m;

  set<pair<long, long>> memory_start;
  set<pair<long, long>> memory_len;
  map<int, pair<long, long>> history;

  memory_start.insert({1, n});
  memory_len.insert({n, -1});

  for (int i = 1; i <= m; i++) {
    long req;
    cin >> req;

    if (req > 0) {
      if (memory_len.empty() || memory_len.rbegin()->first < req) {
        cout << -1 << '\n';
        history[i] = {-1, -1};
        continue;
      }

      set<pair<long, long>>::iterator it = prev(memory_len.end());
      long len = it->first;
      long start = -(it->second);

      cout << start << '\n';
      history[i] = {start, req};

      memory_start.erase({start, len});
      memory_len.erase(it);

      if (len > req) {
        long new_start = start + req;
        long new_len = len - req;

        memory_start.insert({new_start, new_len});
        memory_len.insert({new_len, -new_start});
      }
    } else {
      int t = -req;
      map<int, pair<long, long>>::iterator itHist = history.find(t);

      if (itHist == history.end() || itHist->second.first == -1)
        continue;

      long start = itHist->second.first;
      long length = itHist->second.second;

      long new_start = start;
      long new_len = length;

      set<pair<long, long>>::iterator it = memory_start.lower_bound({start, 0});
      if (it != memory_start.end()) {
        if (start + length == it->first) {
          new_len += it->second;
          memory_len.erase({it->second, -(it->first)});
          memory_start.erase(it);
        }
      }

      it = memory_start.lower_bound({start, 0});
      if (it != memory_start.begin()) {
        set<pair<long, long>>::iterator itL = prev(it);
        if (itL->first + itL->second == start) {
          new_start = itL->first;
          new_len += itL->second;
          memory_len.erase({itL->second, -(itL->first)});
          memory_start.erase(itL);
        }
      }

      memory_start.insert({new_start, new_len});
      memory_len.insert({new_len, -new_start});
    }
  }
  return 0;
}