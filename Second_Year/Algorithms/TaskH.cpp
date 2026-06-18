#include <iostream>
#include <vector>

int rearrangement(std::vector<int>& products, int low, int high) {
  int j = low - 1;
  int pivot = products[high];
  for (int i = low; i < high; i++) {
    if (products[i] > pivot) {
      j++;
      std::swap(products[i], products[j]);
    }
  }
  std::swap(products[high], products[j + 1]);
  return j + 1;
}

void quickSort(std::vector<int>& products, int low, int high) {
  if (low < high) {
    int pivot_position = rearrangement(products, low, high);
    quickSort(products, pivot_position + 1, high);
    quickSort(products, low, pivot_position - 1);
  }
}

int main() {
  int n, k;
  int product;
  std::cin >> n >> k;
  std::vector<int> products;
  int min_sum = 0;
  int count = 0;

  for (int i = 0; i < n; i++) {
    std::cin >> product;
    products.push_back(product);
  }

  quickSort(products, 0, (int)products.size() - 1);

  for (int i = 0; i < (int)products.size(); i++) {
    count += 1;
    if (count % k != 0) {
      min_sum += products[i];
    }
  }
  std::cout << min_sum;

  return 0;
}

/*  for (size_t i = 0; i < products.size(); i++) {
    std::cout << products[i] << " ";
  }*/