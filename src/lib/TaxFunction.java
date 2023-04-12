package lib;

public class TaxFunction {
	private static final int Max_Children = 3;
    private static final int Married_Tax_Threshold = 4500000;
    private static final int Child_Tax_Threshold = 1500000;
    private static final int Default_Tax_Threshold = 54000000;
	
	public static int calculateTax(EmployeeData employeeData) {
        int numberOfMonthWorking = employeeData.getNumberOfMonthWorking();
        if (numberOfMonthWorking > 12) {
            System.err.println("More than 12 month working per year");
            return 0;
        }

        int monthlySalary = employeeData.getMonthlySalary();
        int otherMonthlyIncome = employeeData.getOtherMonthlyIncome();
        int deductible = employeeData.getDeductible();
        boolean isMarried = employeeData.isMarried();
        int numberOfChildren = employeeData.getNumberOfChildren();
        if (numberOfChildren > Max_Children) {
            numberOfChildren = Max_Children;
        }

        int taxThreshold = Default_Tax_Threshold;
        if (isMarried) {
            taxThreshold += Married_Tax_Threshold;
        }
        taxThreshold += numberOfChildren * Child_Tax_Threshold;

        int netIncome = (monthlySalary + otherMonthlyIncome) * numberOfMonthWorking - deductible;
        int taxableIncome = netIncome - taxThreshold;
        if (taxableIncome < 0) {
            return 0;
        }

        return (int) Math.floor(0.05 * taxableIncome);
    }
}