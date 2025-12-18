package management;

public class Labor {
    private String laborType;
    private double ratePerHour;
    private int hoursWorked;

    public Labor(String laborType, double ratePerHour, int hoursWorked) {
        this.laborType = laborType;
        this.ratePerHour = ratePerHour;
        this.hoursWorked = hoursWorked;
    }

    public double getRatePerHour() {
		return ratePerHour;
	}

	public void setRatePerHour(double ratePerHour) {
		this.ratePerHour = ratePerHour;
	}

	public int getHoursWorked() {
		return hoursWorked;
	}

	public void setHoursWorked(int hoursWorked) {
		this.hoursWorked = hoursWorked;
	}

	public void setLaborType(String laborType) {
		this.laborType = laborType;
	}

	public double calculateLaborCost() {
        return ratePerHour * hoursWorked;
    }

    public String getLaborType() {
        return laborType;
    }
}