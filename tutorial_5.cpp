// Student name : Lok Hiu Fung Kelvin
// Student ID   :54608948

// Submission deadline : Sunday, 15 Oct 2017, 11pm
// Upload your .cpp file via Canvas
// Late submission or submission by other means will not be graded

/*
	This tutorial is a continuation of tutorial-04.

	Part-1:
	The telList obtained from the data file is sorted by tel. no.
	One can search for the user name based on a given tel. no.

	In an application, one may also need to search for the tel. no.
	with a given name.

	One way to allow a program to do the search operation based on name
	is to create an index array (rather than sort the telList by name).

	In this exercise, we shall create an array of telRec pointers such that
	the records referenced by the pointers in the array are arranged in
	ascending order based on name.

	telRec **nameIndex = new telRec*[n];  // array of telRec pointers

	Initially nameIndex[i] points to the record telList[i].
	You need to write your codes to sort the array nameIndex[] such that
		nameIndex[i]->name is less than nameIndex[i+1]->name for 0 <= i <= n-2

	After sorting the nameIndex[], you shall implement a function to search for
	the tel. no. for a given name using binary search.

	Part-2:
	Two persons are considered to be friend of each other if they belong to
	the same chat group.

	In the second part, implement a function to print the name and tel. no.
	of the friends of a given user (specified by name).

	The tel. no. of the members in a chat group are sorted in ascending order.
*/

#include <iostream>
#include <fstream>
#include <string>

using namespace std;

struct telRec {
	string name;
	string tel;
};

struct chatGroup {
	string groupName;
	string *member;  // array of string storing the tel no. of group members
	int size;
};

const string NotFound = "not found";

void readTelList(const string& filename, telRec*& telList, int& n)
{
	// File format :
	// Number of records is given on the first line.
	// Each telephone record is stored in 1 line (starting from 2nd line).
	// Name is delimited by comma ',' followed by a space char, and then the telephone number.
	// Telephone records in the file are ordered by telephone number.

	// Read in the data from the file.
	// Create the array telList, and set the value of n (number of telRec)

	ifstream infile(filename);
	if (!infile.is_open())
	{
		cout << "Error: cannot open data file : " << filename << endl;
		system("pause");
		exit(0);
	}

	string temp;
	infile >> temp >> n;
	telList = new telRec[n];

	// Use getline() to read in one line of text from the file.
	// The C++ function getline() is similar to Scanner.nextLine() in Java.

	string line;
	getline(infile, line);  // consume the '\n' after the value of n

	for (int i = 0; i < n; i++)
	{
		getline(infile, line);
		int j = 0;
		while (line[j] != ',')
			j++;
		telList[i].name = line.substr(0, j);
		telList[i].tel = line.substr(j + 2, 8);
	}
	infile.close();
}

int compareString(const void *a, const void *b)
{
	string *s1 = (string *)a;
	string *s2 = (string *)b;
	return s1->compare(*s2);
}

void readChatGroup(const string& filename, chatGroup*& groupList, int& g)
{
	// File format :
	// Number of chat groups is given on the first line.
	// Each group contains:
	//    group_name size
	//    telephone numbers of the members in the chat group (not ordered)
	// Chat groups in the file are ordered by group name.

	// Read in the data from the file.
	// Create the array groupList, and set the value of g (number of groups)

	ifstream infile(filename);
	if (!infile.is_open())
	{
		cout << "Error: cannot open data file : " << filename << endl;
		system("pause");
		exit(0);
	}

	string temp;
	infile >> temp >> g;
	groupList = new chatGroup[g];

	for (int i = 0; i < g; i++)
	{
		infile >> groupList[i].groupName >> groupList[i].size;
		groupList[i].member = new string[groupList[i].size];

		for (int j = 0; j < groupList[i].size; j++)
			infile >> groupList[i].member[j];

		// sort the list of tel. no. in the chat group
		qsort(groupList[i].member, groupList[i].size, sizeof(string), compareString);
	}
	infile.close();
}

int compTelRecByTel(const void *a, const void *b)
{
	// compare 2 telRec based on tel

	telRec *v1 = (telRec *)a;
	telRec *v2 = (telRec *)b;

	return v1->tel.compare(v2->tel);
}

