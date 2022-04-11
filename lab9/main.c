#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <omp.h>

#define DIMENSION 10

int** create_random_matrix() {
    int **matrix = (int**) malloc(DIMENSION * sizeof(int*));
    for(int i = 0; i < DIMENSION; i++) matrix[i] = (int*) malloc(DIMENSION * sizeof(int));
    for (int i = 0; i < DIMENSION; ++i)
        for (int j = 0; j < DIMENSION; ++j)
            matrix[i][j] = rand() % 9; // NOLINT(cert-msc50-cpp)
    return matrix;
}

int** create_diagonal_matrix() {
    int **matrix = (int**) malloc(DIMENSION * sizeof(int*));
    for(int i = 0; i < DIMENSION; i++) matrix[i] = (int*) malloc(DIMENSION * sizeof(int));
    for (int i = 0; i < DIMENSION; ++i)
        for (int j = 0; j < DIMENSION; ++j)
            matrix[i][j] = (i == j) ? (rand() % 8) + 1 : 0;  // NOLINT(cert-msc50-cpp)
    return matrix;
}

void print_matrix(int** matrix) {
    for (int i = 0; i < DIMENSION; ++i) {
        for (int j = 0; j < DIMENSION; ++j) {
            printf("%3d ", matrix[i][j]);
        }
        printf("\n");
    }
}

int** multiply_two_matrices(int** A, int** B) {
    int **matrix = (int **) malloc(DIMENSION * sizeof(int *));
    for (int i = 0; i < DIMENSION; i++) matrix[i] = (int *) malloc(DIMENSION * sizeof(int));

    int i, j, k;
#pragma omp parallel for private(i, j, k) shared(matrix, A, B) collapse(3) default(none)
    for (i = 0; i < DIMENSION; ++i)
        for (j = 0; j < DIMENSION; ++j)
            for (k = 0; k < DIMENSION; ++k)
                matrix[i][j] += A[i][k] * B[k][j];
    return matrix;

}
int** multiply_two_diagonal_matrices(int** A, int** B) {
    int **matrix = (int **) malloc(DIMENSION * sizeof(int *));
    for (int i = 0; i < DIMENSION; i++) matrix[i] = (int *) malloc(DIMENSION * sizeof(int));

    int i, j;
#pragma omp parallel for private(i, j) shared(matrix, A, B) collapse(2) default(none)
    for (i = 0; i < DIMENSION; ++i)
        for (j = 0; j < DIMENSION; ++j)
            matrix[i][j] = (i == j) ? A[i][j] * B[i][j] : 0;
    return matrix;
}

void free_matrix(int** matrix) {
    for (int i = 0; i < DIMENSION; ++i)
        free(matrix[i]);
    free(matrix);
}

int main() {
    double start_time = omp_get_wtime();
    srand(time(NULL)); // NOLINT(cert-msc51-cpp)
    omp_set_num_threads(omp_get_num_procs());

    int** A = create_diagonal_matrix();
    int** B = create_diagonal_matrix();
    print_matrix(A);
    printf("\n");
    print_matrix(B);
    printf("\n");
    int** product = multiply_two_diagonal_matrices(A, B);
    print_matrix(product);
    free_matrix(A);
    free_matrix(B);
    free_matrix(product);

    double run_time = omp_get_wtime() - start_time;
    printf("Run time = %.4f s.\n", run_time);
    return 0;
}
