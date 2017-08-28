package smile;

/* this class contains the calculation for evaluate the model */
public class ModelEvaluation {

    // confusion matrix
    public int[][] confusionMatrix(int[] testYClass, int[] testYPredictClass, int Idx) throws Exception {
        int[][] matrix = new int[Idx + 1][Idx + 1];

        for (int k = 0; k < testYClass.length; k++) {
            int datax = testYClass[k];
            int datay = testYPredictClass[k];
            matrix[datax][datay]++;
        }
        return matrix;
    }

    // printing the result of TP, TN, FP, FN
    public void printTrueFalsePositiveNegatif(int[][] matrix, int Idx) {
        int[] TP = calculTruePositive(matrix, Idx);
        int[] TN = calculTrueNegative(matrix, Idx);
        int[] FP = calculFalsePositive(matrix, Idx);
        int[] FN = calculFalseNegative(matrix, Idx);
        int[] totalEachClass = calculRowClass(matrix, Idx);
        int[] totalEachColumn = calculColumnClass(matrix, Idx);
        int totalAll = calculTotalClass(matrix, Idx);

        System.out.println("Total all data " + totalAll);
        System.out.println("\nTotal each class");
        for (int j = 0; j < totalEachClass.length; j++) {
            System.out.print(totalEachClass[j] + "\t");
        }

        System.out.println("\nTotal each column");
        for (int j = 0; j < totalEachColumn.length; j++) {
            System.out.print(totalEachColumn[j] + "\t");
        }

        System.out.println("\nTrue positives");
        for (int j = 0; j < TP.length; j++) {
            System.out.print(TP[j] + "\t");
        }

        System.out.println("\nFalse negatives");
        for (int j = 0; j < FN.length; j++) {
            System.out.print(FN[j] + "\t");
        }

        System.out.println("\nFalse positives");
        for (int j = 0; j < FP.length; j++) {
            System.out.print(FP[j] + "\t");
        }

        System.out.println("\nTrue negatives");
        for (int j = 0; j < TN.length; j++) {
            System.out.print(TN[j] + "\t");
        }
    }

    public int calculTotalClass(int[][] matrix, int Idx) {
        int totalAll = 0;

        // calculating total instance of all class
        for (int i = 0; i <= Idx; i++) {
            for (int j = 0; j <= Idx; j++) {
                totalAll += matrix[i][j];
            }
        }
        return totalAll;
    }

    public int[] calculRowClass(int[][] matrix, int Idx) {
        int[] totalEachClass = new int[Idx + 1];

        // calculating each row of class
        for (int i = 0; i <= Idx; i++) {
            totalEachClass[i] = 0;
            for (int j = 0; j <= Idx; j++) {
                totalEachClass[i] += matrix[i][j];
            }
        }
        return totalEachClass;
    }

    public int[] calculColumnClass(int[][] matrix, int Idx) {
        int[] totalEachColumn = new int[Idx + 1];

        // calculating total for each column
        for (int i = 0; i <= Idx; i++) {
            totalEachColumn[i] = 0;
            for (int j = 0; j <= Idx; j++) {
                totalEachColumn[i] += matrix[j][i];
            }
        }
        return totalEachColumn;
    }

    public int[] calculTruePositive(int[][] matrix, int Idx) {
        int[] TP = new int[Idx + 1];

        // calculating TP for each class
        for (int i = 0; i <= Idx; i++) {
            for (int j = 0; j <= Idx; j++) {
                if (i == j) {
                    TP[i] += matrix[i][j];
                }
            }
        }
        return TP;
    }

    public int[] calculFalseNegative(int[][] matrix, int Idx) {
        int[] TP = calculTruePositive(matrix, Idx);
        int[] FN = new int[Idx + 1];
        int[] totalEachClass = calculRowClass(matrix, Idx);

        // calculating FN for each class
        for (int i = 0; i <= Idx; i++) {
            FN[i] = totalEachClass[i] - TP[i];
        }
        return FN;
    }

    public int[] calculFalsePositive(int[][] matrix, int Idx) {
        int[] TP = calculTruePositive(matrix, Idx);
        int[] FP = new int[Idx + 1];
        int[] totalEachColumn = calculColumnClass(matrix, Idx);

        // calculating FP for each class
        for (int i = 0; i <= Idx; i++) {
            FP[i] = totalEachColumn[i] - TP[i];
        }
        return FP;
    }

    public int[] calculTrueNegative(int[][] matrix, int Idx) {
        int[] TP = calculTruePositive(matrix, Idx);
        int[] TN = new int[Idx + 1];
        int[] totalEachClass = calculRowClass(matrix, Idx);
        int[] totalEachColumn = calculColumnClass(matrix, Idx);
        int totalAll = calculTotalClass(matrix, Idx);

        // calculating TN for each class
        for (int i = 0; i <= Idx; i++) {
            TN[i] = totalAll - (totalEachClass[i] + totalEachColumn[i] - TP[i]);
        }

        return TN;
    }

    // accuracy of all class = TP of all class / number of all instances
    public double Accuracy(int[] TP, int calculTotal) {
        double totalTP = 0.0;
        for (int i = 0; i < TP.length; i++) {
            totalTP += TP[i];
        }
        return ((float) (totalTP / calculTotal));
    }

