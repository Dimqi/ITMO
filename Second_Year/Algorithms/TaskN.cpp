#include <iostream>
#include <vector>

using namespace std;

vector<int> pigs;
vector<int> state_pig;

int answ = 0;

void recursive_search_key(int num) {
  state_pig[num] = 1;

  int next_pig = pigs[num];

  if (state_pig[next_pig] == 0) {
    recursive_search_key(next_pig);
  } else if (state_pig[next_pig] == 1) {
    answ++;
  }

  state_pig[num] = 2;
}

int main() {
  int n, m;

  cin >> n;

  for (int i = 0; i < n; i++) {
    cin >> m;

    pigs.push_back(m - 1);
    state_pig.push_back(0);
  }

  for (int i = 0; i < n; i++) {
    if (state_pig[i] == 0) {
      recursive_search_key(i);
    }
  }

  cout << answ;

  return 0;
}