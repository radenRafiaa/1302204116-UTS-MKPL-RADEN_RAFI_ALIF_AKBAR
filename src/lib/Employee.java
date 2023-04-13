package lib;

import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;

public class Employee {
	private EmployeeClass employees
	private LocalDate dateJoined;
	private boolean isForeigner;
	private Gender gender; //true = Laki-laki, false = Perempuan

	private double monthlySalary;
    private double annualSalary;
    private double annualIncomeTax;

	private Spouse spouse;

	private List<String> childNames;
	private List<String> childIdNumbers;
	
	public Employee(EmployeeClass employees, LocalDate dateJoined, boolean isForeigner,  Gender gender) {
		this.employees = employees;
		this.dateJoined = dateJoined;
		this.isForeigner = isForeigner;
		this.gender = gender;
		
		childNames = new LinkedList<String>();
		childIdNumbers = new LinkedList<String>();
	}

	public enum Gender {
		MALE, FEMALE
	}

	public class EmployeeClass{
		private String employeeId;
		private String firstName;
		private String lastName;
		private String idNumber;
		private String address;

		public EmployeeClass(String employeeId, String firstName,String lastName,String idNumber,String address) {
			this.employeeId = employeeId;
			this.firstName = firstName;
			this.lastName = lastName;
			this.idNumber = idNumber;
			this.address = address;
		}
	}

	public class Spouse {
        private String name;
        private String idNumber;

        public Spouse(String name, String idNumber) {
            this.name = name;
            this.idNumber = idNumber;
        }

        public String getName() {
            return name;
        }

        public String getIdNumber() {
            return idNumber;
        }
    }

	public class Child {
        private String name;
        private String idNumber;

        public Child(String name, String idNumber) {
            this.name = name;
            this.idNumber = idNumber;
        }

        public String getName() {
            return name;
        }

        public String getIdNumber() {
            return idNumber;
        }
    }
	
	/**
	 * Fungsi untuk menentukan gaji bulanan pegawai berdasarkan grade kepegawaiannya (grade 1: 3.000.000 per bulan, grade 2: 5.000.000 per bulan, grade 3: 7.000.000 per bulan)
	 * Jika pegawai adalah warga negara asing gaji bulanan diperbesar sebanyak 50%
	 */
	
	public void setMonthlySalary(int grade) {	
	this.monthlySalary = calculateSalary(grade, isForeigner);
}

	private double calculateSalary(int grade, boolean isForeigner) {
		if (grade == 1) {
			return isForeigner ? 4500000 : 3000000;
		} else if (grade == 2) {
			return isForeigner ? 7500000 : 5000000;
		} else if (grade == 3) {
			return isForeigner ? 10500000 : 7000000;
		}
		return 0;
	}
	
	public void setAnnualDeductible(int deductible) {	
		this.annualDeductible = deductible;
	}
	
	public void setOtherMonthlyIncome(int income) {	
		this.otherMonthlyIncome = income;
	}
	
	public String getSpouseName() {
		return spouseName;
	}
	
	public void setSpouseName(String spouseName) {
		this.spouseName = spouseName;
	}
	
	public String getSpouseIdNumber() {
		return spouseIdNumber;
	}
	
	public void setSpouseIdNumber(String spouseIdNumber) {
		this.spouseIdNumber = spouseIdNumber;
	}
	
	public void addChild(String childName, String childIdNumber) {
        children.add(new Child(childName, childIdNumber));
    }
	
	public int getAnnualIncomeTax() {
		int monthWorkingInYear = calculateMonthWorkingInYear();
		double taxableIncome = calculateTaxableIncome();
		this.annualIncomeTax = TaxFunction.calculateTax(taxableIncome, monthWorkingInYear);
		return annualIncomeTax;
	}

	private int calculateMonthWorkingInYear() {
		LocalDate now = LocalDate.now();
		return now.getYear() == dateJoined.getYear() ? now.getMonthValue() - dateJoined.getMonthValue() : 12;
	}

	//Menghitung berapa lama pegawai bekerja dalam setahun ini, jika pegawai sudah bekerja dari tahun sebelumnya maka otomatis dianggap 12 bulan.
	private double calculateTaxableIncome() {
		double totalIncome = monthlySalary + otherMonthlyIncome;
		if (spouse != null) {
			totalIncome += employees.getSpouseAdditionalIncome();
		}
		if (!childNames.isEmpty()) {
			totalIncome += employees.getChildAdditionalIncome() * childNames.size();
		}
		return totalIncome * 12 - employees.getAnnualDeductible();
	}
}