    /**
     Precision: Given all the predicted labels (for a given class X), how many instances were correctly predicted?
     Recall: For all instances that should have a label X, how many of these were correctly captured?
     **/

    // precision = TP / (TP + FP)
    public double[] Precision(int[] TP, int[] FP) {
        double[] resultPrecision = new double[TP.length];
        for (int i = 0; i < TP.length; i++) {
            resultPrecision[i] = Double.isNaN((float) TP[i] / (TP[i] + FP[i])) ? 0.0 : (float) TP[i] / (TP[i] + FP[i]);
        }
        return resultPrecision;
    }

    // Recall = sensitivity = TP / (TP + FN)
    public double[] Recall(int[] TP, int[] FN) {
        double[] resultRecall = new double[TP.length];
        for (int i = 0; i < TP.length; i++) {
            resultRecall[i] = Double.isNaN((float) TP[i] / (TP[i] + FN[i])) ? 0.0 : (float) TP[i] / (TP[i] + FN[i]);
        }
        return resultRecall;
    }

    // Specificity = true negative rate = TN / (TN + FP)
    public double[] Specificity(int[] TN, int[] FP) {
        double[] resultSpecificity = new double[TN.length];
        for (int i = 0; i < TN.length; i++) {
            resultSpecificity[i] = Double.isNaN((float) TN[i] / (TN[i] + FP[i])) ? 0.0 : (float) TN[i] / (TN[i] + FP[i]);
        }
        return resultSpecificity;
    }

    // Fmeasure = 2 * (precision * recall) / (precision + recall)
    public double[] Fmeasure(double[] Precision, double[] Recall) {
        double[] resultFmeasure = new double[Precision.length];
        for (int i = 0; i < Precision.length; i++) {
            resultFmeasure[i] = Double.isNaN((float) 2 * (Precision[i] * Recall[i]) / (Precision[i] + Recall[i])) ? 0.0 : (float) (float) 2 * (Precision[i] * Recall[i]) / (Precision[i] + Recall[i]);
        }
        return resultFmeasure;
    }

    // the average of FMeasure
    public double allFmeasure(double Precision, double Recall) {
        double resultAllFmeasure = Double.isNaN((float) 2 * (Precision * Recall) / (Precision + Recall)) ? 0.0 : (float) 2 * (Precision * Recall) / (Precision + Recall);
        return resultAllFmeasure;
    }

    // the average of Precision macro
    public double allPrecisionMacro(double[] Precision) {
        double resultAllPrecision = 0.0, subTotal = 0.0;
        for (int i = 0; i < Precision.length; i++) {
            subTotal += Precision[i];
        }
        resultAllPrecision = Double.isNaN((float) subTotal / Precision.length) ? 0.0 : (float) subTotal / Precision.length;
        return resultAllPrecision;
    }

    // the average of Precision micro
    public double allPrecisionMicro(int[] TP, int[] FP) {
        double resultAllPrecision = 0.0, subTotalTP = 0.0, subTotalFP = 0.0;
        for (int i = 0; i < TP.length; i++) {
            subTotalTP += TP[i];
            subTotalFP += FP[i];
        }
        resultAllPrecision = Double.isNaN((float) subTotalTP / (subTotalTP + subTotalFP)) ? 0.0 : (float) (float) subTotalTP / (subTotalTP + subTotalFP);
        return resultAllPrecision;
    }

    // the average of Recall macro
    public double allRecallMacro(double[] Recall) {
        double resultAllRecall = 0.0;
        double subTotal = 0.0;
        for (int i = 0; i < Recall.length; i++) {
            subTotal += Recall[i];
        }
        resultAllRecall = Double.isNaN((float) subTotal / Recall.length) ? 0.0 : (float) subTotal / Recall.length;
        return resultAllRecall;
    }

    // the average of Recall micro
    public double allRecallMicro(int[] TP, int[] FN) {
        double resultAllRecall = 0.0, subTotalTP = 0.0, subTotalFN = 0.0;
        for (int i = 0; i < TP.length; i++) {
            subTotalTP += TP[i];
            subTotalFN += FN[i];
        }
        resultAllRecall = Double.isNaN((float) subTotalTP / (subTotalTP + subTotalFN)) ? 0.0 : (float) (float) subTotalTP / (subTotalTP + subTotalFN);
        return resultAllRecall;
    }

    // the average of Specificity
    public double allSpecificity(double[] Specificity) {
        double resultAllSpecificity = 0.0;
        double subTotal = 0.0;
        for (int i = 0; i < Specificity.length; i++) {
            subTotal += Specificity[i];
        }
        resultAllSpecificity = Double.isNaN((float) subTotal / Specificity.length) ? 0.0 : (float) subTotal / Specificity.length;
        return resultAllSpecificity;
    }

    public int calculInstanceClassified(int[] Testy, int[] yPredict) {
        // calculating the classes classified
        int count_classified = 0;

        // counting class classified or not
        for (int i = 0; i < Testy.length; i++) {
            if (Testy[i] == yPredict[i]) {
                count_classified++;
            }
        }
        return count_classified;
    }

    public int calculInstanceNotClassified(int[] Testy, int[] yPredict) {
        // calculating the classes classified
        int count_error = 0;

        // counting class classified or not
        for (int i = 0; i < Testy.length; i++) {
            if (Testy[i] != yPredict[i]) {
                count_error++;
            }
        }
        return count_error;
    }

}