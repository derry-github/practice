#include <iostream>

using namespace std;

// 节点类
class Node {
public:
    int data;
    Node *prev = nullptr;
    Node *next = nullptr;
    Node(int val) : data(val) {}
};

// 双向循环链表类
class LinkedList {
public:
    Node* head; // 头结点

    LinkedList() {
        head = new Node(0);
        head->next = head->prev = head; // 自己指向自己形成环
    }

    // 1. 创建链表
    void create() {
        cout << "请输入数字（空格分隔，回车结束）: ";
        int val;
        while (cin >> val) {
            add(val);
            while (cin.peek() == ' ') cin.ignore(); // 空格则跳过
            if (cin.peek() == '\n') break; // 回车则结束
        }
    }

    // 2. 增加节点（尾插法）
    void add(int val) {
        Node* newNode = new Node(val);
        newNode->next = head;
        newNode->prev = head->prev;
        head->prev->next = newNode;
        head->prev = newNode;
    }

    // 3. 通用删除方法 (isForward=true 为正向，false 为反向)
    bool remove(int n, bool isForward) {
        if (head->next == head || n <= 0) return false;

        // 定位到头节点
        Node* curr = head;

        // 移动到第 N 个位置
        for (int i = 0; i < n; i++) {
            curr = isForward ? curr->next : curr->prev;
            if (curr == head) return false; // 越界
        }

        // 删除节点
        curr->prev->next = curr->next;
        curr->next->prev = curr->prev;
        delete curr;
        return true;
    }

    void print() {
        cout << "当前链表: ";
        Node* curr = head->next;
        if (curr == head) { cout << "空" << endl; return; }
        while (curr != head) {
            cout << curr->data << " ";
            curr = curr->next;
        }
        cout << endl;
    }
};

int main() {
    LinkedList list;
    list.create();
    list.print();

    int choice, n;
    while (cout << "\n1.插入元素 2.删除元素（正向） 3.删除元素（反向） 4.退出并返回头节点\n选择: " && cin >> choice && choice != 4) {
        if (choice == 1) {
            cout << "数: "; cin >> n;
            list.add(n);
        } else if (choice == 2 || choice == 3) {
            cout << "位置 N: "; cin >> n;
            if (!list.remove(n, choice == 2)) cout << "失败!" << endl;
        }
        list.print();
    }
    if (list.head->next != list.head) cout << "头节点: " << list.head->next->data << endl;
    return 0;
}
