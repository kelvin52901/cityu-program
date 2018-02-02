// Student name: Lok Hiu Fung Kelvin	
// Student ID  : 54608948

// *** Submission deadline: 24 Sept 2017, Sunday, 11pm
// *** Upload your .cpp file via Canvas. 
// *** Late submission or submission by other means will not be graded.

#include <iostream>
#include <cstdlib>
#include <ctime>

using namespace std;

/*
Part-1:	Find the number of words in the input char array.
*/
int wordCount(const char text[])
{
	// Precondition: 
	//   Input array contains lowercase and uppercase letters, digits, and the space char only.
	//   The array is terminated by '\0'.
	//   Words are separated by one or more space char. 

	int count = 0;
	int i;
	int temp;
	for (i = 0; text[i] != '\0'; i++) {
		if (text[i] != ' ' && (text[i + 1] == ' ' || text[i + 1] == '\0')) {
			count++;
		}


		//your codes
	}

	return count;
}


/*
Part-2:	Find the first non-repeating non-white space character in the array.
*/
int firstNonRepeatCharLoc(const char text[])
{
	// Precondition: Input array contains lowercase and uppercase letters, digits and space char.
	int i;
	int temp = 0;
	char temp1 = 0;
	char temp2;
	int result = 0;
	while (text[temp] != '\0') {

		for (i = 0; text[i] != '\0'; i++) {
			temp1 = text[temp];
			temp2 = text[i];
			if (i == temp)
				continue;
			if (text[temp] >= 'a')				//to uppercase
				temp1 = text[temp] - 'a' + 'A';	//to uppercase
			if (text[i] >= 'a')					//to uppercase
				temp2 = text[i] - 'a' + 'A';	//to uppercase

			if (temp1 == ' ') {
				result = 0;
				temp++;
				break;
			}
			if (temp1 == temp2) {
				result = 0;
				temp++;
				break;
			}
			result++;
		}
		if (result > strlen(text)) {
			return temp;
		}
	}
	// Lowercase and uppercase letters (e.g. 'a' and 'A') are considered the same.
	// The function returns the index of the first non-repeating (non-white space) char, if any.
	// If no non-repeating char (non-white space) is found, the function returns -1.

	return -1;
	// dummy return statement
}


/*
Part-3:	Find the longest substring without repeating characters.
*/
void longestSubstringNoRepeatChar(const char text[], int& start, int& end)
{
	// Precondition: Input array contains lowercase and uppercase letters, and digits (no space char).
	// Lowercase and uppercase letters are considered the same, i.e. 'a' == 'A'.
	// start and end denote the beginning and end indexes of the substring.
	int e, s = 0, st = 0;
	for (e = 1; text[e] != '\0'; e++) {
		for (s = st; s < e; s++) {
			if (toupper(text[s]) == toupper(text[e])) {//find same char
				if ((e - st) > (end - start))//determine which bigger. if bigger,change start and end
				{
					end = e - 1;
					start = st;
				}
				st = s + 1;
				break;
			}
		}
		if (text[e + 1] == '\0') {					//determine the last case without the same char
			if ((e - st) > (end - start))
			{
				end = e;
				start = st;
			}
		}
	}
	// your codes	

	// your codes	
}



// The main() function is implemented for you.
// Purpose of the main() function is to generate test data to test the
// functions that you have written for parts 1 to 3.

int main()
{
	char text1[] = "EE2331  Data Structures and Algorithms  Tutorial  2 ";

	cout << "Part-1:" << endl << endl;

	cout << "Input text[] = " << text1 << endl;
	int wc = wordCount(text1);
	cout << "Word count = " << wc << endl << endl;

	cout << "---------------------------------------------------" << endl << endl;
	cout << "Part-2:" << endl << endl;

	char text2[] = "Engineering Undergraduate Degree Programs in City University of Hong Kong";
	int f = firstNonRepeatCharLoc(text2);

	cout << "Input text = " << text2 << endl;
	if (f >= 0)
		cout << "First non-repeating char = " << text2[f] << endl << endl;
	else
		cout << "First non-repeating char = null" << endl << endl;

	cout << "---------------------------------------------------" << endl << endl;
	cout << "Part-3:" << endl << endl;

	char text3[] = "DataStructuresandAlgorithmsTutorial2";
	int start = 0, end = 0;
	longestSubstringNoRepeatChar(text3, start, end);

	cout << "Input text = " << text3 << endl;
	cout << "Longest substring without repeating chars : ";
	for (int i = start; i <= end; i++)
		cout << text3[i];
	cout << endl << endl;  // expected output: ndAlgorithms

	char text4[] = "ThisIsTheSecondTestString123456789";
	start = 0, end = 0;
	longestSubstringNoRepeatChar(text4, start, end);

	cout << "Input text = " << text4 << endl;
	cout << "Longest substring without repeating chars : ";
	for (int i = start; i <= end; i++)
		cout << text4[i];
	cout << endl << endl;  // expected output: String123456789

	system("pause");
	return 0;
}

