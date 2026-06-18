#include <iostream>
#include <map>
#include <string>
#include <vector>

bool isNumber(const std::string& s) {
  int start_index;
  if (s[0] == '-') {
    start_index = 1;
  } else {
    start_index = 0;
  }
  for (int i = start_index; i < (int)s.size(); i++) {
    if (!isdigit(s[i]))
      return false;
  }
  return true;
}

int main() {
  std::string s;
  std::map<std::string, int> current_value;
  std::vector<std::vector<std::pair<std::string, int>>> history_stack;

  history_stack.push_back({});
  while (std::cin >> s) {
    if (s == "{") {
      history_stack.push_back({});
    } else if (s == "}") {
      auto& layer = history_stack.back();
      for (int i = layer.size() - 1; i >= 0; i--) {
        std::string name = layer[i].first;
        int old_value = layer[i].second;
        current_value[name] = old_value;
      }
      history_stack.pop_back();
    } else {
      size_t pos = s.find('=');
      std::string name = s.substr(0, pos);
      bool variable_exist = current_value.find(name) != current_value.end();
      if (!variable_exist) {
        current_value[name] = 0;
      }
      history_stack.back().push_back({name, current_value[name]});
      if (isNumber(s.substr(pos + 1))) {
        int value = stoi(s.substr(pos + 1));
        current_value[name] = value;
      } else {
        std::string variable_value = s.substr(pos + 1);
        if (current_value.find(variable_value) == current_value.end()) {
          current_value[variable_value] = 0;
        }
        current_value[name] = current_value[variable_value];
        std::cout << current_value[name] << std::endl;
      }
    }
  }

  return 0;
}

/*
*   int nesting_levels = 0;
  std::string s;
  std::map<std::string, std::vector<int>> variables;
if (s == "{") {
      nesting_levels += 1;
      for (auto& pair : variables) {
        if (!pair.second.empty()) {
          pair.second.push_back(pair.second.back());
        }
      }
    } else if (s == "}") {
      nesting_levels -= 1;
      for (auto& pair : variables) {
        pair.second.pop_back();
        if (pair.second.size() == 0) {
          pair.second.push_back(0);
        }
      }
    } else {
      size_t pos = s.find('=');
      std::string name = s.substr(0, pos);
      if (variables.find(name) == variables.end()) {
        for (int i = 0; i <= nesting_levels; i++) {
          variables[name].push_back(0);
        }
      }
      if (isNumber(s.substr(pos + 1))) {
        int value = stoi(s.substr(pos + 1));
        variables[name].back() = value;

      } else {
        std::string variable_value = s.substr(pos + 1);
        if (variables.find(variable_value) == variables.end()) {
          for (int i = 0; i <= nesting_levels; i++) {
            variables[variable_value].push_back(0);
          }
        }
        variables[name].back() = variables[variable_value].back();
        std::cout << variables[name].back() << std::endl;
      }
    }*/