package model;

public class Bus {
    private final String number;
    private final String model;
    private final long mileage;

    private Bus(Builder builder) {
        this.number = builder.number;
        this.model = builder.model;
        this.mileage = builder.mileage;
    }

    public String getNumber() {
        return number;
    }

    public String getModel() {
        return model;
    }

    public long getMileage() {
        return mileage;
    }

    @Override
    public String toString() {
        return "Bus{" +
                "number='" + number + '\'' +
                ", model='" + model + '\'' +
                ", mileage=" + mileage +
                '}';
    }

    public static class Builder {
        private String number;
        private String model;
        private long mileage;

        public Builder number(String number) {
            this.number = number;
            return this;
        }

        public Builder model(String model) {
            this.model = model;
            return this;
        }

        public Builder mileage(long mileage) {
            this.mileage = mileage;
            return this;
        }

        public Bus build() {
            return new Bus(this);
        }
    }
}