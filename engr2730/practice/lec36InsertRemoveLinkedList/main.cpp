#include <iostream>

using namespace std;

class Node{
public:

int getData() const { return m_data; }
void setData(const int & newdata) { m_data = newdata; }
Node *getNextPtr() const { return m_nextPtr; }
void setNextPtr(Node *newPtr) { m_nextPtr = newPtr; }

private:
int m_data; /* payload */
Node *m_nextPtr; /* pointer to the next node */
};

typedef Node * NodePtr;

void printNodes(Node *startPtr) {
    for (Node *currentPtr = startPtr; currentPtr != nullptr; currentPtr = currentPtr->getNextPtr()){
        cout << currentPtr->getData() << " " << endl;
    }
}

void insertNodeInLinkedList(NodePtr *ptr2startPtr, int value)
{
    NodePtr newNodePtr; /* will point to newly created node */
    NodePtr beforeNodePtr, afterNodePtr;

    /* dynamically allocate memory for new node */
    newNodePtr = new Node;

    /* insert node in list */
    if (newNodePtr != nullptr) /* if successfully allocated memory */
    {
        newNodePtr->setData(value);
        newNodePtr->setNextPtr(nullptr);

        /* determine where node should be inserted    */
        /* by defining beforeNodePtr and afterNodePtr */
        beforeNodePtr = nullptr;
        afterNodePtr = *ptr2startPtr;
        while (afterNodePtr != nullptr && afterNodePtr->getData() < value)
        {
            beforeNodePtr = afterNodePtr;
            afterNodePtr = afterNodePtr->getNextPtr();
        }

        /* case 1: insert at beginning of list (beforeNodePtr is still NULL) */
        if (beforeNodePtr == NULL)
        {
            newNodePtr->setNextPtr(*ptr2startPtr); /* set link to prior start */
            *ptr2startPtr = newNodePtr;       /* set new value for the startPtr */
        }
        else /* case 2: insert in middle or at end of list */
        {
            beforeNodePtr->setNextPtr(newNodePtr);
            newNodePtr->setNextPtr(afterNodePtr);
        }
    }
    else
    {
        cout << "Error allocating memory for new node of linked list." << endl ;
    }
}

void deleteNodeInLinkedList(NodePtr *ptr2startPtr, int value)
{
    NodePtr nodeToDeletePtr; /* will point to node to be deleted */
    NodePtr priorNodePtr;    /* will point to node immediately preceding node to be deleted */

    /* 1. determine which node should be deleted by defining nodeToDeletePtr and priorNodePtr */
    nodeToDeletePtr = *ptr2startPtr;
    priorNodePtr = nullptr;
    while (nodeToDeletePtr != nullptr && nodeToDeletePtr->getData() != value)
    {
        priorNodePtr = nodeToDeletePtr;
        nodeToDeletePtr = nodeToDeletePtr->getNextPtr();
    }

    /* 2. delete node */
    if (nodeToDeletePtr == nullptr) /* empty list or node not found */
    {
        return;
    }
    else if (priorNodePtr == nullptr) /* first node should be deleted */
    {
        *ptr2startPtr = (*ptr2startPtr)->getNextPtr(); /* move startPtr to second node in list */
        delete nodeToDeletePtr;            /* free memory */
    }
    else /* middle or end node should be deleted */
    {
        priorNodePtr->setNextPtr(nodeToDeletePtr->getNextPtr()); /* skip nodeToDelete in linked list */
        delete nodeToDeletePtr;                            /* free memory */
    }
}


int main() {
    cout << "Hello, World!" << endl;

    // Create an empty list
    Node * startPtr = nullptr;
    printNodes(startPtr);

    insertNodeInLinkedList(&startPtr, 1);
    insertNodeInLinkedList(&startPtr, 3);
    printNodes(startPtr);

    deleteNodeInLinkedList(&startPtr, 5);
    printNodes(startPtr);
    deleteNodeInLinkedList(&startPtr, 1);
    printNodes(startPtr);

    return 0;
}