const string& getNameByTel(const telRec *telList, int n, const string& tel)
{
	// Return the name (by reference) that is associated with the given tel number
	// If the input tel is not found, 
	// return Notfound (the const string defined at the top of the file).

	// The returned string object is a const, i.e. the calling function cannot modify 
	// the returned string object.

	// Use binary search to find the telRec

	/*
	int low = 0;
	int high = n-1;
	while (low <= high)
	{
		int mid = (low + high) / 2;
		if (telList[mid].tel == tel)
			return telList[mid].name;

		if (telList[mid].tel < tel)
			low = mid + 1;
		else
			high = mid - 1;
	}
	return NotFound;
	*/

	// implementation using the function bsearch() of the C standard library
	telRec t;
	t.tel = tel;

	telRec *result;
	result = (telRec *)bsearch(&t, telList, n, sizeof(telRec), compTelRecByTel);

	if (result != NULL)
		return result->name;

	return NotFound;
}

void printChatGroup(const chatGroup& group, const telRec *telList, int n)
{
	cout << "Chat group : " << group.groupName << endl;
	cout << "Number of members : " << group.size << endl;

	for (int i = 0; i < group.size; i++)
	{
		string name = getNameByTel(telList, n, group.member[i]);
		cout << name << ", " << group.member[i] << endl;
	}
	cout << endl;
}

void printChatGroupByName(const chatGroup *groupList, int g, const string& gname,
	const telRec *telList, int n)
{
	// Print the chat group to the standard output.
	// If the chat group is found, format of the output:
	//    Chat group : group_name
	//    Number of members :
	//    name, tel (one line per member)

	// If the chat group is not found, format of the output:
	//    Chat group : group_name not found

	// Use binary search to find the group

	int low = 0;
	int high = g - 1;
	while (low <= high)
	{
		int mid = (low + high) / 2;
		if (groupList[mid].groupName == gname)
		{
			printChatGroup(groupList[mid], telList, n);
			return;
		}

		if (groupList[mid].groupName < gname)
			low = mid + 1;
		else
			high = mid - 1;
	}

	cout << "Chat group : " << gname << " not found" << endl << endl;
}

// --------------------------------------------------  functions for tut-05

//int compstring(const void *a, const void *b)
//{
	// compare 2 telRec based on tel

	//telRec *v1 = (telRec *)a;
//	telRec *v2 = (telRec *)b;

	//return (v1->name.compare(v2->name));

//}
string test = "1";
const string& getTelByName(telRec **ptrList, int n, const string& user)
{
	//binaray search

	int high = n;
	int low = 0;
	while (low <= high) {
		int mid = (high + low) / 2;
		if (user == ptrList[mid]->name)
			return ptrList[mid]->tel;
		if (user > ptrList[mid]->name)
			low = mid + 1;
		else
			high = mid - 1;

	}

	// your codes
	// implement this function using binary search

	return NotFound;
}


void printFriend(const chatGroup *groupList, int g, const telRec *telList,
	telRec **index, int n, const string& user)
{
	// If the user is not found, print
	//    User name_of_user is not found

	// If friends of the user are found, print
	//    Friends of name_of_user :
	//    name, tel (one record per line, no duplication and do NOT include user himself)

	// If friends of the user are not found, print
	//    Friends of name_of_user : not found

	// You don't know how many friends the user may have.
	// You need to consider the dynamic allocation and de-allocation of the array(s) for
	// storing the intermediate results.
	int groupLoc;
	int telLoc;
	bool find = false;
	int count = 0;
	string *friends = NULL;
	//temp = new string[100];
	string tel = getTelByName(index, n, user);
	if (tel == NotFound)
	{
		cout << "User " << user << " is " << NotFound << endl << endl;
		return;
	}
	else {
		for (groupLoc = 0; groupLoc < g; groupLoc++) {		//search groplist
			for (telLoc = 0; telLoc < groupList[groupLoc].size; telLoc++) { //search member match
				if (groupList[groupLoc].member[telLoc] == tel) {
					find = true;
					if (friends == NULL)
						friends = new string[groupList[groupLoc].size];	//aloocation
					if (count > 0) {
						string *temp = new string[groupList[groupLoc].size + count];	//re-allovation
						for (int l = 0; l < count; l++) {
							temp[l] = friends[l];		//copy array content to new
						}
						delete[]friends;	//de-allocatetion of old array
						friends = temp;
					}
					for (int z = 0; z < groupList[groupLoc].size; z++) {		//copy friend tel to friend array

						if (groupList[groupLoc].member[z] != tel) {
							friends[count] = groupList[groupLoc].member[z];

							count++;
						}
					}

				}
			}
		}
		if (find == true) {		//print result
			cout << "User " << user << " " << "Friends of name_of_user :" << count << endl;
			for (int z = 0; z < count; z++) {
				cout << "name: " << getNameByTel(telList, n, friends[z]) << " tel: " << friends[z] << endl;
			}
			delete[] friends;		//de-allocatetion of array
		}
		else
			cout << "User " << user << " no firend" << endl;
	}
	// your codes

}

