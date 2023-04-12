package lib;

public class TaxFunction {
	private static final int Max_Children = 3;
    private static final int Married_Tax_Threshold = 4500000;
    private static final int Child_Tax_Threshold = 1500000;
    private static final int Default_Tax_Threshold = 54000000;
	
	/**
	 * Fungsi untuk menghitung jumlah pajak penghasilan pegawai yang harus dibayarkan setahun.
	 * 
	 * Pajak dihitung sebagai 5% dari penghasilan bersih tahunan (gaji dan pemasukan bulanan lainnya dikalikan jumlah bulan bekerja dikurangi pemotongan) dikurangi penghasilan tidak kena pajak.
	 * 
	 * Jika pegawai belum menikah dan belum punya anak maka penghasilan tidak kena pajaknya adalah Rp 54.000.000.
	 * Jika pegawai sudah menikah maka penghasilan tidak kena pajaknya ditambah sebesar Rp 4.500.000.
	 * Jika pegawai sudah memiliki anak maka penghasilan tidak kena pajaknya ditambah sebesar Rp 4.500.000 per anak sampai anak ketiga.
	 * 
	 */
	

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