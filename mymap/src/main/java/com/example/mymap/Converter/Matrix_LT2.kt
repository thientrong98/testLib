package tech.vlab.ttqhhcm.Converter

class Matrix_LT2 {
    var values: Array<DoubleArray?>
    var ncols: Int
    var nrows: Int

    constructor(dat: Array<DoubleArray?>) {
        values = dat
        nrows = dat.size
        ncols = dat[0]!!.size
    }

    constructor(row: Int, col: Int) {
        nrows = row
        ncols = col
        values = java.lang.reflect.Array.newInstance(
            java.lang.Double.TYPE, *intArrayOf(
                nrows,
                ncols
            )
        ) as Array<DoubleArray?>
    }

    fun setValueAt(row: Int, col: Int, value: Double) {
        values[row]!![col] = value
    }

    fun getValueAt(row: Int, col: Int): Double {
        return values[row]!![col]
    }

    val isSquare: Boolean
        get() = nrows == ncols

    fun size(): Int {
        return if (isSquare) {
            nrows
        } else -1
    }

    fun multiplyByConstant(constant: Double): Matrix_LT2 {
        val mat = Matrix_LT2(nrows, ncols)
        for (i in 0 until nrows) {
            for (j in 0 until ncols) {
                mat.setValueAt(i, j, values[i]!![j] * constant)
            }
        }
        return mat
    }

    fun insertColumnWithValue1(): Matrix_LT2 {
        val X_ = Matrix_LT2(nrows, ncols + 1)
        for (i in 0 until X_.nrows) {
            for (j in 0 until X_.ncols) {
                if (j == 0) {
                    X_.setValueAt(i, j, 1.0)
                } else {
                    X_.setValueAt(i, j, getValueAt(i, j - 1))
                }
            }
        }
        return X_
    }

    companion object {
        private fun changeSign(i: Int): Int {
            return if (i % 2 == 0) {
                1
            } else -1
        }

        @Throws(RuntimeException::class)
        fun determinant(matrix: Matrix_LT2): Double {
            return if (!matrix.isSquare) {
                throw RuntimeException("matrix need to be square.")
            } else if (matrix.size() == 1) {
                matrix.getValueAt(0, 0)
            } else {
                if (matrix.size() == 2) {
                    return matrix.getValueAt(0, 0) * matrix.getValueAt(1, 1) - matrix.getValueAt(
                        0,
                        1
                    ) * matrix.getValueAt(1, 0)
                }
                var sum = 0.0
                for (i in 0 until matrix.ncols) {
                    sum += changeSign(i).toDouble() * matrix.getValueAt(0, i) * determinant(
                        createSubMatrix(matrix, 0, i)
                    )
                }
                sum
            }
        }

        fun createSubMatrix(
            matrix: Matrix_LT2,
            excluding_row: Int,
            excluding_col: Int
        ): Matrix_LT2 {
            val mat = Matrix_LT2(matrix.nrows - 1, matrix.ncols - 1)
            var r = -1
            for (i in 0 until matrix.nrows) {
                if (i != excluding_row) {
                    r++
                    var c = -1
                    for (j in 0 until matrix.ncols) {
                        if (j != excluding_col) {
                            c++
                            mat.setValueAt(r, c, matrix.getValueAt(i, j))
                        }
                    }
                }
            }
            return mat
        }

        @Throws(RuntimeException::class)
        fun cofactor(matrix: Matrix_LT2): Matrix_LT2 {
            val mat = Matrix_LT2(matrix.nrows, matrix.ncols)
            for (i in 0 until matrix.nrows) {
                for (j in 0 until matrix.ncols) {
                    mat.setValueAt(
                        i, j, (changeSign(i) * changeSign(j)).toDouble() * determinant(
                            createSubMatrix(matrix, i, j)
                        )
                    )
                }
            }
            return mat
        }

        fun transpose(matrix: Matrix_LT2): Matrix_LT2 {
            val transposedMatrix = Matrix_LT2(matrix.ncols, matrix.nrows)
            for (i in 0 until matrix.nrows) {
                for (j in 0 until matrix.ncols) {
                    transposedMatrix.setValueAt(j, i, matrix.getValueAt(i, j))
                }
            }
            return transposedMatrix
        }

        @Throws(RuntimeException::class)
        fun inverse(matrix: Matrix_LT2): Matrix_LT2 {
            return transpose(cofactor(matrix)).multiplyByConstant(1.0 / determinant(matrix))
        }

        fun multiply(A: Matrix_LT2, B: Matrix_LT2): Matrix_LT2 {
            val row1 = A.nrows
            val col1 = B.ncols
            if (row1 != col1) {
                throw RuntimeException("Illegal matrix dimensions.")
            }
            val C = Matrix_LT2(A.ncols, B.ncols)
            for (i in 0 until row1) {
                for (j in 0 until col1) {
                    for (k in 0 until row1) {
                        val dArr = C.values[i]
                        dArr!![j] = dArr[j] + A.values[i]!![k] * B.values[k]!![j]
                    }
                }
            }
            return C
        }

        fun add(A: Matrix_LT2, B: Matrix_LT2): Matrix_LT2 {
            if (A.nrows == B.nrows && A.ncols == B.ncols) {
                val C = Matrix_LT2(A.ncols, A.ncols)
                for (i in 0 until A.nrows) {
                    for (j in 0 until A.ncols) {
                        C.values[i]!![j] = A.values[i]!![j] + B.values[i]!![j]
                    }
                }
                return C
            }
            throw RuntimeException("Illegal matrix dimensions.")
        }

        fun multyply_anfa(A: Matrix_LT2, anfa: Double): Matrix_LT2 {
            val C = Matrix_LT2(A.ncols, A.ncols)
            for (i in 0 until A.nrows) {
                for (j in 0 until A.ncols) {
                    C.values[i]!![j] = A.values[i]!![j] * anfa
                }
            }
            return C
        }

        fun multiply_Vector(A: Matrix_LT2, x: DoubleArray): DoubleArray {
            val m = A.nrows
            val n = x.size
            if (m != n) {
                throw RuntimeException("Illegal matrix dimensions.")
            }
            val y = DoubleArray(m)
            for (i in 0 until m) {
                for (j in 0 until n) {
                    y[i] = y[i] + A.values[i]!![j] * x[j]
                }
            }
            return y
        }

        fun add_Vector(x: DoubleArray, y: DoubleArray): DoubleArray {
            if (x.size != y.size) {
                throw RuntimeException("Illegal matrix dimensions.")
            }
            val z = DoubleArray(x.size)
            for (i in x.indices) {
                z[i] = x[i] + y[i]
            }
            return z
        }

        fun LOG1(A: Matrix_LT2) {
            for (i in 0 until A.nrows) {
                for (j in 0 until A.ncols) {
//                Log.e("Aij", "" + A.data[i][j]);
                }
            }
        }

        fun LOG2(v: DoubleArray) {
            for (d in v) {
//            Log.e("Aij", "" + d);
            }
        }
    }
}