void tut_05(telRec *telList, int n, chatGroup *groupList, int g)
{
	telRec **nameIndex = new telRec*[n];
	for (int i = 0; i < n; i++) {
		nameIndex[i] = &telList[i];
	}
	for (int i = 0; i < n; i++) {			// Insertion sort
		telRec *t = nameIndex[i];
		int j;
		for (j = i - 1; j >= 0 && nameIndex[j]->name > t->name; j--) {
			nameIndex[j + 1] = nameIndex[j];
		}
		nameIndex[j + 1] = t;
	}

	// +++++  your codes to sort the array nameIndex[]


	// print the list of telRec ordered by name
	cout << "Tel List : " << n << " records\n";
	for (int i = 0; i < n; i++)
		cout << nameIndex[i]->name << ", " << nameIndex[i]->tel << endl;
	cout << endl;

	string user[4] = { "CHAN Tai Man", "YEUNG Pok Long",
					  "WONG Kin Ho",  "LUK Yin Ting" };

	for (int i = 0; i < 4; i++)
	{
		cout << "---------------------------------------\n\n";
		printFriend(groupList, g, telList, nameIndex, n, user[i]);
		// Expected search outcomes:
		//    CHAN Tai Man : user not found
		//    YEUNG Pok Long : 5 friends are found, print the name and tel. no. of the 5 users
		//    WONG Kin Ho : 0 friend is found
		//    LUK Yin Ting : 11 friends are found, print the name and tel. no. of the 11 users
	}
}

int main()
{
	telRec *telList;
	int n;  // number of tel records
	chatGroup *groupList;
	int g;  // number of chat groups

	string file1 = "tel-name.txt";    // sorted by telephone number
	string file2 = "chat-groups.txt"; // sorted by group name, tel no. within a group are not sorted

	readTelList(file1, telList, n);
	readChatGroup(file2, groupList, g);

	tut_05(telList, n, groupList, g);


	//string t3 = getTelByName(telList, n, nameIndex, user);
	//cout << "Tel. no. of " << user << " is " << t3 << endl;
	//
	//string t1 = "91765295";
	//cout << "Owner of tel. no. " << t1 << " is " << getNameByTel(telList, n, t1) << endl << endl;
	// Expected output
	//    Owner of tel. no. 91765295 is WONG Kin Ho
	/*
	string t2 = "90001111";
	cout << "Owner of tel. no. " << t2 << " is " << getNameByTel(telList, n, t2) << endl << endl;;
	// Expected output
	//    Owner of tel. no. 90001111 is not found


	string gname = "group-04";
	printChatGroupByName(groupList, g, gname, telList, n);
	// Expected output
	//    Chat group : group-04
	//    Number of members : 7
	//    CHAN Wai Hang, 98512078
	//    LUI Ka Fai, 98529423
	//    TSE Tsz Hin, 95871616
	//    SIU Ka Hin, 92432172
	//    LOK Kam Hung, 93670697
	//    WAN Tsun Man, 97039667
	//    CHAN Man Hong, 92026350


	gname = "group-03";
	printChatGroupByName(groupList, g, gname, telList, n);
	// Expected output
	//    Chat group : group-03 not found
	*/

	system("pause");
	exit(0);
}