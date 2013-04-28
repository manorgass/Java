#include <stdio.h>
#define TRUE 1

int getNum() {
	int num;
	char trash;
	while (TRUE)
	{
		printf("Please input the number for select functions (1~5) : ");
		scanf("%d", &num);
		if(num > 5 || num < 0) {
			
			printf("input error!!\n\n");
			continue;
		}
		return num;
	}
}

void printMenu() {
	printf("1) Show ip address table\n");
	printf("2) Change ip address\n");
	printf("3) Set user data\n");
	printf("4) Exit\n");
}




int main() {
	int x = 0;
	printf("======IP Switch ver 1.00 by JungWon Kim======\n");		



	while(TRUE) {
		printMenu();
		x = getNum();

		switch(x) {
		case 4:
			return 0;
		}
	}
}