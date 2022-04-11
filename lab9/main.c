#include <stdio.h>
#include <stdlib.h>
#include <time.h>

#define DIMENSION 4

int** create_random_matrix() {
    int **matrix = (int**) malloc(DIMENSION * sizeof(int*));
    for(int i = 0; i < DIMENSION; i++) matrix[i] = (int*) malloc(DIMENSION * sizeof(int));
    for (int i = 0; i < DIMENSION; ++i)
        for (int j = 0; j < DIMENSION; ++j)
            matrix[i][j] = rand() % 9;
    return matrix;
}

void print_matrix(int** matrix) {
    for (int i = 0; i < DIMENSION; ++i) {
        for (int j = 0; j < DIMENSION; ++j) {
            printf("%d ", matrix[i][j]);
        }
        printf("\n");
    }
}

int** multiply_two_matrices(int** A, int** B) {
    int **matrix = (int**) malloc(DIMENSION * sizeof(int*));
    for(int i = 0; i < DIMENSION; i++) matrix[i] = (int*) malloc(DIMENSION * sizeof(int));
    for (int i = 0; i < DIMENSION; ++i)
        for (int j = 0; j < DIMENSION; ++j)
            for (int k = 0; k < DIMENSION; ++k)
                matrix[i][j] += A[i][k] * B[k][j];
    return matrix;
}

int main() {
    clock_t begin = clock();
    srand(time(NULL)); // NOLINT(cert-msc51-cpp)

    int** A = create_random_matrix();
    int** B = create_random_matrix();
    print_matrix(A);
    printf("--------\n");
    print_matrix(B);
    printf("--------\n");
    int** product = multiply_two_matrices(A, B);
    print_matrix(product);

    printf("Time elapsed: %.2f ms.\n", (double) (clock() - begin) / CLOCKS_PER_SEC * 1000);
    return 0;
}
