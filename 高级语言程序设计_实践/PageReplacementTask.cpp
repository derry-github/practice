#include <iostream>
#include <iomanip>

using namespace std;

class PageManager {
public:
    int blocks[5];      // 物理块
    int capacity;        // 物理块总数
    int size = 0;        // 当前已占用块数
    int nextReplace = 0; // 下一个要替换的位置 (FIFO 核心)
    int faultCount = 0;  // 缺页次数

    PageManager(int cap) : capacity(cap) {
        for (int i = 0; i < 5; i++) blocks[i] = -1; // -1 表示空块
    }

    // 检查页面是否已在内存中
    bool isHit(int page) {
        for (int i = 0; i < size; i++) {
            if (blocks[i] == page) return true;
        }
        return false;
    }

    // 访问页面
    void access(int page) {
        bool hit = isHit(page);
        if (!hit) {
            faultCount++;
            if (size < capacity) {
                blocks[size++] = page;
            } else {
                blocks[nextReplace] = page;
                nextReplace = (nextReplace + 1) % capacity;
            }
        }

        cout << "访问 " << page << " | 物理块: ";
        for (int i = 0; i < capacity; i++) {
            if (blocks[i] == -1) cout << "[ ] ";
            else cout << "[" << blocks[i] << "] ";
        }
        cout << (hit ? "(🎯)" : "(❌)") << endl;
    }

    void showResult(int total) {
        if (total == 0) return;
        cout << "\n总访问次数: " << total << endl;
        cout << "总缺页次数: " << faultCount << endl;
        cout << fixed << setprecision(2);
        cout << "缺页率: " << (float)faultCount / total * 100 << "%" << endl;
    }
};

int main() {
    int pages[100];
    int total = 0;
    int n;

    // 1. 获取物理块数（限制最大为5）
    while (true) {
        cout << "请输入物理块个数 (1-5): ";
        if (cin >> n && n >= 1 && n <= 5) break;
        cout << "输入无效，请输入 1 到 5 之间的整数。" << endl;
        cin.clear();
    }

    // 2. 获取页面序列（空格分隔，回车结束）
    cout << "请输入页面序列（空格分隔，回车结束）: ";
    int val;
    while (cin >> val) {
        if (total < 100) pages[total++] = val;
        while (cin.peek() == ' ') cin.ignore();
        if (cin.peek() == '\n') break;
    }

    PageManager pm(n);
    cout << "\n--- FIFO 页面置换开始 ---" << endl;
    for (int i = 0; i < total; i++) {
        pm.access(pages[i]);
    }

    pm.showResult(total);
    return 0;
}
