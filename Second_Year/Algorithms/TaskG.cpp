#include <algorithm>
#include <iostream>
#include <vector>

int rearrangement(std::vector<std::pair<int, int>>& weights, int low, int high) {
  int i = low - 1;
  int pivot = weights[high].second;
  for (int j = low; j < high; j++) {
    if (weights[j].second < pivot) {
      i++;
      std::swap(weights[j], weights[i]);
    }
  }
  std::swap(weights[i + 1], weights[high]);
  return i + 1;
}

void quickSort(std::vector<std::pair<int, int>>& weights, int low, int high) {
  if (low < high) {
    int pivot_position = rearrangement(weights, low, high);
    quickSort(weights, low, pivot_position - 1);
    quickSort(weights, pivot_position + 1, high);
  }
}

int main() {
  std::vector<int> letters(26);
  std::vector<std::pair<int, int>> letter_weight(26);
  std::string line;
  std::string result_line = "";
  std::string left = "";
  std::string right = "";
  std::cin >> line;
  int weight;

  for (int i = 0; i < (int)line.size(); i++) {
    letters[int(line[i] - 'a')] += 1;
  }

  for (int i = 0; i < 26; i++) {
    std::cin >> weight;
    letter_weight[i] = {i, weight};
  }

  for (int i = 0; i < (int)letters.size(); i++) {
    if (letters[i] % 2 != 0 && letters[i] != 0) {
      result_line += 'a' + i;
      letters[i] -= 1;
    }
    if (letters[i] / 2 != 1 && letters[i] != 0) {
      while (letters[i] / 2 != 1) {
        left.push_back(char('a' + letter_weight[i].first));
        right.push_back(char('a' + letter_weight[i].first));
        letters[i] -= 2;
      }
    }
  }

  quickSort(letter_weight, 0, (int)letter_weight.size() - 1);

  for (int i = 0; i < (int)letter_weight.size(); i++) {
    if (letters[letter_weight[i].first] != 0) {
      left.push_back(char('a' + letter_weight[i].first));
      right.push_back(char('a' + letter_weight[i].first));
      letters[letter_weight[i].first] -= 2;
    }
  }
  std::reverse(left.begin(), left.end());
  std::cout << left;
  std::cout << result_line;
  std::cout << right;

  return 0;
}

/*for (int i = 0; i < 26; i++) {
    std::cin >> weight;
    if (letter_weight.size() != 0 && prev_weight != 0) {
      if (weight > prev_weight) {
        letter_weight.insert(letter_weight.begin() + last_position + 1, {weight,
  i});

      }
    }

  }*/


/*
*/

/*  for (int i = 25; i >= 0; i--) {
    if (letters[i] == 0) {
      letter_weight.erase(letter_weight.begin() + i);
    }
  }*/