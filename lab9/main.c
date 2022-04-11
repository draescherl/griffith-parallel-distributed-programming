#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <omp.h>

#define DIMENSION 1000


int** create_random_matrix() {
    int **matrix = (int**) malloc(DIMENSION * sizeof(int*));
    for(int i = 0; i < DIMENSION; i++) matrix[i] = (int*) malloc(DIMENSION * sizeof(int));
    for (int i = 0; i < DIMENSION; ++i)
        for (int j = 0; j < DIMENSION; ++j)
            matrix[i][j] = (rand() % 8) + 1; // NOLINT(cert-msc50-cpp)
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


int** create_upper_triangular_matrix() {
    int **matrix = (int**) malloc(DIMENSION * sizeof(int*));
    for(int i = 0; i < DIMENSION; i++) matrix[i] = (int*) malloc(DIMENSION * sizeof(int));
    for (int i = 0; i < DIMENSION; ++i)
        for (int j = 0; j < DIMENSION; ++j)
            matrix[i][j] = (i > j) ? 0 : (rand() % 8) + 1;  // NOLINT(cert-msc50-cpp)
    return matrix;
}


int** create_lower_triangular_matrix() {
    int **matrix = (int**) malloc(DIMENSION * sizeof(int*));
    for(int i = 0; i < DIMENSION; i++) matrix[i] = (int*) malloc(DIMENSION * sizeof(int));
    for (int i = 0; i < DIMENSION; ++i)
        for (int j = 0; j < DIMENSION; ++j)
            matrix[i][j] = (i < j) ? 0 : (rand() % 8) + 1;  // NOLINT(cert-msc50-cpp)
    return matrix;
}


void print_matrix(int** matrix) {
    for (int i = 0; i < DIMENSION; ++i) {
        for (int j = 0; j < DIMENSION; ++j)
            printf("%3d ", matrix[i][j]);
        putchar('\n');
    }
}


int** multiply_two_matrices(int** A, int** B) {
    int **matrix = (int **) malloc(DIMENSION * sizeof(int *));
    for (int i = 0; i < DIMENSION; i++) matrix[i] = (int *) malloc(DIMENSION * sizeof(int));

    int i, j, k;
#pragma omp parallel for private(i, j, k) shared(matrix, A, B) collapse(2) default(none)
    for (i = 0; i < DIMENSION; ++i) {
        for (j = 0; j < DIMENSION; ++j) {
            matrix[i][j] = 0;
            for (k = 0; k < DIMENSION; ++k)
                matrix[i][j] += A[i][k] * B[k][j];
        }
    }
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


int** multiply_two_upper_triangular_matrices(int** A, int** B) {
    int **matrix = (int **) malloc(DIMENSION * sizeof(int *));
    for (int i = 0; i < DIMENSION; i++) matrix[i] = (int *) malloc(DIMENSION * sizeof(int));

    int i, j, k;
#pragma omp parallel for private(i, j, k) shared(matrix, A, B) collapse(2) default(none)
    for (i = 0; i < DIMENSION; ++i) {
        for (j = 0; j < DIMENSION; ++j) {
            matrix[i][j] = 0;
            if (i > j)
                for (k = 0; k < DIMENSION; ++k)
                    matrix[i][j] += A[i][k] * B[k][j];
        }
    }
    return matrix;
}


int** multiply_two_lower_triangular_matrices(int** A, int** B) {
    int **matrix = (int **) malloc(DIMENSION * sizeof(int *));
    for (int i = 0; i < DIMENSION; i++) matrix[i] = (int *) malloc(DIMENSION * sizeof(int));

    int i, j, k;
#pragma omp parallel for private(i, j, k) shared(matrix, A, B) collapse(2) default(none)
    for (i = 0; i < DIMENSION; ++i) {
        for (j = 0; j < DIMENSION; ++j) {
            matrix[i][j] = 0;
            if (i < j)
                for (k = 0; k < DIMENSION; ++k)
                    matrix[i][j] += A[i][k] * B[k][j];
        }
    }
    return matrix;
}


void free_matrix(int** matrix) {
    for (int i = 0; i < DIMENSION; ++i)
        free(matrix[i]);
    free(matrix);
}


int main() {
    srand(time(NULL)); // NOLINT(cert-msc51-cpp)
    omp_set_num_threads(omp_get_num_procs());
    double start_time, run_time;
    int **A, **B, **product;

    printf("Multiplying two random square matrices (%dx%d) ...\n", DIMENSION, DIMENSION);
    start_time = omp_get_wtime();
    A = create_random_matrix();
    B = create_random_matrix();
    product = multiply_two_matrices(A, B);
    run_time = omp_get_wtime() - start_time;
    printf("Done. Took %.4f s.\n", run_time);

    printf("Multiplying two diagonal matrices (%dx%d) ...\n", DIMENSION, DIMENSION);
    start_time = omp_get_wtime();
    A = create_diagonal_matrix();
    B = create_diagonal_matrix();
    product = multiply_two_diagonal_matrices(A, B);
    run_time = omp_get_wtime() - start_time;
    printf("Done. Took %.4f s.\n", run_time);

    printf("Multiplying two upper triangular matrices (%dx%d) ...\n", DIMENSION, DIMENSION);
    start_time = omp_get_wtime();
    A = create_upper_triangular_matrix();
    B = create_upper_triangular_matrix();
    product = multiply_two_upper_triangular_matrices(A, B);
    run_time = omp_get_wtime() - start_time;
    printf("Done. Took %.4f s.\n", run_time);

    printf("Multiplying two lower triangular matrices (%dx%d) ...\n", DIMENSION, DIMENSION);
    start_time = omp_get_wtime();
    A = create_lower_triangular_matrix();
    B = create_lower_triangular_matrix();
    product = multiply_two_lower_triangular_matrices(A, B);
    run_time = omp_get_wtime() - start_time;
    printf("Done. Took %.4f s.\n", run_time);

    free_matrix(A);
    free_matrix(B);
    free_matrix(product);

    return 0;
}
