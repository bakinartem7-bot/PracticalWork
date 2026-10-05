package validation;

import model.Bus;

public class BusValidator {
    public static boolean isValidNumber(String number) {
        if (number == null || number.trim().isEmpty()) {
            return false;
        }
        String trimmed = number.trim();
        if (trimmed.matches("[A-Za-z0-9\\-]{1,20}")) {
            return true;
        }
        return false;
    }

    public static boolean isValidModel(String model) {
        if (model == null || model.trim().isEmpty()) {
            return false;
        }
        return true;
    }

    public static boolean isValidMileage(String mileageStr) {
        if (mileageStr == null || mileageStr.trim().isEmpty()) {
            return false;
        }
        try {
            long mileage = Long.parseLong(mileageStr.trim());
            if (mileage < 0) {
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidBus(String number, String model, String mileageStr) {
        return isValidNumber(number) && isValidModel(model) && isValidMileage(mileageStr);
    }

    public static boolean isValidBus(Bus bus) {
        if (bus == null) {
            return false;
        }
        return isValidNumber(bus.getNumber()) && isValidModel(bus.getModel()) && bus.getMileage() >= 0;
    }
}