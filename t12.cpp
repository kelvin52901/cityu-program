// Student name: LOK HIU FUNG KELVIN
// Student ID  :54608948

// Submission deadline: 3 Dec 2017, Sunday, 11 pm.

/*
Part-1: Binary search tree
In each data node, it stores the value and a weight.
The weight is equal to the number of nodes in the subtree whose root is the current node.
With the knowledge of the weight of each node in a binary search tree,
we can find the element for a given rank.
The smallest element is ranked 1st, and so on.

1. Implement the function to compute the weight of each node in a binary tree.
2. Implement the function to look up an element in a binary search tree by rank.

Part-2: Recursion and backtracking
Implement a function to determine if a given set of integers can be divided
into 2 partitions where the sums of the 2 partitions differ by no more than 1.

For example:
Set of values = {1, 2, 3, 4, 7}

The set can be divided into 2 partitions with
partition_0 = {1, 7}
partition_1 = {2, 3, 4}
Sum of partiton_0 is 8, and sum of partition_1 is 9.
*/

#include <iostream>
#include <iomanip>
#include <fstream>

using namespace std;

template<class Type>
struct treeNode
{
	Type info;
	treeNode<Type> *left, *right;
};

struct dataRec
{
	int value;
	int weight;
};

ostream& operator<<(ostream& os, const dataRec& r)
{
	os << r.value << "(" << r.weight << ")";
	return os;
}

template <class Type>
void printTree(const treeNode<Type> *p, int indent)
{
	if (p != NULL)
	{
		printTree(p->right, indent + 7);
		cout << setw(indent) << p->info << endl;
		printTree(p->left, indent + 7);
	}
}

//----------------------------------------- functions for part-1

int computeWeight(treeNode<dataRec> *tree)
{
	if (tree->left == NULL&&tree->right == NULL)
		return  tree->info.weight = 1;

	if (tree->left != NULL)
		tree->info.weight += computeWeight(tree->left);
	if (tree->right != NULL)
		tree->info.weight += computeWeight(tree->right);
	// Your codes
	tree->info.weight += 1;
	return tree->info.weight; // dummy return statement
}

int searchByRank(treeNode<dataRec> *tree, int rank)
{

	// Precondition: 1 <= rank <= tree->info.weight (no. of nodes in the tree)

	// The smallest element in the BST has rank = 1.
	// This function makes use of the weight information available in the data structure.
	while (tree != NULL)
	{
		if (tree->left != NULL)
		{
			if (rank <= tree->left->info.weight)
				tree = tree->left;
			else if (rank != tree->left->info.weight + 1) {
				rank -= (tree->left->info.weight + 1);
				tree = tree->right;
			}
			else if (rank == tree->left->info.weight + 1)
				return tree->info.value;
			else return tree->left->info.value;
		}
		else {
			if (tree->right != NULL) {
				if (rank == tree->info.weight)
					return tree->right->info.value;
				if (rank == tree->right->info.weight)
					return tree->info.value;
			}
			if (tree->right == NULL)
				return tree->info.value;
		}
		searchByRank(tree, rank);
	}
	return 0; // dummy return statement
}


//------------------------------------------ function for part-2

bool partition(int *A, int n, int i, int& s0, int& s1)
{
	// Determine if elements in A[] can be divided into 2 partitions such
	// that the sums of the 2 partitions differ by no more than 1.

	// s0 = sum of elements in partition_0
	// s1 = sum of elements in partition_1

	// Try to place A[i] in partition_0 or partition_1,
	// and then make progress using recursion.


	// Your codes
	int partition0 = 0;
	int partition1 = 0;
	int test = 0;
	if (i < n) {
		if (s0 <= s1) {				//try to put in partition0
			partition0 = A[i++];
			s0 += partition0;
		}
		else {								//try to put in partition1
			partition1 = A[i++];
			s1 += partition1;
		}

		while (partition(A, n, i, s0, s1) == false) {	//backtracking
			if (partition1 > 0) {
				s1 -= partition1;
				partition0 = partition1;
				s0 += partition0;
				partition1 = 0;
				test++;
			}
			else {
				s0 -= partition0;
				partition1 = partition0;
				s1 += partition1;
				partition0 = 0;
				test++;
			}
			if (partition0 > 0 && test > 1) {
				s0 -= partition0;
				return false;
			}
			if (partition1 > 0 && test > 1) {
				s1 -= partition1;
				return false;
			}
		}
	}
	if (abs(s0 - s1) <= 1)
		return true;
	else {
		return false;
	}
}


//----------------------------------------- functions prepared for you

treeNode<dataRec> *buildTreeFromArray(const int *a, int n, int i, int nullSymb)
{
	// Create the binary tree from the input array.
	// Weight of tree nodes are not determined.

	treeNode<dataRec> *p = NULL;
	if (i < n && a[i] != nullSymb)
	{
		p = new treeNode<dataRec>;
		p->info.value = a[i];
		p->info.weight = 0;
		p->left = buildTreeFromArray(a, n, 2 * i + 1, nullSymb);
		p->right = buildTreeFromArray(a, n, 2 * i + 2, nullSymb);
	}
	return p;
}

void part_2(int *A, int n)
{
	cout << "\n-----------------------------------------------------------\n";
	cout << "Input array : ";
	for (int i = 0; i < n; i++)
		cout << A[i] << ", ";
	cout << endl << endl;

	int sum0 = 0, sum1 = 0;
	if (partition(A, n, 0, sum0, sum1))
	{
		cout << "Array is divided into 2 partitions with approx. equal sums.\n";
		cout << "sum0 = " << sum0 << ", sum1 = " << sum1 << "\n\n";
	}
	else
		cout << "Array cannot be divided into 2 partitions with approx. equal sum\n\n";
}


int main()
{
	// Part-1
	int arrayTree[] = { 55, 35, 70, 20, 43, 62, 85, -1, 29, 38, 51, -1, -1, 80 };
	int n = 14;
	treeNode<dataRec> *bst = buildTreeFromArray(arrayTree, n, 0, -1);  // nullSymb = -1
	computeWeight(bst);
	cout << endl;
	printTree(bst, 7);
	cout << endl << endl;

	cout << "Searching a binary search tree by rank\n";
	for (int i = 1; i <= bst->info.weight; i++)
		cout << "Element ranked " << i << " in data set = " << searchByRank(bst, i) << endl;


	// Part-2: Recursion with backtracking
	int A1[] = { 1 };
	int A2[] = { 3 };
	int A3[] = { 2, 4, 7, 3, 1 };
	int A4[] = { 5, 8, 12, 6, 7, 19 };
	int n1 = 1;
	int n2 = 1;
	int n3 = 5;
	int n4 = 6;

	part_2(A1, n1);
	part_2(A2, n2);
	part_2(A3, n3);
	part_2(A4, n4);

	int A5[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 19, 28, 37, 46, 55, 64, 73, 82, 91 };
	int n5 = 18;
	part_2(A5, n5);

	int A6[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 22, 33, 44, 55, 66, 77, 88, 999 };
	int n6 = 18;
	part_2(A6, n6);

	system("pause");
	exit(0);
}


